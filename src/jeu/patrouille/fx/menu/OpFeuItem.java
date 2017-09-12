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
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class OpFeuItem extends MenuItemButton{
    
    public OpFeuItem(FXSoldat fxs,FXCarte fxcarte){
        super(ActionType.OCCASION_DE_FEU,fxs,fxcarte);
    }

    @Override
    public BaseAction buildMenuItemAction() {
       BaseAction act=new BaseAction(ActionType.PA_ACTION, -1, -1, -1, -1, fxs.getSoldat(), null);
       return act;
    }



    @Override
    public boolean isDisabledItem() {
        Soldat s=fxs.getSoldat();
        try {
            return !s.isTempDisponiblePour(actionType);
        } catch (ModeDeFeuException ex) {
            throw new RuntimeException(ex);
        }
    }


    
}
