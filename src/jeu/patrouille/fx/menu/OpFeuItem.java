/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class OpFeuItem extends SoldatMenuItem{
    
    public OpFeuItem(FXSoldat fxs){
        super(ActionType.OCCASION_DE_FEU,fxs);
    }

    @Override
    public BaseAction buildMenuItemAction() {
       BaseAction act=new BaseAction(ActionType.PA_ACTION, -1, -1, -1, -1, fxs.getSoldat(), null);
       return act;
    }

    @Override
    public int changeStates(int n) {
        return-1;
    }

    @Override
    public void updateState() {
        return;
    }
    
}
