/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.pieces.FXUSSoldat;
/**
 *
 * @author appleale
 */
public abstract class SoldatMenuItem extends MenuItem{
    FXUSSoldat fxs;
    public SoldatMenuItem(int actionType,FXUSSoldat fxs) {
        super(actionType);
        this.fxs=fxs;
    }

    public FXUSSoldat getFXSoldat() {
        return fxs;
    }
    
    
    
}
