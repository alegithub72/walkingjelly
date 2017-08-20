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
public class NotSautOrCourseSoldatException extends Exception {

    /**
     * Creates a new instance of <code>NotSautOrCourseSoldatException</code>
     * without detail message.
     */
    public NotSautOrCourseSoldatException() {
    }

    /**
     * Constructs an instance of <code>NotSautOrCourseSoldatException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotSautOrCourseSoldatException(String msg) {
        super(msg);
    }
}
