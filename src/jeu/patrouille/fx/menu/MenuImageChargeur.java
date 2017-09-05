/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.scene.image.Image;
import jeu.patrouille.coeur.actions.enums.ActionType;

/**
 *
 * @author applealex
 */
public class MenuImageChargeur {
    

           ;
    Image[] menuImages;
    static MenuImageChargeur ss;
    Image[] menuImageSubType;
    
    MenuImageChargeur() {
        menuImages=new Image[ActionType.values().length];
        menuImageSubType=new Image[4];
        menuImages[ActionType.MARCHE.ordinal()]=new Image("walkButton.png");
        menuImages[ActionType.OCCASION_DE_FEU.ordinal()]=new Image("opFireButton.png");
        menuImages[ActionType.COURS.ordinal()]=new Image("runButton.png");
        menuImages[ActionType.FEU.ordinal()]=new Image("fireButton.png");
        menuImages[ActionType.PA_ACTION.ordinal()]=new Image("arrowPng.png");
        menuImages[ActionType.BANDAGE.ordinal()]=new Image("bandageButton2.png");
        menuImages[ActionType.FEU_VISER.ordinal()]=new Image("aimedShot.png");
        menuImages[ActionType.ARME_RECHARGE.ordinal()]=new Image("loadMagazineButton.png");
        menuImageSubType[0]=new Image("BurstButton.png");
        menuImageSubType[1]=new Image("FullAutomaticButton.png");
        menuImageSubType[2]=new Image("aimedShotBURST.png");
        menuImageSubType[3]=new Image("aimedShotFullAuto.png");
    }
    
    
    private Image getSubTypeImage(int n){
        return menuImageSubType[n];
    }
    private  Image getImage(ActionType type){
        
        return  menuImages[type.ordinal()];
    }
    public static Image getImageMenu(ActionType type){
        return getInstance().getImage(type);
    }
    public static Image getImageMenuSubType(int n){
        return getInstance().getSubTypeImage(n);
    }
    
    private static  MenuImageChargeur getInstance(){
        if(ss==null) ss=new MenuImageChargeur();
        return ss;
        }
    }



    
    
    
    

