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
public class ImmobilzedSodlatException extends Exception {

    /**
     * Creates a new instance of <code>ImmobilzedSodlatException</code> without
     * detail message.
     */
    public ImmobilzedSodlatException() {
    }

    /**
     * Constructs an instance of <code>ImmobilzedSodlatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ImmobilzedSodlatException(String msg) {
        super(msg);
    }
}
