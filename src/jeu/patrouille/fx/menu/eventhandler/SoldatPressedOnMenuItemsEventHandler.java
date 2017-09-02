/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import java.net.URL;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class SoldatPressedOnMenuItemsEventHandler  implements EventHandler<MouseEvent>{
    
    Sprite sp;

    AudioClip media;
    AudioClip media2;
    public SoldatPressedOnMenuItemsEventHandler(Sprite sp) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("plich.aif");
     media=new AudioClip(url.toString());
     
     url=classLoader.getResource("clunck.wav");
     media2=new AudioClip(url.toString());
     media.setVolume(0.8);
     media2.setVolume(0.8);
    this.sp=sp;


    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {

        if(event.getButton()==MouseButton.PRIMARY ){
        media.play();
        sp.setFrame(1);            
        }else if(event.getButton()==MouseButton.SECONDARY){
            media2.play();
        }
        
   
    }
    
}
