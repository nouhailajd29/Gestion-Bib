package Test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Domaine.Oeuvre;
import Utility.BibliothequeExceptions;
import Utility.DBConnection;
import control.OeuvreControl;

public class OeuvreTest {
	
	    private static OeuvreControl ouvreControl;

	    @BeforeClass
	    public static void setUpBeforeClass() throws Exception {
	        ouvreControl = new OeuvreControl();
	        DBConnection.getConnection();
	    }

	    @AfterClass
	    public static void tearDownAfterClass() throws Exception {
	        DBConnection.getConnection();
	    }

	    @Test
	    public void testAjouter() {
	        try {
	            Oeuvre oeuvre = new Oeuvre();
	            oeuvre.setTitre("Tit");
	            oeuvre.setAuteur("Auteur de l'oeuvre");
	            oeuvre.setCategorie("Categorie de l'oeuvre");
	           
	            ouvreControl.ajouter(oeuvre);
	           
	        } catch (BibliothequeExceptions e) {
	            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
	        }
	    }

	    @Test
	    public void testModifier() {
	        try {
	            // Ajouter une oeuvre
	            Oeuvre oeuvre = new Oeuvre();
	            oeuvre.setTitre("Titre de l'oeuvre");
	            oeuvre.setAuteur("Auteur de l'oeuvre");
	            oeuvre.setCategorie("Categorie de l'oeuvre");
	            ouvreControl.ajouter(oeuvre);
	            int idOeuvre = ouvreControl.getListOeuvres().get(0).getId();

	            // Modifier l'oeuvre
	            Oeuvre oeuvreModifiee = new Oeuvre();
	            oeuvreModifiee.setId(idOeuvre);
	            oeuvreModifiee.setTitre("Nouveau titre");
	            oeuvreModifiee.setAuteur("Nouvel auteur");
	            oeuvreModifiee.setCategorie("Nouvelle categorie");
	            ouvreControl.modifier(oeuvreModifiee);

	            // Vérifier que l'oeuvre a été modifiée
	            assertEquals("Nouveau titre", ouvreControl.getListOeuvres().get(0).getTitre());
	            assertEquals("Nouvel auteur", ouvreControl.getListOeuvres().get(0).getAuteur());
	            assertEquals("Nouvelle categorie", ouvreControl.getListOeuvres().get(0).getCategorie());
	        } catch (BibliothequeExceptions e) {
	            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
	        }
	    }
	    @Test
	    public void testSupprimer() {
	        try {
	            // création de l'oeuvre à supprimer
	            Oeuvre oeuvre = new Oeuvre();
	            oeuvre.setTitre("Titre de l'oeuvre");
	            oeuvre.setAuteur("Auteur de l'oeuvre");
	            oeuvre.setCategorie("Categorie de l'oeuvre");
	           
	            // ajout de l'oeuvre à la liste d'oeuvres du contrôleur
	            ouvreControl.ajouter(oeuvre);
	            assertEquals(1, ouvreControl.getListOeuvres().size());
	            
	            // suppression de l'oeuvre
	            ouvreControl.supprimer(oeuvre);
	            assertEquals(0, ouvreControl.getListOeuvres().size());
	            
	        } catch (BibliothequeExceptions e) {
	            // en cas d'exception, le test échoue
	            fail("Une exception ne devrait pas être lancée ici : " + e.getMessage());
	        }
	    }

	}