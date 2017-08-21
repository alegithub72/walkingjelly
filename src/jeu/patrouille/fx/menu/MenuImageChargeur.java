/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.scene.image.Image;
import jeu.patrouille.coeur.actions.enums.OrdreAction;

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
        menuImages=new Image[OrdreAction.values().length];
        menuImages[OrdreAction.MARCHE.ordinal()]=new Image("walkButton.png");
        menuImages[OrdreAction.OCCASION_DE_FEU.ordinal()]=new Image("opFireButton.png");
        menuImages[OrdreAction.COURS.ordinal()]=new Image("runButton.png");
        menuImages[OrdreAction.FEU.ordinal()]=new Image("fireButton.png");
        menuImages[OrdreAction.PA_ACTION.ordinal()]=new Image("arrowPng.png");
        
    }
    
    
    
    public  Image getImage(OrdreAction type){

        return  menuImages[type.ordinal()];
    }
    public static MenuImageChargeur geInstance(){
        if(ss==null) ss=new MenuImageChargeur();
        return ss;
        }
    }



    
    
    
    

