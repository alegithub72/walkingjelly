/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.event.EventType;
//import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXPlanche;

/**
 *
 * @author appleale
 */
public class EndTurnEventHandler implements EventHandler<MouseEvent>{
    FXPlanche fxpl;
    public EndTurnEventHandler(FXPlanche fxpl){
        this.fxpl=fxpl;
    }

    @Override
    public void handle(MouseEvent event) {
       if(event.getButton()==MouseButton.PRIMARY &&
               event.getEventType()==MouseEvent.MOUSE_CLICKED){
           fxpl.endTurn();
       
       }
        
        
    }
    
    
    
}
