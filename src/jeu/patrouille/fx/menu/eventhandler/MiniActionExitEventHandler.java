/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class MiniActionExitEventHandler implements EventHandler<MouseEvent> {
    
    BaseAction act;
    FXCarte fxcarte;
    public MiniActionExitEventHandler(BaseAction act,FXCarte fxcarte) {
        this.act=act;
        this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
        
   
             if(event.getEventType()==MouseEvent.MOUSE_EXITED) 
                 fxcarte.removeMiniIcon();
                event.consume();
        
    }
     
    
    
    
}
