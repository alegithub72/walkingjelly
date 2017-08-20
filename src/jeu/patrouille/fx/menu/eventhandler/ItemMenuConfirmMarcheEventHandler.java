/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;

/**
 *
 * @author appleale
 */
public class ItemMenuConfirmMarcheEventHandler  implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    MenuItem item;
    public ItemMenuConfirmMarcheEventHandler(MenuItem item,FXCarte fxcarte) {
        this.fxcarte=fxcarte;
        this.item=item;
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO modificare il cursore
       
        if (event.getButton() == MouseButton.PRIMARY) {
            
            try {
                if(item.getActionType()==BaseAction.MARCHE)
                    fxcarte.confirmMarcheActionCommand(item, event.getSceneX(), event.getSceneY());
                else if(item.getActionType()==BaseAction.FEU){
                    //TODO something
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            fxcarte.refreshCarte();
        }else if(event.getButton()==MouseButton.SECONDARY){
            fxcarte.annulleCommand();
            fxcarte.refreshCarte();
        }
        
        event.consume();
               

    }
    
}
