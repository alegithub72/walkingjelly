/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.sprite;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import jeu.patrouille.fx.animation.FrameAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;



/**
 *
 * @author appleale
 */
public class Sprite extends Parent
{
  
    

    protected ImageView imgView;


    public static int MOVE_TRANSITION = 0;
    Image frameImages;
    Sprite[] extraSprite=new Sprite[2];
    Rectangle2D[] frames;
    int w;
    int h;
    int wSquare;
    int hSquare;
    protected int defaultFrame;
    protected FrameAnimationTimer frameAnimTimer[];
    int k;
    protected Animation[] ptList;
    double x,y;

    
    public Sprite(int w, int h,int wboardBox,int hBoardBox,String img){
        super();
        this.w = w;
        this.h = h;
        this.wSquare=wboardBox;
        this.hSquare=hBoardBox;

        
        if(img!=null) this.frameImages=new Image(img);

        ptList=new Animation[5];
        
        frameAnimTimer=new FrameAnimationTimer[2];
        
    }

    public void buildFrameImages(Image frameImages) {
        if(imgView!=null) getChildren().remove(imgView);
        this.frameImages = frameImages;
        createShapeFrame();        
        imgView = new ImageView(frameImages);
        imgView.setViewport(frames[0]);          
        if(!getChildren().contains(imgView))
            getChildren().add(imgView);
        
    }
      protected void buildFrameImages() {


        createShapeFrame();
        imgView = new ImageView(frameImages);
        imgView.setViewport(frames[0]);  
        getChildren().add(imgView);   


       
        
    }

    
    
     void createShapeFrame(){
        int n = (frameImages.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }    
    public Node getImgView(){
        return imgView;
    }
    public Image getImg(){
        return frameImages;
    }
    protected Sprite() {
                super();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setFrame(int i) {
        if(i>=frames.length)  i=0;
        this.defaultFrame = i;
        imgView.setViewport(frames[i]);
    }

    





    
    
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isMoveFini() {
        if(ptList==null || ptList[0]==null) return true;
        else{
           // System.out.println("STATUS"+ptList[0].getStatus()+" time");
            return ptList[0].getStatus() == Animation.Status.STOPPED;
        }
    }
    
    public boolean isAnimEnd(){
        return frameAnimTimer[0].isStopped();
    }
    public boolean isAnimSetted(){
        return frameAnimTimer[0]!=null;
    }
    public void removeAnimationSetting() {
        for (int i = 0; i < ptList.length; i++) {
            ptList[i] = null;
        }
        for (int i = 0; i < frameAnimTimer.length; i++) {
             if(frameAnimTimer[i]!=null) frameAnimTimer[i].stop();
            frameAnimTimer[i] = null;
        }

    }
    
    public void stopAnimation(int n){
       if(frameAnimTimer[n]!=null) frameAnimTimer[n].stop();
    }
    public void removeExtraSprite(int n){
        //if(extraSprite[n]!=null) fbx.remove(extraSprite[n]);
    }
    public static double convertBoardIposition(int i,int w){
    
                double x = ((i * w) + (w / 2)) 
                    - (FXCarte.TILE_SIZE/2);
                return x;
    }
     public static double convertBoardJposition(int j,int h){
        double y = ((j * h) + (h / 2)) 
                    - (FXCarte.TILE_SIZE/2)
                    ;
        return y;
    }   
    public static double convertBoardIpositionCenter(int i,int w){
    
                double x = ((i * w) 
                        + (w / 2)
                        );
                return x;
    }
    public static double convertBoardJpositionCenter(int j,int h){
    
                double y = ((j * h)
                        + (h / 2)
                       )  ;
                        
                return y;
    }    
    public void  removeThis(){
        //fbx.remove(this);
    }
    
//TODO permette la costruzioni di varie immagini....    
public Image composedImage(String f) {
    ImageView test = new ImageView(new Image("menuItem.png"));
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);
    Image rotatedImage = test.snapshot(params, null);
    return rotatedImage;

}    
public void defaultFrame(){
        setFrame(defaultFrame);
    }

public void setDeafultFrame(int n){
    this.defaultFrame=n;
    }
    

}
