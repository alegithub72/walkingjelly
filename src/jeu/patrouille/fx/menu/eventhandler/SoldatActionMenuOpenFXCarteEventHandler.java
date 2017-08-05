/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import jeu.patrouille.fx.board.FXPlanche;



/**
 *
 * @author appleale
 */
public class SoldatActionMenuOpenFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXPlanche pl;
    public SoldatActionMenuOpenFXCarteEventHandler(FXPlanche pl) {

    this.pl=pl;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
           if (event.getButton() == MouseButton.PRIMARY) {
                pl.closeFXCarteMenuItems();

           }
           if (event.getButton() == MouseButton.SECONDARY) {
                 pl.closeFXCarteMenuItems();
                 pl.openFXCarteMenuItems();
           }  
       

        
        
        
        event.consume();
    }
    
    
    
}
