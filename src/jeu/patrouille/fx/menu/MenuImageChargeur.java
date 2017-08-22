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
    
    public static int MARCHE_IMG=0,OP_FIRE_IMG=1,RUN_IMG=2,FEU_IMG=3;
           ;
    Image[] menuImages;
    static MenuImageChargeur ss;
     
    
    MenuImageChargeur() {
        menuImages=new Image[ActionType.values().length];
        menuImages[ActionType.MARCHE.ordinal()]=new Image("walkButton.png");
        menuImages[ActionType.OCCASION_DE_FEU.ordinal()]=new Image("opFireButton.png");
        menuImages[ActionType.COURS.ordinal()]=new Image("runButton.png");
        menuImages[ActionType.FEU.ordinal()]=new Image("fireButton.png");
        menuImages[ActionType.PA_ACTION.ordinal()]=new Image("arrowPng.png");
        
    }
    
    
    
    public  Image getImage(ActionType type){

        return  menuImages[type.ordinal()];
    }
    public static MenuImageChargeur geInstance(){
        if(ss==null) ss=new MenuImageChargeur();
        return ss;
        }
    }



    
    
    
    

