/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments.armes.exceptions;

/**
 *
 * @author appleale
 */
public class IncompatibleMagazineException extends Exception {

    /**
     * Creates a new instance of <code>IncompatibleMagazineException</code>
     * without detail message.
     */
    public IncompatibleMagazineException() {
    }

    /**
     * Constructs an instance of <code>IncompatibleMagazineException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncompatibleMagazineException(String msg) {
        super(msg);
    }
}
