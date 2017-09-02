/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class SoldatRelasedOnMenuItemsEventHandler   implements EventHandler<MouseEvent>{
    
    Sprite sp;

    public SoldatRelasedOnMenuItemsEventHandler(Sprite sp) {
    
    this.sp=sp;

    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType()==MouseEvent.MOUSE_RELEASED &&
                event.getButton()==MouseButton.PRIMARY) 
        sp.setFrame(0);
        

    }
    
}
