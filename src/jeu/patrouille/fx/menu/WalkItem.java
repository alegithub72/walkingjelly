/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.MarcheAction;
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
public class WalkItem extends MenuItemButton{
    
    int n;    
    public WalkItem(FXSoldat fxs,FXCarte fxcarte){
        super(ActionType.MARCHE,fxs,fxcarte);

    }

    public WalkItem() {
        super(ActionType.MARCHE, null, null);
    }

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction lsact=fxs.getSoldat().lastMovement();
        BaseAction action=null;
        //TODO considerare anche crawl
        if(lsact!=null)
         action=new MarcheAction(lsact.getI1(),lsact.getJ1(),-1,-1,fxs.getSoldat());
        else action = new MarcheAction(fxs.getSoldat().getI(),fxs.getSoldat().getJ(),-1,-1,fxs.getSoldat());

        return action;
        
    }




    @Override
    public void changeStates() {

        return;
    }

    @Override
    public void updateState() {
        Soldat s=fxs.getSoldat();
        if(s.getPose()==Piece.Pose.DROIT)
            actionType=ActionType.MARCHE;
        else if(s.getPose()==Piece.Pose.PRONE)
            actionType=ActionType.COUCHER;
        
        buildFrameImages(MenuImageChargeur.getImageMenu(this.actionType));
     
        
        
    }

    @Override
    public boolean isDisabledItem() {
          Soldat s=fxs.getSoldat();
        try {
            return (s.isImmobilize()||
                    !s.isTempDisponiblePour(actionType) ||
                    s.getPose()==Piece.Pose.GENOUCS || 
                    s.getPose()==Piece.Pose.PRONE);
        } catch (ModeDeFeuException ex) {
            throw new  RuntimeException(ex);

        }
    }
    
    
    
}
