package Domaine;

import Utility.BibliothequeExceptions;
import Utility.DBConnection;
import static Utility.Utility.YMDtoDMY;
import static Utility.Utility.closeStatement;
import static Utility.Utility.closeStatementResultSet;
import static Utility.Utility.dateToStr;
import static Utility.Utility.initialiseRequetePreparee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Enitiy Emprunt
 * @author Diallo & Janati
 */
public class Emprunt {

    private int id;
    private Date dateEmprunt;
    private Date dateRetourPrevu;
    private Date dateRetourEffective;
    private Usager usagerEmprunt;
    private Exemplaire exemplairesEmprunt;

    public Emprunt() {
        dateEmprunt = new Date();
        dateRetourPrevu = new Date();
        dateRetourEffective = new Date();
        usagerEmprunt = new Usager();
        exemplairesEmprunt = new Exemplaire();
    }

    /**
     * 
     * @param id identifiant  emprunt
     * @param dateEmprunt date emprunt
     * @param dateRetourPrevu  date de retour prévu
     */
    public Emprunt(int id, Date dateEmprunt, Date dateRetourPrevu) {
        this.id = id;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevu = dateRetourPrevu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws BibliothequeExceptions {
        if (id <= 0) {
            throw new BibliothequeExceptions("Identifiant Oeuvre non valide !");
        }
        this.id = id;
    }

    public Date getDateRetourEffective() {
        return this.dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) throws BibliothequeExceptions {
        if (dateRetourEffective != null) {
            if (this.dateRetourEffective.compareTo(dateEmprunt) > 0) {
                throw new BibliothequeExceptions("La date de retour effective ne doit pas être avant la"
                        + " date d'emprunt");
            }
        }
        this.dateRetourEffective = dateRetourEffective;
    }

    public Date getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) throws BibliothequeExceptions {
        if (null == dateEmprunt) {
            throw new BibliothequeExceptions("Veuillez renseigner la date d'emprunt");
        }
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetourPrevu() {
        return this.dateRetourPrevu;
    }

    public void setDateRetourPrevu(Date dateRetourPrevu) throws BibliothequeExceptions {
        if (this.dateEmprunt.compareTo(dateRetourPrevu) > 0) {
            throw new BibliothequeExceptions("La date de retour prévu ne doit pas être avant"
                    + " la date d'emprunt");
        }
        this.dateRetourPrevu = dateRetourPrevu;
    }

    public Exemplaire getExemplairesEmprunt() {
        return exemplairesEmprunt;
    }

    public void setExemplairesEmprunt(Exemplaire exemplairesEmprunt) {
        this.exemplairesEmprunt = exemplairesEmprunt;
    }

    public Usager getUsagerEmprunt() {
        return usagerEmprunt;
    }

    public void setUsagerEmprunt(Usager usagerEmprunt) {
        this.usagerEmprunt = usagerEmprunt;
    }
    public void emprunter(Usager usager, Oeuvre oeuvre, Exemplaire exemplaire, Date dateJour) throws BibliothequeExceptions {
        Emprunt emprunt = findEmpruntNonRendu(usager, oeuvre);
        if (null != emprunt) {
            throw new BibliothequeExceptions("Vous avez un emprunt d'exemplaire de cette oeuvre non rendu\n"
                    + "Titre Oeuvre : " + oeuvre.getTitre() + "\n"
                    + "Date d'emprunt : " + YMDtoDMY(emprunt.getDateEmprunt().toString(), "-") + "\n"
                    + "Date de retour Prévu : " + YMDtoDMY(emprunt.getDateRetourPrevu().toString(), "-"));
        }
        final String SQL_INSERT = "INSERT INTO emprunt "
                + "(ExemplaireId, UsagerID, DateEmprunt, DateRetourPrevu, DateRetourEffective) "
                + "VALUES (?, ?, ?, ?, ?)";

        Calendar cal = Calendar.getInstance();

        String typeOeuvre = oeuvre.getClass().getSimpleName();
        String dateRetourPrev;
        if (typeOeuvre.equals(Magazine.class.getSimpleName())) {
            cal.add(Calendar.DATE, 30);
        } else {
            cal.add(Calendar.DATE, 10);
        }
        dateRetourPrev = dateToStr(cal.getTime());
        String formatedDateJour = dateToStr(dateJour);

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = initialiseRequetePreparee(DBConnection.getConnection(), SQL_INSERT,
                    exemplaire.getId(), usager.getId(),
                    formatedDateJour, dateRetourPrev, null);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new BibliothequeExceptions("Echec lors de l'enregistrement de l'emprunt");
            }
        } catch (SQLException e) {
            throw new BibliothequeExceptions("Erreurs lors de l'enregistrement de l'emprunt", e.getCause());
        } catch (BibliothequeExceptions e) {
            throw new BibliothequeExceptions("Erreurs lors de l'enregistrement de l'emprunt ", e.getCause());
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public void delete(Exemplaire exemplaire) throws BibliothequeExceptions {
        final String SQL_DELETE_BY_ID = "DELETE FROM emprunt WHERE ExemplaireId = ? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = initialiseRequetePreparee(DBConnection.getConnection(), SQL_DELETE_BY_ID,
                    exemplaire.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | BibliothequeExceptions e) {
            throw new BibliothequeExceptions("Erreurs lors de la suppression des emprunts de l'exemplaire ", e.getCause());
        } finally {
            closeStatement(preparedStatement);
        }
    }

