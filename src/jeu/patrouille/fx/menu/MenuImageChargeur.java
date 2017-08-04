/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import javafx.scene.image.Image;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class MenuImageChargeur {
    
    public static int MARCHE_IMG=0,OP_FIRE_IMG=1,RUN_IMG=2,FEU_IMG=3;
    Image[] menuImages;
    static MenuImageChargeur ss;
     
    
    MenuImageChargeur() {
        menuImages=new Image[19];
        menuImages[BaseAction.MARCHE]=new Image("walkButton.png");
        menuImages[BaseAction.OP_FEU]=new Image("opFireButton.png");
        menuImages[BaseAction.COURS]=new Image("runButton.png");
        menuImages[BaseAction.FEU]=new Image("fireButton.png");
        menuImages[BaseAction.NO_ACTION]=new Image("arrowPng.png");
        
    }
    
    
    
    public  Image getImage(int type){

        return  menuImages[type];
    }
    public static MenuImageChargeur geInstance(){
        if(ss==null) ss=new MenuImageChargeur();
        return ss;
        }
    }



    
    
    
    

