/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions.exceptions;

/**
 *
 * @author appleale
 */
public class MakeActionFailException extends Exception {

    /**
     * Creates a new instance of <code>MakeActionFailException</code> without
     * detail message.
     */
    public MakeActionFailException() {
    }

    /**
     * Constructs an instance of <code>MakeActionFailException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MakeActionFailException(String msg) {
        super(msg);
    }

    public MakeActionFailException(Throwable cause) {
        super(cause);
    }
    
}
