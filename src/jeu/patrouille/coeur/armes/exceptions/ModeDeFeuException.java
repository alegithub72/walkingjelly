/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.armes.exceptions;

/**
 *
 * @author Alessio Sardaro
 */
public class ModeDeFeuException extends Exception {

    /**
     * Creates a new instance of <code>ModeDeFeuException</code> without detail
     * message.
     */
    public ModeDeFeuException() {
    }

    /**
     * Constructs an instance of <code>ModeDeFeuException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ModeDeFeuException(String msg) {
        super(msg);
    }
}
