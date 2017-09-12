/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXMenuItemsDossier;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.AbstractMenuItemButton;

/**
 *
 * @author appleale
 */
public class MenuItemChangeStatusOnly implements EventHandler<MouseEvent>{
    AbstractMenuItemButton item;
    FXMenuItemsDossier menu;
    public MenuItemChangeStatusOnly(AbstractMenuItemButton item,FXMenuItemsDossier menu) {
        this.menu=menu;
        this.item=item;
        item.updateState();

    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getButton()==MouseButton.SECONDARY){
            item.changeStates();
            
        }else {

        menu.sendMessageToPlayer("Temp insufficient!!Change le statu aved droite button.");


        }
    }
    
}
