/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXPlanche;
/**
 *
 * @author appleale
 */
public class ItemMenuRangeDisplayHandler implements EventHandler<MouseEvent>{
    FXPlanche fxpl;
    boolean outOfScroll=false;
    int count=0;
    public ItemMenuRangeDisplayHandler(FXPlanche fxpl,int actionType){
        this.fxpl=fxpl;
    }
    @Override
    public void handle(MouseEvent event) {
         count++;
         if (!fxpl.scrollFXCarteCanvas(event.getSceneX(), event.getSceneY())) {
            fxpl.displayMarcheRangeAction(event.getSceneX(),event.getSceneY());


         }
        if(count>2) {
                count=0;
                fxpl.refreshFXCarteCarte();
            }
         
         event.consume();
    }
    
 



    
}
