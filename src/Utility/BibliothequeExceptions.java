package Utility;

import javax.swing.JOptionPane;

/**
 *
 * @author Diallo & Janati
 */
public class BibliothequeExceptions extends Exception{

    public BibliothequeExceptions(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "Informations", JOptionPane.PLAIN_MESSAGE);
    }
    
    public BibliothequeExceptions(Throwable cause) {
        super(cause);
    }
    
    public BibliothequeExceptions(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
