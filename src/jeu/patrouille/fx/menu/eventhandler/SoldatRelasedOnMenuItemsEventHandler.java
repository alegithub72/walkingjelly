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
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class SoldatRelasedOnMenuItemsEventHandler   implements EventHandler<MouseEvent>{
    
     MenuItemButton bt;

    public SoldatRelasedOnMenuItemsEventHandler(MenuItemButton bt) {
    
    this.bt=bt;

    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType()==MouseEvent.MOUSE_RELEASED &&
                event.getButton()==MouseButton.PRIMARY) 
        bt.release();
        

    }
    
}
