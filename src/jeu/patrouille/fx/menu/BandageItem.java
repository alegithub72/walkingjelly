/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import jeu.patrouille.coeur.actions.BandageAction;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class BandageItem extends MenuItemButton{
    public BandageItem(FXSoldat fxs,FXCarte fxcarte){
        super(ActionType.BANDAGE,fxs,fxcarte);
    }

    public BandageItem() {
        super(ActionType.BANDAGE, null,null);
    }

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction act=new BandageAction(fxs.getSoldat(), null);
        return act;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void changeStates() {

    }    

    @Override
    public boolean isDisabledItem() {
        Soldat s=fxs.getSoldat();
        try {
            return ( s.isBraceGaucheBlesse() &&
                    s.isBrasDroiteBlesse() )
                    || !s.isTempDisponiblePour(actionType);
        } catch (ModeDeFeuException ex) {
           throw new RuntimeException(ex);
        }
    }


    
}
