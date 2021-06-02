/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class ScrollKeyEventHandler implements EventHandler<KeyEvent> {

    FXCarte fxcarte;

    public ScrollKeyEventHandler(FXCarte fxcarte) {
        this.fxcarte = fxcarte;
    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println("------------------>keyyy type<--------------------"+event.getEventType());        
        if(event.getCode()==KeyCode.UP) 
            fxcarte.scrollCanvas(0, 10);
        else if(event.getCode()==KeyCode.DOWN)
            fxcarte.scrollCanvas(0, -10);
        else if(event.getCode()==KeyCode.LEFT) 
            fxcarte.scrollCanvas(10, 0);
        else if(event.getCode()==KeyCode.RIGHT)
            fxcarte.scrollCanvas(-10, 0);

  


    }

}
