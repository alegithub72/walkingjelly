/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.sprite;

import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public  class FXPatrouilleSprite extends Sprite {
    
    protected FXCarte fxcarte;
    protected int offsx, offsy;
    public FXPatrouilleSprite(int w,int h,String img,FXCarte fxcarte) {
        super(w, h,img);
        this.fxcarte=fxcarte;
        offsx = w/2;
        offsy = h/2;
    }
    public  void create(){
            buildFrameImages();
    }

    public int getOffsx() {
        return offsx;
    }

    public int getOffsy() {
        return offsy;
    }
    
    
    
//    protected double esteticCorrectionY0(int scrollI, double y) {
//        double y0 = y;
//        if (scrollI == 0) {
//            y0 = 30;
//        } else if (scrollI == (FXCarte.AREA_SCROLL_I_H - 1)) {
//            y0 = FXCarte.PIXEL_SCROLL_AREA_H - FXCarte.TILE_SIZE - 30;
//        }
//        return y0;
//    }
//
//    protected double esteticCorrectionX0(int scrollJ, double x) {
//        double x0 = x;
//        if (scrollJ == 0) {
//            x0 = 30;
//        } else if (scrollJ == (FXCarte.AREA_SCROLL_J_W - 1)) {
//            x0 = FXCarte.PIXEL_SCROLL_AREA_W - FXCarte.TILE_SIZE - 30;
//        }
//        return x0;
//    }
    
    public  void setX(int scrollJ,double x){
        //x=esteticCorrectionX0(scrollJ, x);
        setTranslateX(x);
    }
    public void setY(int scrollI,double y){
    //y=esteticCorrectionY0(scrollI, y);
        setTranslateY(y);
    
    }    
    
}
