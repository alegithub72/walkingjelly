/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class EndAnimPauseHandler implements EventHandler<ActionEvent> {

    FXCarte fxcarte;

    public EndAnimPauseHandler(FXCarte fxcarte) {
        this.fxcarte = fxcarte;

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType() == ActionEvent.ACTION) {
            fxcarte.setAnimOn(false);

        }
        event.consume();
    }

}
