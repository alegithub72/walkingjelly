 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.AbstractMenuItemButton;
import jeu.patrouille.fx.menu.MenuItemButton;
   
/**
 *
 * @author appleale
 */
public class SoldatClickedOnMenuItemsEventHandler  implements EventHandler<MouseEvent>{
    
    AbstractMenuItemButton item;  
    AbstractMenuItemButton mainMenu[];
    FXCarte fxcarte;
    int n;
    public SoldatClickedOnMenuItemsEventHandler(AbstractMenuItemButton item,FXCarte fxcarte) {
        this.item=item;
        n=1;
        this.fxcarte=fxcarte;
    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY  ){

           if(!fxcarte.isFXHelperActionSeletiones()) 
               fxcarte.clickOnButtonItems(item);
            
        }else if(event.getButton()==MouseButton.SECONDARY){
            item.changeStates();
            if(item.getLink()!=null) item.getLink().updateState();
            MenuItemButton sItem=(MenuItemButton)item;
            fxcarte.openCurrentSoldatMenuItems(sItem.getFXSoldat().getTranslateX(),
                    sItem.getTranslateY());
        }
        
      
    event.consume();
    }
    
}
