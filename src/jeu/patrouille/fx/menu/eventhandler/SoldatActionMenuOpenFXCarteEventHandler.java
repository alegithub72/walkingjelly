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
public class SoldatActionMenuOpenFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXPlanche pl;
    public SoldatActionMenuOpenFXCarteEventHandler(FXPlanche pl) {

    this.pl=pl;
    }

    @Override
    public void handle(MouseEvent event) {
        FXSoldat s=pl.getFXCarte().getFXSoldatSelectionee();   
       if(s!=null){
           pl.closeMenuItems();
           if (event.getButton() == MouseButton.PRIMARY) {

           }
           if (event.getButton() == MouseButton.SECONDARY) {
                           
                pl.getFXCarte().setSelectionee(s);
                pl.buildMenuItem(s);               
           }  
       }

        
        
        
        event.consume();
    }
    
    
    
}
