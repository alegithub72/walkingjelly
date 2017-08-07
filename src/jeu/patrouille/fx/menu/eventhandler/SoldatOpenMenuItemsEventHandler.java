/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.pieces.FXSoldat;



/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsEventHandler  implements javafx.event.EventHandler<MouseEvent>{

    FXSoldat s;
    FXPlanche pl;
    public SoldatOpenMenuItemsEventHandler(FXSoldat s,FXPlanche pl) {
     
    this.s=s;
    this.pl=pl;
    }

    @Override
    public void handle(MouseEvent event) {
        
        
        if(event.getButton()==MouseButton.PRIMARY) {
               pl.openSoldatMenuItems(s);
               
             
          
        }
        else if(event.getButton()==MouseButton.SECONDARY){
          
     
        }
        
        
        
        event.consume();
    }
    
    
    
}
