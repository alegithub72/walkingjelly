/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.util;

import javafx.scene.ImageCursor;
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
     arrowCRRight=new ImageCursor(new Image("cursorScroll.png"));
     arrowCRLeft=new ImageCursor(new Image("cursorScrollLeft.png"));
     arrowCRUp=new ImageCursor(new Image("cursorScrollUP.png"));
     arrowCRDown=new ImageCursor(new Image("cursorScrollDOWN.png"));           
         
    }
     
    ImageCursor arrowCRRight;
    ImageCursor arrowCRLeft;
    ImageCursor arrowCRUp;
    ImageCursor arrowCRDown;  
   
   static ImageChargeur singleton;
     public static ImageChargeur getInstance(){
    if(singleton==null) singleton=new ImageChargeur();
    return singleton;
    }
    public Image getImage(int n){
    return array[n];
    }

    public ImageCursor getArrowCRRight() {
        return arrowCRRight;
    }

    public ImageCursor getArrowCRLeft() {
        return arrowCRLeft;
    }

    public ImageCursor getArrowCRUp() {
        return arrowCRUp;
    }

    public ImageCursor getArrowCRDown() {
        return arrowCRDown;
    }
    
     
}
