/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class EndTurnButtonEventHandler implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    Sprite s;
    public EndTurnButtonEventHandler(FXCarte fxcarte,Sprite s){
    this.fxcarte=fxcarte;
    this.s=s;
    
    }
    @Override
    public void handle(MouseEvent event) {
            
            
            if(event.getEventType()==MouseEvent.MOUSE_PRESSED){
          
            s.setFrame(1);
            }
            if(event.getEventType()==MouseEvent.MOUSE_RELEASED ) 
                s.setFrame(0);
            event.consume();

     }
    
    
}
