/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class EndTurnEventHandler implements EventHandler<MouseEvent>{
    FXPlanche fxpl;
    Sprite s;
    public EndTurnEventHandler(FXPlanche fxpl,Sprite s){
    this.fxpl=fxpl;
    this.s=s;
    
    }
    @Override
    public void handle(MouseEvent event) {
            System.out.println(event);
            
            if(event.getEventType()==MouseEvent.MOUSE_PRESSED){
            fxpl.playFXCarteTurn();
            s.setFrame(1);
            }
            if(event.getEventType()==MouseEvent.MOUSE_RELEASED ) 
                s.setFrame(0);
            event.consume();
     }
    
    
}
