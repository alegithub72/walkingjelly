/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXPlanche;
/**
 *
 * @author appleale
 */
public class ItemMenuRangeDisplayHandler implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    boolean outOfScroll=false;
    int count=0;
    public ItemMenuRangeDisplayHandler(FXCarte fxcarte,int actionType){
        this.fxcarte=fxcarte;
    }
  
    @Override
    public void handle(MouseEvent event) {
         count++;
         if (!fxcarte.scrollCanvas(event.getSceneX(), event.getSceneY())) {
            fxcarte.displayMarcheRangeAction(event.getSceneX(),event.getSceneY());


         }
        if(count>2) {
                count=0;
                fxcarte.refreshCarte();
            }
         
         event.consume();
    }
    
 



    
}
