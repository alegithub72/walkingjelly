/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author appleale
 */
public class StartDeamonThreadEventHandler implements EventHandler<ActionEvent>{
    Thread thread;
    public StartDeamonThreadEventHandler(Thread thread) {
        this.thread=thread;
    }

    
    
    @Override
    public void handle(ActionEvent event) {
        if(event.getEventType()==ActionEvent.ACTION)
            thread.start();
        
        event.consume();
        
    }
    
}
