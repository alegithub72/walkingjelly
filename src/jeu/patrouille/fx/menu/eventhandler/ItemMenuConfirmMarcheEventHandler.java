/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.WalkItem;

/**
 *
 * @author appleale
 */
public class ItemMenuConfirmMarcheEventHandler  implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
WalkItem item;
    public ItemMenuConfirmMarcheEventHandler(WalkItem walk,FXCarte fxcarte) {
        this.fxcarte=fxcarte;
        this.item=walk;
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO modificare il cursore
        if (event.getButton() == MouseButton.PRIMARY) {
            fxcarte.confirmMarcheActionCommand(item, event.getSceneX(), event.getSceneY());
            fxcarte.refreshCarte();
        }else if(event.getButton()==MouseButton.SECONDARY){
            fxcarte.annulleCommand();
            fxcarte.refreshCarte();
        }
        
        event.consume();
               

    }
    
}
