/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class RunItem extends SoldatMenuItem{
    
    public RunItem(FXSoldat fxs){
        super(BaseAction.COURS, fxs);
    }

    @Override
    public BaseAction buildMenuItemAction() {
    return null;
    }
    
    
}
