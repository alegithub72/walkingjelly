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
            item.setFrame(0);
            double x = event.getSceneX();
            double y = event.getSceneY();
            double tmpI = (y / FXCarte.TILE_SIZE);
            double tmpJ = (x / FXCarte.TILE_SIZE);
            System.out.println(x + "," + y);
            int i1 = ((int) tmpI) + fxpl.getFXCartePosI();
            int j1 = ((int) tmpJ) + fxpl.getFXCartePosJ();
            System.out.println("walk here i1=" + i1 + " j1=" + j1);
            fxpl.setFXCarteHelperArrivalCarteCoord(i1, j1);
            fxpl.deactiveFXCarteRangePointer();
           
            fxpl.setFXCarteOnMouseClicked(new SoldatActionMenuOpenFXCarteEventHandler(fxpl));
            fxpl.setFXCarteCursor(Cursor.DEFAULT);
            if (!fxpl.getFXCarteActionHelper().isCommanNotvalid()) {
                
                fxpl.addFXCarteSoldataSelectioneeAction();

                fxpl.imprimerProfile();
                fxpl.visualizeActionBarActual();
                fxpl.closeFXCarteMenuItems();
                fxpl.setFXCarteOnMouseMoved(new ScrollEventHandler(fxpl.getFXCarte()));
                //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
                fxpl.sendMessageToPlayer( fxpl.getFXCarteActionHelper().toString());
                fxpl.resetFXCarteHelperAction();
            
            } else {
                fxpl.setFXCarteOnMouseMoved(null);
                fxpl.setFXCarteOnMouseMoved(new ScrollEventHandler(fxpl.getFXCarte()));
                fxpl.setFXCarteCursor(Cursor.DEFAULT);                
                fxpl.sendMessageToPlayer("Commando non valido");
            }
        }else if(event.getButton()==MouseButton.SECONDARY){
                fxpl.getFXCarteActionHelper().setCommanNotvalid(true);            
                fxpl.setFXCarteOnMouseMoved(null);
                fxpl.setFXCarteOnMouseClicked(new SoldatActionMenuOpenFXCarteEventHandler( fxpl));                
                fxpl.setFXCarteOnMouseMoved(new ScrollEventHandler(fxpl.getFXCarte()));
                fxpl.setFXCarteCursor(Cursor.DEFAULT);                
                fxpl.sendMessageToPlayer("Commando Annullato");            
        }
        event.consume();
               

    }
    
}
