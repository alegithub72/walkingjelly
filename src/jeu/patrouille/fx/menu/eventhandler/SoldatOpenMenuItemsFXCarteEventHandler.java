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
public class SoldatOpenMenuItemsFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXPlanche pl;
    public SoldatOpenMenuItemsFXCarteEventHandler(FXPlanche pl) {

    this.pl=pl;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
 
           if (event.getButton() == MouseButton.SECONDARY
                   && event.getClickCount()<=1 ) {
                pl.openCurrentSoldatMenuItems();
               
           }  else if(event.getButton()==MouseButton.SECONDARY
                   && event.getClickCount()>1){
                pl.closeFXCarteMenuItems();
           }
       

        
        
        
        event.consume();
    }
    
    
    
}
