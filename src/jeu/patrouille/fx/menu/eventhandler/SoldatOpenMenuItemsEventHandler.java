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
import jeu.patrouille.fx.pieces.FXSoldat;




/**
 *
 * @author appleale
 */
public class SoldatOpenMenuItemsEventHandler  implements javafx.event.EventHandler<MouseEvent>{

    FXSoldat sfx;
    FXMenuItemsDossier menu;
    AudioClip media;
    public SoldatOpenMenuItemsEventHandler(FXSoldat s,FXMenuItemsDossier menu) {
    ClassLoader classLoader = getClass().getClassLoader();
     URL url=classLoader.getResource("clickSol.wav");
     media=new AudioClip(url.toString());     
     media.setVolume(0.4);
    this.sfx=s;
    this.menu=menu;
    }

    @Override
    public void handle(MouseEvent event) {
        
        
        if(event.getButton()==MouseButton.PRIMARY) {
               menu.openSoldatMenuItems(sfx);
               media.play();
             
          
        }
        else if(event.getButton()==MouseButton.SECONDARY){
          
     
        }
        
        
        
        event.consume();
    }
    
    
    
}
