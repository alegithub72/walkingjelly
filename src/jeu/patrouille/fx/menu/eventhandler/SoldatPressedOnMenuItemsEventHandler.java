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
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class SoldatPressedOnMenuItemsEventHandler  implements EventHandler<MouseEvent>{
    
    Sprite sp;
 
    public SoldatPressedOnMenuItemsEventHandler(Sprite sp) {
    
    this.sp=sp;


    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY ){

            sp.setFrame(1);
            
        }
        
   
    }
    
}
