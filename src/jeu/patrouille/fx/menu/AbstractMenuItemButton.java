/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public abstract class AbstractMenuItemButton extends FXPatrouilleSprite{
    public static int  MENU_H=100;
    public static int MENU_W=100;
    ActionType actionType;
    AbstractMenuItemButton link;    
    AudioClip media;
    AudioClip media2;    
    public AbstractMenuItemButton(ActionType type,FXCarte fxcarte){
        super(MENU_W,MENU_H,null,fxcarte);
        if(type!=null){
            buildFrameImages(MenuImageChargeur.getImageMenu(type));
            actionType=type;
        }
    }
    public void pressPrimary(){
        media.play();
        setFrame(1);
    }
    public void pressSecondary(){
        media2.play();
        setFrame(1);
    }
    public void release(){
        setFrame(0);
    
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    public void addLink(AbstractMenuItemButton link){
        this.link=link;
    }
    public AbstractMenuItemButton getLink(){
        return link;
    }    
    
    
    public  ObservableList<Node>  getChildrens(){
    return this.getChildren();
    }
    public abstract  BaseAction buildMenuItemAction();
    public abstract void changeStates();
    public abstract void updateState();
    public abstract void enable();
}
