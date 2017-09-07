/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.scene.control.Label;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class WalkItem extends MenuItemButton{
    public WalkItem(FXSoldat fxs,Label label){
        super(ActionType.MARCHE,fxs,label);
        
    }

    public WalkItem() {
        super(ActionType.MARCHE, null, null);
    }

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction lsact=fxs.getSoldat().lastAction(ActionType.MARCHE);
        BaseAction action=null;
        if(lsact!=null)
         action=new MarcheAction(lsact.getI1(),lsact.getJ1(),-1,-1,fxs.getSoldat());
        else action = new MarcheAction(fxs.getSoldat().getI(),fxs.getSoldat().getJ(),-1,-1,fxs.getSoldat());

        return action;
        
    }
        public static void main(String[] args) {}



    @Override
    public void changeStates() {
 
    }

    @Override
    public void updateState() {
        return;
    }
    
}
