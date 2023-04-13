package control;

import Utility.BibliothequeExceptions;
import java.util.ArrayList;

import Domaine.Oeuvre;
/**
 * 
 * @author Diallo & Janati
 */
public class OeuvreControl {

    public static void ajouter(Oeuvre oeuvre) throws BibliothequeExceptions {
        //Verifier la validité des informations
        oeuvre.setTitre(oeuvre.getTitre());
        oeuvre.setAuteur(oeuvre.getAuteur());
        oeuvre.setCategorie(oeuvre.getCategorie());

        oeuvre.ajouter(oeuvre);
    }

    public static void modifier(Oeuvre oeuvre) throws BibliothequeExceptions {
        oeuvre.setId(oeuvre.getId());
        oeuvre.setTitre(oeuvre.getTitre());
        oeuvre.setAuteur(oeuvre.getAuteur());
        oeuvre.setCategorie(oeuvre.getCategorie());
        oeuvre.setNbResa(oeuvre.getNbResa());

        oeuvre.modifier(oeuvre);
    }

    public static void supprimer(Oeuvre oeuvre) throws BibliothequeExceptions {
        oeuvre.delete(oeuvre);
    }
    public static Oeuvre findById(int id) throws BibliothequeExceptions {
        Oeuvre oeuvre = new Oeuvre();
        return oeuvre.findById(id);
    }

    public static ArrayList<Oeuvre> findByTitre(String titre) throws BibliothequeExceptions {
        //Oeuvre oeuvre = new Oeuvre();
        return new Oeuvre().findByTitre(titre);
    }
    public static ArrayList<Oeuvre> getListOeuvres() throws BibliothequeExceptions {
        return new Oeuvre().getListOeuvres();
    }
    
}
