/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import java.net.URL;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class EndTurnButtonEventHandler implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    Sprite s;
    AudioClip media;
    public EndTurnButtonEventHandler(FXCarte fxcarte,Sprite s){
        this.fxcarte = fxcarte;
        this.s = s;
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("plich.aif");
        media = new AudioClip(url.toString());
    }
    @Override
    public void handle(MouseEvent event) {
            
            
            if(event.getEventType()==MouseEvent.MOUSE_PRESSED){
                media.play();
            s.setFrame(1);
            }
            if(event.getEventType()==MouseEvent.MOUSE_RELEASED ) 
                s.setFrame(0);
            event.consume();

     }
    
    
}
