/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.board.FXCarte;
/**
 *
 * @author appleale
 */
public class ItemMenuRangeDisplayHandler implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    boolean outOfScroll=false;
    int count=0;
    ActionType actionType;
    public ItemMenuRangeDisplayHandler(FXCarte fxcarte,ActionType actionType){
        this.fxcarte=fxcarte;
        this.actionType=actionType;
    }
  
    @Override
    public void handle(MouseEvent event) {
         count++;
         if (!fxcarte.scrollCanvasBorder(event.getSceneX(), event.getSceneY())) {
             
           if(actionType==ActionType.MARCHE  || 
                   actionType==ActionType.COURS) 
               fxcarte.displayMarcheRangeAction(event.getSceneX(),event.getSceneY());
           else if(actionType==ActionType.FEU)
               fxcarte.displayFeuRangeAction(event.getSceneX(), event.getSceneY());
           else if(actionType==ActionType.BANDAGE || actionType==ActionType.ARME_RECHARGE)
               fxcarte.displayRangeAction(event.getSceneX(), event.getSceneY(),actionType);
         }
        if(count>2) {
                count=0;
                fxcarte.refreshCarte();
            }
         
         event.consume();
    }
    
 



    
}
