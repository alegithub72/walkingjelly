/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public abstract class MenuItem extends FXPatrouilleSprite{
    public static int  MENU_H=100;
    public static int MENU_W=100;
    int actionType;
    MenuImageChargeur menuImgChgr;
    public MenuItem(int type){
    super(MENU_W,MENU_H,null,null);
    menuImgChgr=MenuImageChargeur.geInstance();
    buildFrameImages(menuImgChgr.getImage(type));
    actionType=type;
    }
    public void press(){
        setFrame(1);
    }
    public void release(){
        setFrame(0);
    
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
    
    
    
    public  ObservableList<Node>  getChildrens(){
    return this.getChildren();
    }
    public abstract  BaseAction buildMenuItemAction();
}
