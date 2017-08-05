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
public class SoldatClickOnMenuItemsEventHandler   implements EventHandler<MouseEvent>{
    
    MenuItem item;
    MenuItem mainMenu[];
    FXPlanche fxpl;
    public SoldatClickOnMenuItemsEventHandler(MenuItem item,MenuItem[] mainMenu,
            FXPlanche fxpl) {
    
    this.item=item;
    this.mainMenu=mainMenu;
    this.fxpl=fxpl;
    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY){

        fxpl.clickOnButtonItems(item);
            
       
        }
      
    event.consume();
    }
    
}
