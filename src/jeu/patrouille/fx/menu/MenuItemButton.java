/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;
import java.net.URL;
import javafx.scene.media.AudioClip;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.pieces.FXSoldat;
/**
 *
 * @author appleale
 */
public abstract class MenuItemButton extends AbstractMenuItemButton{
    FXSoldat fxs;

    public MenuItemButton(ActionType actionType,FXSoldat fxs) {
        super(actionType);
        this.fxs = fxs;
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("plich.aif");
        media = new AudioClip(url.toString());

        url = classLoader.getResource("clunck.wav");
        media2 = new AudioClip(url.toString());
        media.setVolume(0.8);
        media2.setVolume(0.8);     
    }

    public FXSoldat getFXSoldat() {
        return fxs;
    }
     public abstract void changeStates();
       
    
} 
