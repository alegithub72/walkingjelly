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
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.fx.board.FXCarte;

import jeu.patrouille.fx.board.FXPlanche;



/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXCarte fxcarte;
    AudioClip media;
    public SoldatOpenMenuItemsFXCarteEventHandler(FXCarte fxcarte) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("clickSol.wav");
     media=new AudioClip(url.toString());     
     media.setVolume(0.4);    
     this.fxcarte=fxcarte;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
           fxcarte.setCursor(Cursor.HAND);
           if (event.getButton() == MouseButton.SECONDARY
                   && event.getClickCount()<=1 ) {
                
                fxcarte.openCurrentSoldatMenuItems(event.getSceneX(),event.getSceneY());
                media.play();
           }  else if(event.getButton()==MouseButton.PRIMARY
                   && event.getClickCount()>1){
                fxcarte.closeFXCarteMenuItems();
           }
       

        
        
        
        event.consume();
    }
    
    
    
}
