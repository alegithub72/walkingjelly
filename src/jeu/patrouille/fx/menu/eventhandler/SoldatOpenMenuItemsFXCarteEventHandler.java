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




/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 
    FXCarte fxcarte;
    AudioClip media;
    boolean b;
    public SoldatOpenMenuItemsFXCarteEventHandler(FXCarte fxcarte) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("clickSol.wav");
     media=new AudioClip(url.toString());     
     media.setVolume(0.4);    
     this.fxcarte=fxcarte;
     b=false;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
           fxcarte.setCursor(Cursor.HAND);
           if (event.getButton() == MouseButton.SECONDARY
                   && b) {                
                fxcarte.openCurrentSoldatMenuItems(event.getSceneX(),event.getSceneY());
                media.play();
                b=!b;
           }  else if(event.getButton()==MouseButton.SECONDARY
                   && !b ){
               b=!b;
                fxcarte.closeFXCarteMenuItems();
                fxcarte.sendMessageToPlayer("");
           }
       

        
        
        
        event.consume();
    }
    
    
    
}
