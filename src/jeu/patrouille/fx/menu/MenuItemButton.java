/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;
import java.net.URL;
import javafx.scene.effect.MotionBlur;
import javafx.scene.media.AudioClip;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.eventhandler.MenuItemChangeStatusOnly;
import jeu.patrouille.fx.menu.eventhandler.SoldatClickedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXSoldat;
/**
 *
 * @author appleale
 */
public abstract class MenuItemButton extends AbstractMenuItemButton{
    FXSoldat fxs;
    public MenuItemButton(ActionType actionType,FXSoldat fxs,FXCarte fxcarte) {
        super(actionType,fxcarte);
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

     
    public void disableItem(){
               
                MotionBlur mt=new MotionBlur(45, 8);
               // m.setEffect(new GaussianBlur());
             
                this.setEffect(mt);
                this.setOnMouseClicked(new MenuItemChangeStatusOnly(this,fxcarte.getMenu()));    
    
    }       
    public abstract boolean isDisabledItem();

    @Override
    public void changeStates() {
      return;
    }

    @Override
    public void updateState() {
        return ;
    }

   
    
    @Override
    public void enable() {
        if(isDisabledItem()) disableItem();
        else  enableMenuItem();
    }
    void enableMenuItem(){
            setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(this,  fxcarte));
            setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(this));
            setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(this));  
            setEffect(null);
    
    }   
    
} 
