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
public class IncoscientSoldatException extends Exception {

    /**
     * Creates a new instance of <code>IncoscientSoldatException</code> without
     * detail message.
     */
    public IncoscientSoldatException() {
    }

    /**
     * Constructs an instance of <code>IncoscientSoldatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IncoscientSoldatException(String msg) {
        super(msg);
    }
}
