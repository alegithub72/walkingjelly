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
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.WalkItem;

/**
 *
 * @author appleale
 */
public class ActionConfirmMarcheEventHandler  implements EventHandler<MouseEvent>{
FXCarte fxcarte;
WalkItem item;
    public ActionConfirmMarcheEventHandler(WalkItem walk,FXCarte fxcarte) {
        this.fxcarte=fxcarte;
        this.item=walk;
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO modificare il cursore
        if (event.getButton() == MouseButton.PRIMARY) {
           
            double x = event.getSceneX();
            double y = event.getSceneY();
            double tmpI = (y / FXCarte.TILE_SIZE);
            double tmpJ = (x / FXCarte.TILE_SIZE);
            System.out.println(x + "," + y);
            int i1 = ((int) tmpI) + fxcarte.getPosI();
            int j1 = ((int) tmpJ) + fxcarte.getPosJ();
            System.out.println("walk here i1=" + i1 + " j1=" + j1);
            BaseAction act = fxcarte.getActActualCommad();
            item.setFrame(0);
            act.setI1(i1);
            act.setJ1(j1);
            Soldat s = fxcarte.getFXSoldatSelectionee().getSoldat();
            fxcarte.setOnMouseClicked(new SoldatActionMenuOpenFXCarteEventHandler(fxcarte.getFxpl()));
            fxcarte.setCursor(Cursor.DEFAULT);
            if (!fxcarte.isCommanNotvalid()) {
                
                s.addAction(act);
                fxcarte.getFxpl().imprimerProfile();
                fxcarte.getFxpl().visualizeActionBar(act.getType(), s.actionSize() - 1);
                fxcarte.closeMenuItems();
                fxcarte.setOnMouseMoved(new ScrollEventHandler(fxcarte));
                //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
                fxcarte.getFxpl().sendMessageToPlayer("SUCCESS Action=" + act.toString());
                fxcarte.setActActualCommad(null);
            
            } else {
                fxcarte.setOnMouseMoved(null);
                fxcarte.setOnMouseMoved(new ScrollEventHandler(fxcarte));
                fxcarte.getFxpl().sendMessageToPlayer("Commando non valido");
            }
        }else if(event.getButton()==MouseButton.SECONDARY){
                fxcarte.setCommanNotvalid(true);            
                fxcarte.setOnMouseMoved(null);
                fxcarte.setOnMouseClicked(new SoldatActionMenuOpenFXCarteEventHandler( fxcarte.getFxpl()));                
                fxcarte.setOnMouseMoved(new ScrollEventHandler(fxcarte));
                fxcarte.getFxpl().sendMessageToPlayer("Commando Annullato");            
        }
        event.consume();
               

    }
    
}
