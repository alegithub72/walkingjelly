/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.FeuAction;
import jeu.patrouille.coeur.actions.enums.OrdreAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.pieces.FXUSSoldat;

/**
 *
 * @author appleale
 */
public class FeuItem extends SoldatMenuItem{
    public FeuItem(FXSoldat fxs){
        super(OrdreAction.FEU,fxs);
    }

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction act=new FeuAction( -1,  -1,   fxs.getSoldat(), null);
        return act;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
