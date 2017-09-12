/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.RechargeArmeAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class LoadMagazineItem extends MenuItemButton{
    public LoadMagazineItem(FXSoldat fxs,FXCarte fxcarte){
        super(ActionType.ARME_RECHARGE,fxs,fxcarte);
    }

    public LoadMagazineItem() {
        super(ActionType.ARME_RECHARGE, null,null);
    }
 
    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction act=new RechargeArmeAction(fxs.getSoldat(), null);
        return act;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  

    @Override
    public boolean isDisabledItem() {
        Soldat s=fxs.getSoldat();
        try {
            return ((s.isBraceGaucheBlesse() &&
                    s.isBrasDroiteBlesse() )
                    || !s.isTempDisponiblePour(actionType));
            } catch (ModeDeFeuException ex) {
           throw new RuntimeException(ex);
        }
    }


    
}
