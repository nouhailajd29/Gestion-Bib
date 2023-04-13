package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws SQLException, BibliothequeExceptions {
        try {
           String url ="jdbc:mysql://localhost:3306/g.bibliotheque";
           String driver ="com.mysql.jdbc.Driver";
           String nomUtilisateur = "root";
           String motDePasse = "";
           return DriverManager.getConnection(url, nomUtilisateur, motDePasse);
        } catch (Exception ex) {
            throw new BibliothequeExceptions("Impossible de charger le fichier properties '");
        }

        
    }

}