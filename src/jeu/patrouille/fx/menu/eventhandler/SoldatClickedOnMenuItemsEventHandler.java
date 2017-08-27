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
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.MenuItem;
   
/**
 *
 * @author appleale
 */
public class SoldatClickedOnMenuItemsEventHandler  implements EventHandler<MouseEvent>{
    
    MenuItem item;  
    MenuItem mainMenu[];
    FXCarte fxcarte;
    public SoldatClickedOnMenuItemsEventHandler(MenuItem item,
            FXCarte fxcarte) {
    
    this.item=item;

    this.fxcarte=fxcarte;
    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY  ){

           if(!fxcarte.isFXHelperActionSeletiones()) fxcarte.clickOnButtonItems(item);
            
        }
        
      
    event.consume();
    }
    
}
