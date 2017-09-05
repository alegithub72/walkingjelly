/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.AbstractMenuItemButton;

/**
 *
 * @author appleale
 */
public class MenuItemChangeStatusOnly implements EventHandler<MouseEvent>{
    AbstractMenuItemButton item;
    FXPlanche fxpl;
    public MenuItemChangeStatusOnly(AbstractMenuItemButton item,FXPlanche fxpl) {
        this.fxpl=fxpl;
        this.item=item;
        item.updateState();

    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getButton()==MouseButton.SECONDARY){
            item.changeStates();
            
        }else {

        fxpl.sendMessageToPlayer("Temp insufficient!!Change le statu aved droite button.", Color.YELLOW);


        }
    }
    
}