    //pour voir s'il a deja emprunté un exemplaire de cet oeuvre non rendu
    //il peut pas emprunter plusieurs exemplaire de la meme oeuvre
    public Emprunt findEmpruntNonRendu(Usager usager, Oeuvre oeuvre) throws BibliothequeExceptions {
        final String SQL_SELECT_BY_ID_OEUVRE_USAGER = "SELECT emp.*, u.Nom, u.Prenom, o.id, o.Titre, o.Auteur"
                + " FROM emprunt emp, exemplaire e, usager u, oeuvre o"
                + " WHERE emp.ExemplaireId = e.id"
                + " AND emp.UsagerID = u.id"
                + " AND e.OeuvreID = o.id"
                + " AND emp.DateRetourEffective IS NULL"
                + " AND u.id = ?"
                + " AND o.id = ?";
        ArrayList<Emprunt> emprunts
                = find(SQL_SELECT_BY_ID_OEUVRE_USAGER, usager.getId(), oeuvre.getId());
        return ((emprunts == null) || (emprunts.isEmpty())) ? null : emprunts.get(0);
    }

    public ArrayList<Emprunt> findEmprunts(Oeuvre oeuvre) throws BibliothequeExceptions {
        final String SQL_SELECT_BY_ID_OEUVRE_USAGER = "SELECT emp.*, u.Nom, u.Prenom, o.id, o.Titre, o.Auteur"
                + " FROM emprunt emp, exemplaire e, usager u, oeuvre o"
                + " WHERE emp.ExemplaireId = e.id"
                + " AND emp.UsagerID = u.id"
                + " AND e.OeuvreID = o.id"
                + " AND emp.DateRetourEffective IS NULL"
                + " AND o.id = ?";
        ArrayList<Emprunt> emprunts
                = find(SQL_SELECT_BY_ID_OEUVRE_USAGER, oeuvre.getId());

        return emprunts.isEmpty() ? null : emprunts;
    }

    public Emprunt find(Usager usager, int idExemplaire, Oeuvre oeuvre) throws BibliothequeExceptions {
        final String SQL_SELECT = "SELECT emp.*, u.Nom, u.Prenom, o.id, o.Titre, o.Auteur"
                + " FROM emprunt emp, exemplaire e, usager u, oeuvre o"
                + " WHERE emp.ExemplaireId = e.id"
                + " AND emp.UsagerID = u.id"
                + " AND e.OeuvreID = o.id"
                + " AND emp.UsagerID = ?"
                + " AND emp.ExemplaireId = ?"
                + " AND o.id = ? ";
        ArrayList<Emprunt> emprunts
                = find(SQL_SELECT, usager.getId(), idExemplaire, oeuvre.getId());
        return emprunts.isEmpty() ? null : emprunts.get(0);
    }

    public void Rendre(Emprunt emprunt) throws BibliothequeExceptions {
        final String SQL_UPDATE = "UPDATE emprunt "
                + " SET DateRetourEffective = ? WHERE id = ?";
        Calendar cal = Calendar.getInstance();
        String DateRetEffective = dateToStr(cal.getTime());

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = initialiseRequetePreparee(DBConnection.getConnection(), SQL_UPDATE,
                    DateRetEffective, emprunt.getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new BibliothequeExceptions("Echec de l'enregistrement du retour de l'exemplaire");
            }
        } catch (SQLException e) {
            throw new BibliothequeExceptions("Erreurs lors de l'enregistrement du retour de l'exemplaire ", e.getCause());
        } finally {
            closeStatement(preparedStatement);
        }
    }

    private ArrayList<Emprunt> find(String sql, Object... objets) throws BibliothequeExceptions {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Emprunt> listeEmprunts = new ArrayList<>();

        try {
            preparedStatement = initialiseRequetePreparee(DBConnection.getConnection(), sql, objets);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listeEmprunts.add(mappingExamplaire(resultSet));
            }
        } catch (SQLException e) {
            throw new BibliothequeExceptions("Aucun enregistrement trouvé " + e.getMessage());
        } finally {
            closeStatementResultSet(preparedStatement, resultSet);
        }
        return listeEmprunts;
    }

    private static Emprunt mappingExamplaire(ResultSet resultSet) throws SQLException {

        Emprunt emprunt = new Emprunt();
        try {
            emprunt.setId(resultSet.getInt("id"));
            emprunt.setDateEmprunt(resultSet.getDate("DateEmprunt"));
            emprunt.setDateRetourPrevu(resultSet.getDate("DateRetourPrevu"));
            emprunt.setDateRetourEffective(resultSet.getDate("DateRetourEffective"));
            emprunt.usagerEmprunt.setId(resultSet.getInt("UsagerID"));
            emprunt.usagerEmprunt.setNom(resultSet.getString("u.Nom"));
            emprunt.usagerEmprunt.setPrenom(resultSet.getString("u.Prenom"));
            emprunt.exemplairesEmprunt.setId(resultSet.getInt("ExemplaireId"));

        } catch (BibliothequeExceptions e) {
            System.out.println(e.getMessage());
        }
        return emprunt;
    }

    @Override
    public String toString() {
        return "Emprunt{" + "id=" + id + ", dateEmprunt=" + dateEmprunt + ", dateRetourPrevu=" + dateRetourPrevu + ", dateRetourEffective=" + dateRetourEffective + ", usagerEmprunt=" + usagerEmprunt + ", exemplairesEmprunt=" + exemplairesEmprunt + '}';
    }

}
