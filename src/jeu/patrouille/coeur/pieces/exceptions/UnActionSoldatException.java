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
public class UnActionSoldatException extends Exception {

    /**
     * Creates a new instance of <code>UnActionSoldatException</code> without
     * detail message.
     */
    public UnActionSoldatException() {
    }

    /**
     * Constructs an instance of <code>UnActionSoldatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnActionSoldatException(String msg) {
        super(msg);
    }
}
