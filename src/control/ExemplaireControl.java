package control;

import Utility.BibliothequeExceptions;
import java.util.ArrayList;

import Domaine.Exemplaire;
import Domaine.Oeuvre;
/**
 * 
 * @author Diallo & Janati
 */
public class ExemplaireControl {

    public static void ajouter(Oeuvre oeuvre, String etatExemplaire) throws BibliothequeExceptions {
         oeuvre = oeuvre.findById(oeuvre.getId());
        if (null == oeuvre) {
            throw new BibliothequeExceptions("L'oeuvre n'existe pas");
        }
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.ajouter(oeuvre, etatExemplaire);
    }

    public static void modifier(Exemplaire exemplaire) throws BibliothequeExceptions {
        exemplaire.setId(exemplaire.getId());
        exemplaire.setEtat(exemplaire.getEtat());

        exemplaire.modifier(exemplaire);
    }

    public static void supprimer(Exemplaire exemplaire) throws BibliothequeExceptions {
        exemplaire.delete(exemplaire);
    }

    public static Exemplaire findById(int id) throws BibliothequeExceptions {
        Exemplaire exemplaire = new Exemplaire();
        return exemplaire.findById(id);
    }

    public static ArrayList<Exemplaire> findExemplaireDispo(Oeuvre oeuvre) throws BibliothequeExceptions {
        Exemplaire exemplaire = new Exemplaire();
        return exemplaire.findExemplaireDispo(oeuvre);
    }

    public static ArrayList<Exemplaire> find(Oeuvre oeuvre) throws BibliothequeExceptions {
        Exemplaire exemplaire = new Exemplaire();
        return exemplaire.find(oeuvre);
    }
}
