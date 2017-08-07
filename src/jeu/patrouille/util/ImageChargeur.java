/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.util;

import javafx.scene.image.Image;

/**
 *
 * @author appleale
 */
public class ImageChargeur {
    public static int CURSOR_HOST_RANGE=0,CURSOR_US_RANGE=1,CURSOR_FORBIDDEN=2;
    Image[] array;
     ImageChargeur() {
         array=new Image[5];
         array[0]=new Image("rangeArrowHost.png");
         array[1]=new Image("rangeArrow2.png");
         array[2]=new Image("forbiddenCursor.png");
         
    }
   static ImageChargeur singleton;
     public static ImageChargeur getInstance(){
    if(singleton==null) singleton=new ImageChargeur();
    return singleton;
    }
    public Image getImage(int n){
    return array[n];
    }
     
}
