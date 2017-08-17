/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

import jeu.patrouille.fx.pieces.FXUSSoldat;



/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsEventHandler  implements javafx.event.EventHandler<MouseEvent>{

    FXSoldat s;
    FXCarte fxcarte;
    public SoldatOpenMenuItemsEventHandler(FXSoldat s,FXCarte fxcarte) {
     
    this.s=s;
    this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
        
        
        if(event.getButton()==MouseButton.PRIMARY) {
               fxcarte.openSoldatMenuItems(s);
               
             
          
        }
        else if(event.getButton()==MouseButton.SECONDARY){
          
     
        }
        
        
        
        event.consume();
    }
    
    
    
}
