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
public class ActionConfirmMarcheEventHandler  implements EventHandler<MouseEvent>{
    FXPlanche fxpl;
WalkItem item;
    public ActionConfirmMarcheEventHandler(WalkItem walk,FXPlanche fxpl) {
        this.fxpl=fxpl;
        this.item=walk;
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO modificare il cursore
        if (event.getButton() == MouseButton.PRIMARY) {
            fxpl.confirmMarcheActionCommand(item, event.getSceneX(), event.getSceneY());
        }else if(event.getButton()==MouseButton.SECONDARY){
            fxpl.annulleCommand();
        }
        
        event.consume();
               

    }
    
}
