/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class ScrollEventHandler implements EventHandler<ScrollEvent> {

    FXCarte fxcarte;

    public ScrollEventHandler(FXCarte fxcarte) {
        this.fxcarte = fxcarte;
    }

    @Override
    public void handle(ScrollEvent event) {
        if(event.getEventType()==ScrollEvent.SCROLL) 
            fxcarte.scrollCanvas(event.getDeltaX(), event.getDeltaY());

        event.consume();


    }

}
