/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Piece;



/**
 *
 * @author appleale
 */
public abstract class Joeur extends GeneriqueJoeurs{

    public Joeur(int jeur,Carte c) {
        super(jeur,c);
    }
    String textCommand;

    public String getTextCommand() {
        return textCommand;
    }
    
    

    
    
}
