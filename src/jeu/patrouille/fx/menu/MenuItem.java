/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public abstract class MenuItem extends FXPatrouilleSprite{
    public static int  MENU_H=100;
    public static int MENU_W=100;
    ActionType actionType;
    public MenuItem(ActionType type){
        super(MENU_W,MENU_H,null,null);
        if(type!=null){
            buildFrameImages(MenuImageChargeur.getImageMenu(type));
            actionType=type;
        }
    }
    public void press(){
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
    
    
    
    public  ObservableList<Node>  getChildrens(){
    return this.getChildren();
    }
    public abstract  BaseAction buildMenuItemAction();
    public abstract int changeStates(int n);
}
