/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.CoursAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class RunItem extends MenuItemButton{
    
    public RunItem(FXSoldat fxs,FXCarte fxcarte){
        super(ActionType.COURS, fxs,fxcarte);
    }

    public RunItem() {
        super(ActionType.COURS, null, null);
    }
    

    @Override
    public BaseAction buildMenuItemAction() {
    Soldat s=fxs.getSoldat();
    return new CoursAction(s.getI(), s.getJ(), -1, -1, s);
    }

    @Override
    public void changeStates() {
        return;
    }

    @Override
    public boolean isDisabledItem() {
        Soldat s=fxs.getSoldat();
        try {
            return (s.isImmobilize()|| !s.isTempDisponiblePour(actionType)|| s.getPose()!=Piece.Pose.DROIT);
        } catch (ModeDeFeuException ex) {
            throw new RuntimeException(ex);
        }
    }


    
    
}
