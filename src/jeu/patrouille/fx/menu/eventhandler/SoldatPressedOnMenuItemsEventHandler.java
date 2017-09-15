/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.menu.MenuItemButton;

/**
 *
 * @author appleale
 */
public class SoldatPressedOnMenuItemsEventHandler  implements EventHandler<MouseEvent>{
    
    MenuItemButton bt;

    public SoldatPressedOnMenuItemsEventHandler(MenuItemButton bt) {

    this.bt=bt;


    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY )
            bt.pressPrimary();
        else if(event.getButton()==MouseButton.SECONDARY)
           bt.pressSecondary();
        
        
   
    }
    
}
