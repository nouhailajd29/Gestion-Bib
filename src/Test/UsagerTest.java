package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import Domaine.Usager;
import Utility.BibliothequeExceptions;
import control.OeuvreControl;
import control.UsagerControl;

public class UsagerTest {
    private static UsagerControl usagerControl;
    @Test
	public void testAjouter() {
	    Usager usager = new Usager("Mohammed", "Bohado", new Date(90, 11, 31), "M", "1 rue des Lilas", "0123456789");

	    try {
	        usagerControl.ajouter(usager);
	       
	    } catch (BibliothequeExceptions e) {
	        fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
	    }
	}
    @Test
    public void testModifier() {
        // Ajout d'un usager à la base de données
        Usager usager = new Usager("mohammed", "bohado", new Date(90, 11, 31), "M", "1 rue des Lilas", "0123456789");
        try {
            usagerControl.ajouter(usager);
        } catch (BibliothequeExceptions e) {
            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
        }
        
        // Modification des données de l'usager
        try {
            Usager usagerModifie = usagerControl.getListUsagers().get(0);
            usagerModifie.setNom("SMITH");
            usagerModifie.setPrenom("Jane");
            usagerModifie.setDateNais(new Date(85, 3, 20));
            usagerModifie.setSexe("F");
            usagerModifie.setAdresse("2 avenue des Roses");
            usagerModifie.setTel("0987654321");
            usagerControl.modifier(usagerModifie);
            assertEquals("SMITH", usagerControl.getListUsagers().get(0).getNom());
            assertEquals("Jane", usagerControl.getListUsagers().get(0).getPrenom());
            assertEquals(new Date(85, 3, 20), usagerControl.getListUsagers().get(0).getDateNais());
            assertEquals("F", usagerControl.getListUsagers().get(0).getSexe());
            assertEquals("2 avenue des Roses", usagerControl.getListUsagers().get(0).getAdresse());
            assertEquals("0987654321", usagerControl.getListUsagers().get(0).getTel());
        } catch (BibliothequeExceptions e) {
            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
        }
    }
    public void testSupprimer() {
        // Ajout d'un usager à la base de données
        Usager usager = new Usager("mohammed", "bohado", new Date(90, 11, 31), "M", "1 rue des Lilas", "0123456789");
        try {
            usagerControl.ajouter(usager);
        } catch (BibliothequeExceptions e) {
            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
        }
        
        // Suppression de l'usager
        try {
            usagerControl.supprimer(usager);
        } catch (BibliothequeExceptions e) {
            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
        }

        // Vérification que l'usager a bien été supprimé
        try {
			assertEquals(0, usagerControl.getListUsagers().size());
		} catch (BibliothequeExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}