/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.OrdreAction;
import jeu.patrouille.fx.pieces.FXSoldat;

import jeu.patrouille.fx.pieces.FXUSSoldat;

/**
 *
 * @author appleale
 */
public class DisableMenuItems extends SoldatMenuItem{
    public DisableMenuItems(FXSoldat fxs){
        super(OrdreAction.PA_ACTION,fxs);
        setFrame(1);
        
    }

    @Override
    public BaseAction buildMenuItemAction() {
       BaseAction lstact= fxs.getSoldat().lastAction();
       BaseAction act=new BaseAction(OrdreAction.PA_ACTION,-1, -1, -1, -1, fxs.getSoldat(), null);
       return act;
    }
    
}
