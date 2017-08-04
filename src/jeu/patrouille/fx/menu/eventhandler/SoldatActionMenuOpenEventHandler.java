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
public class SoldatActionMenuOpenEventHandler implements javafx.event.EventHandler<MouseEvent>{

    FXSoldat s;
    FXPlanche pl;
    public SoldatActionMenuOpenEventHandler(FXSoldat s,FXPlanche pl) {
    this.s=s;
    this.pl=pl;
    }

    @Override
    public void handle(MouseEvent event) {
        
        pl.defaceMenuItems();
        if(event.getButton()==MouseButton.PRIMARY) {
            pl.getFXCarte().setSelectionee(s);
            pl.imprimerProfile();
            pl.buildMenuItem(s);
            pl.sendMessageToPlayer(s.getSoldat().getNomDeFamilie()+" "+s.getSoldat().getNom());
          
        }
        else if(event.getButton()==MouseButton.SECONDARY){
            pl.closeMenuItems();
        }
        
        
        
        event.consume();
    }
    
    
    
}
