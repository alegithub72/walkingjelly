/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.fx.board.FXCarte;

import jeu.patrouille.fx.board.FXPlanche;



/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXCarte fxcarte;
    public SoldatOpenMenuItemsFXCarteEventHandler(FXCarte fxcarte) {
    
    this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
           fxcarte.setCursor(Cursor.HAND);
           if (event.getButton() == MouseButton.SECONDARY
                   && event.getClickCount()<=1 ) {
                
                fxcarte.openCurrentSoldatMenuItems(event.getSceneX(),event.getSceneY());
               
           }  else if(event.getButton()==MouseButton.PRIMARY
                   && event.getClickCount()>1){
                fxcarte.closeFXCarteMenuItems();
           }
       

        
        
        
        event.consume();
    }
    
    
    
}
