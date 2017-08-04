/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.pieces.FXSoldat;
/**
 *
 * @author appleale
 */
public abstract class SoldatMenuItem extends MenuItem{
    FXSoldat fxs;
    public SoldatMenuItem(int actionType,FXSoldat fxs) {
        super(actionType);
        this.fxs=fxs;
    }

    public FXSoldat getFXSoldat() {
        return fxs;
    }
    
    
    
}
