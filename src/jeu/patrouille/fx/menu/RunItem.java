/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.pieces.FXUSSoldat;

/**
 *
 * @author appleale
 */
public class RunItem extends MenuItemButton{
    
    public RunItem(FXSoldat fxs){
        super(ActionType.COURS, fxs);
    }

    @Override
    public BaseAction buildMenuItemAction() {
    return null;
    }

    @Override
    public void changeStates() {
        return;
    }

    @Override
    public void updateState() {
      return;
    }
    
    
}
