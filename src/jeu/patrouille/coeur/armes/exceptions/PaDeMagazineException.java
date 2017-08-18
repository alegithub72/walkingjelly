/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.armes.exceptions;

/**
 *
 * @author appleale
 */
public class PaDeMagazineException extends Exception {

    /**
     * Creates a new instance of <code>PaDeMagazine</code> without detail
     * message.
     */
    public PaDeMagazineException() {
    }

    /**
     * Constructs an instance of <code>PaDeMagazine</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public PaDeMagazineException(String msg) {
        super(msg);
    }
}
