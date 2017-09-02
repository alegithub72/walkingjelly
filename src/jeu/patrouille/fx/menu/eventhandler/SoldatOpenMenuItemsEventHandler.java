/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import java.net.URL;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

import jeu.patrouille.fx.pieces.FXUSSoldat;



/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsEventHandler  implements javafx.event.EventHandler<MouseEvent>{

    FXSoldat s;
    FXCarte fxcarte;
    AudioClip media;
    public SoldatOpenMenuItemsEventHandler(FXSoldat s,FXCarte fxcarte) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("clickSol.wav");
     media=new AudioClip(url.toString());     
     media.setVolume(0.4);
    this.s=s;
    this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
        
        fxcarte.setCursor(Cursor.HAND);
        if(event.getButton()==MouseButton.PRIMARY) {
               fxcarte.openSoldatMenuItems(s);
               media.play();
             
          
        }
        else if(event.getButton()==MouseButton.SECONDARY){
          
     
        }
        
        
        
        event.consume();
    }
    
    
    
}
