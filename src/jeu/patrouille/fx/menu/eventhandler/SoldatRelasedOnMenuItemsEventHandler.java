/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;

/**
 *
 * @author appleale
 */
public class SoldatRelasedOnMenuItemsEventHandler   implements EventHandler<MouseEvent>{
    
    MenuItem item;
    MenuItem mainMenu[];
    FXPlanche fxpl;
    public SoldatRelasedOnMenuItemsEventHandler(MenuItem item,
            FXPlanche fxpl) {
    
    this.item=item;
    this.fxpl=fxpl;
    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType()==MouseEvent.MOUSE_RELEASED &&
                event.getButton()==MouseButton.PRIMARY) 
        item.setFrame(0);
        

    }
    
}