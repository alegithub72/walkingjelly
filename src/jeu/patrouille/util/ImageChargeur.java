/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import jeu.patrouille.fx.sprite.CursorHelper;

/**
 *
 * @author appleale
 */
public class ImageChargeur {
    public static int CURSOR_HOST_RANGE=0,CURSOR_US_RANGE=1,
            CURSOR_FORBIDDEN=2,CURSOR_CROSSHAIR=3,CURSO_HELPER_BANDGAE=4;
    CursorHelper cursoHostRange = new CursorHelper(ImageChargeur.CURSOR_HOST_RANGE);
    CursorHelper cursorUSRange = new CursorHelper(ImageChargeur.CURSOR_US_RANGE);
    CursorHelper cursorForbidenRange=new CursorHelper(ImageChargeur.CURSOR_FORBIDDEN);  
    CursorHelper cursorCrooshair=new CursorHelper(ImageChargeur.CURSOR_CROSSHAIR);
    CursorHelper cursorBandage=new CursorHelper(ImageChargeur.CURSO_HELPER_BANDGAE);
    Image[] array;
     ImageChargeur() {
        array=new Image[6];
        array[0]=new Image("rangeArrowHost.png");
        array[1]=new Image("rangeArrow2.png");
        array[2]=new Image("forbiddenCursor.png");
        array[3]=new Image("crosshair.png");
        array[4]=new Image("cursorHelperBandage.png");
        arrowCRRight=new ImageCursor(new Image("cursorScroll.png"));
        arrowCRLeft=new ImageCursor(new Image("cursorScrollLeft.png"));
        arrowCRUp=new ImageCursor(new Image("cursorScrollUP.png"));
        arrowCRDown=new ImageCursor(new Image("cursorScrollDOWN.png")); 
        forbidden=new ImageCursor(new Image("cursorForbidden.png"));
        cursoHostRange.buildFrameImages(array[CURSOR_HOST_RANGE]);
        cursorForbidenRange.buildFrameImages(array[CURSOR_FORBIDDEN]);
        cursorUSRange.buildFrameImages(array[CURSOR_US_RANGE]);
        cursorBandage.buildFrameImages(array[CURSO_HELPER_BANDGAE]);
        cursorCrooshair.buildFrameImages(array[CURSOR_CROSSHAIR]);
    }
     
    ImageCursor arrowCRRight;
    ImageCursor arrowCRLeft;
    ImageCursor arrowCRUp;
    ImageCursor arrowCRDown;  
    ImageCursor forbidden;
    
   static ImageChargeur singleton;
     public static ImageChargeur getInstance(){
    if(singleton==null) singleton=new ImageChargeur();
    return singleton;
    }
    public Image getImage(int n){
    return array[n];
    }
    public CursorHelper getDisplayRange(int type){
        if(type==ImageChargeur.CURSOR_US_RANGE)
            return this.cursorUSRange;
        else if(type==ImageChargeur.CURSOR_HOST_RANGE)
            return this.cursoHostRange;
        else if(type==ImageChargeur.CURSOR_FORBIDDEN)
            return this.cursorForbidenRange;
        else if(type==CURSOR_CROSSHAIR) return this.cursorCrooshair;
        else if(type==CURSO_HELPER_BANDGAE) return this.cursorBandage;
        return null;
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
    
    public ImageCursor getForbidden() {
        return forbidden;
    }
    public static void decodeImage()
            throws FileNotFoundException,IOException{
        InputStream in=new FileInputStream("/Users/appleale/rangeArrow.png");
        //byte[] buf=new byte[1];
        ByteArrayOutputStream outByte=new ByteArrayOutputStream(1);
        while(in.available()>0){
            byte b=(byte)in.read();
            outByte.write(~b);
        }
        in.close();
        outByte.close();
        FileOutputStream outFile=new FileOutputStream("/Users/appleale/NOtrangeArrowHost.png");
        outFile.write(outByte.toByteArray());
        outFile.close();
        
    }     
   public static void loadDecodeImage() throws IOException{
       
       
   FileInputStream inFile=new FileInputStream("/Users/appleale/NOtrangeArrowHost.png");
   ByteArrayOutputStream outByte=new ByteArrayOutputStream(1);
   while(inFile.available()>0){
    byte b=(byte)inFile.read();
    outByte.write(~b);
   }
   outByte.close();
   inFile.close();
   FileOutputStream outFile=new FileOutputStream("/Users/appleale/backrangearrow.png");
   
   outFile.write(outByte.toByteArray());
   outFile.close();
   
   }
    public static  void main(String ars[])throws IOException{
        decodeImage();
        loadDecodeImage();
    }
    
}
