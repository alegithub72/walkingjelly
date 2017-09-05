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
public class ImpossibleRechargeArmeException extends Exception {

    /**
     * Creates a new instance of <code>ImpossibleRechargeArmeException</code>
     * without detail message.
     */
    public ImpossibleRechargeArmeException() {
    }

    /**
     * Constructs an instance of <code>ImpossibleRechargeArmeException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ImpossibleRechargeArmeException(String msg) {
        super(msg);
    }
}
