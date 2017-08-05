/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ScrollEventHandler implements EventHandler<MouseEvent> {

    FXCarte fxcarte;

    public ScrollEventHandler(FXCarte fxcarte) {
        this.fxcarte = fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
        fxcarte.scrollCanvas(event.getSceneX(), event.getSceneY());
        event.consume();


    }

}
