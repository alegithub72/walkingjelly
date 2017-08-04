/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class WalkItem extends SoldatMenuItem{
    public WalkItem(FXSoldat fxs){
        super(BaseAction.MARCHE,fxs);
        
    }

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction lsact=fxs.getSoldat().lastAction();
         BaseAction action=null;
        if(lsact!=null)
         action=new MarcheAction(lsact.getI1(),lsact.getJ1(),-1,-1,null);
        else action = new MarcheAction(fxs.getSoldat().getI(),fxs.getSoldat().getJ(),-1,-1,fxs.getSoldat());

        return action;
        
    }
        public static void main(String[] args) {}
    
}
