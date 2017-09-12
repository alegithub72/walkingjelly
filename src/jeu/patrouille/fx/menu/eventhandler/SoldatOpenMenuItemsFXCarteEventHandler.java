/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;


import java.net.URL;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import jeu.patrouille.fx.board.FXMenuItemsDossier;




/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsFXCarteEventHandler implements javafx.event.EventHandler<MouseEvent>{

 

    AudioClip media;
    boolean b;
    FXMenuItemsDossier menu;
    public SoldatOpenMenuItemsFXCarteEventHandler(FXMenuItemsDossier menu) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("clickSol.wav");
     media=new AudioClip(url.toString());     
     media.setVolume(0.4);    
     this.menu=menu;
     b=false;
    }

    @Override
    public void handle(MouseEvent event) {
     

          
           
           if (event.getButton() == MouseButton.SECONDARY
                   && b) {                
                menu.openCurrentSoldatMenuItems(event.getSceneX(),event.getSceneY());
                media.play();
                b=!b;
           }  else if(event.getButton()==MouseButton.SECONDARY
                   && !b ){
               b=!b;
                menu.closeFXCarteMenuItems();
                menu.sendMessageToPlayer("");
           }
       

        
        
        
        event.consume();
    }
    
    
    
}
