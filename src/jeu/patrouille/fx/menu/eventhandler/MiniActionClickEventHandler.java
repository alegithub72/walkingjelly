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
public class MiniActionClickEventHandler implements EventHandler<MouseEvent> {
    
    BaseAction act;
    FXCarte fxcarte;
    public MiniActionClickEventHandler(BaseAction act,FXCarte fxcarte) {
        this.act=act;
        this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
        
            if(event.getButton()==MouseButton.PRIMARY) 
                fxcarte.visualizeMiniIconAction(act);
            event.consume();
        
    }
     
    
    
    
}
