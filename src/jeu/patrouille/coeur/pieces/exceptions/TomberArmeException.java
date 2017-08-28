/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.exceptions;

/**
 *
 * @author appleale
 */
public class TomberArmeException extends Exception {

    /**
     * Creates a new instance of <code>TomberArmeException</code> without detail
     * message.
     */
    public TomberArmeException() {
    }

    /**
     * Constructs an instance of <code>TomberArmeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TomberArmeException(String msg) {
        super(msg);
    }
}
