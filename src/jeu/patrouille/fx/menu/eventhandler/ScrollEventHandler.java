/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
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
        //removeMenuItem();
        if (!fxcarte.getRootGroup().getChildren().contains(fxcarte.getArrow())) {
           Sprite arrow = new Sprite(100, 100, 100, 100, "arrowPng.png", null);
           fxcarte.setArrow(arrow);
         //  fxcarte.getRootGroup().getChildren().add(arrow);
       }

        fxcarte.scrollCanvas(event.getSceneX(), event.getSceneY());
        event.consume();
        //System.out.println("newposY=" + posI + ",newposX=" + posJ + "");

    }

}
