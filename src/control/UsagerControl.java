package control;

import Utility.BibliothequeExceptions;
import java.util.ArrayList;

import Domaine.Usager;
/**
 * 
 * @author Diallo & Janati
 */
public class UsagerControl {

    public static void ajouter(Usager usager) throws BibliothequeExceptions {
        //Verifier la validité des informations
        usager.setNom(usager.getNom());
        usager.setPrenom(usager.getPrenom());
        usager.setDateNais(usager.getDateNais());
        usager.setSexe(usager.getSexe());
        usager.setAdresse(usager.getAdresse());
        usager.setTel(usager.getTel());

        usager.ajouter(usager);
    }

    public static void modifier(Usager usager) throws BibliothequeExceptions {
        //Verifier que les informations sont valides
        usager.setNom(usager.getNom());
        usager.setPrenom(usager.getPrenom());
        usager.setDateNais(usager.getDateNais());
        usager.setSexe(usager.getSexe());
        usager.setAdresse(usager.getAdresse());
        usager.setTel(usager.getTel());

        usager.modifier(usager);
    }

    public static void supprimer(Usager usager) throws BibliothequeExceptions {
        usager.delete(usager);
    }

    public static Usager findById(int id) throws BibliothequeExceptions {
        return new Usager().findById(id);
    }

    public static ArrayList<Usager> findByNom(String nom) throws BibliothequeExceptions {
        return new Usager().findByNom(nom);
    }
    
    public static ArrayList<Usager> getListUsagers() throws BibliothequeExceptions {
        return new Usager().getListUsagers();
    }
}
