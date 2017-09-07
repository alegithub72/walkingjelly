/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.scene.control.Label;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.pieces.FXSoldat;


/**
 *
 * @author appleale
 */
public class DisableMenuItems extends MenuItemButton{
    public DisableMenuItems(FXSoldat fxs,Label label){
        super(ActionType.PA_ACTION,fxs,label);
        setFrame(1);
        
    }

    @Override
    public BaseAction buildMenuItemAction() {
       BaseAction lstact= fxs.getSoldat().lastAction();
       BaseAction act=new BaseAction(ActionType.PA_ACTION,-1, -1, -1, -1, fxs.getSoldat(), null);
       return act;
    }

    @Override
    public void changeStates() {

    }

    @Override
    public void updateState() {
       return;
    }


    
}
