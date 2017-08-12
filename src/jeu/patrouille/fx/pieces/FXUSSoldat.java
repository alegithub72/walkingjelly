/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.animation.FrameAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXUSSoldat extends Sprite {

    Soldat s;
    int pos;
    ImageView blessureImg;
    ImageView flagImg;
    ImageView classmentImg;
    int defaultFrame;
    ImageView selectionneImg;
    FXCarte fxcarte;
    double orientation;

    public FXUSSoldat(Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,"frameFigurineUS.png",null);
        this.fxcarte=fxcarte;
        this.s=s;   
        this.pos=pos;
        blessureImg=new ImageView("wound.png");
        flagImg=new ImageView("americanFlag.png");
        if(s.getClassement()==Soldat.CLASS_SGT)  classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Soldat.CLASS_SOLDAT)classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
       defaultFrame=0;
       orientation=0;


        
    }

   @Override
   public void setY(double y) {
        
        super.setY(y); 

    }

    @Override
    public void setX(double x) {
        super.setX(x); 
     
    }
    
    public FXUSSoldat(String f,Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,f,null);
        this.fxcarte=fxcarte;
        this.s=s;   
        this.pos=pos;
        blessureImg=new ImageView("wound.png");
        flagImg=new ImageView("americanFlag.png");
        if(s.getClassement()==Soldat.CLASS_SGT)  classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Soldat.CLASS_SOLDAT)classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
        defaultFrame=0;
    
   

        
    }    

    public void setS(Soldat s) {
        this.s = s;
    }
    
    public void setDeafultFrme(int n){
    this.defaultFrame=n;
    }
    public void buildFXUSSoldat(double x,double y){
        //this.relocate(x, y);
        this.setX(x);
        this.setY(y); 
        if(!sprites.getChildren().contains(imgView))
            sprites.getChildren().add(imgView);
        if(!sprites.getChildren().contains(blessureImg))
            sprites.getChildren().add(blessureImg);
        if(!sprites.getChildren().contains(flagImg))
            sprites.getChildren().add(flagImg);
        if(!sprites.getChildren().contains(classmentImg))
            sprites.getChildren().add(classmentImg);    

        defaultFrame();
        //this.relocate(x, y);
       
        resetGroupSprite();
        
    
    }
    private void resetGroupSprite(){
        
        
        flagImg.relocate(this.getX(),this.getY());
        classmentImg.relocate(this.getX(),this.getY());
        classmentImg.setTranslateX(FXCarte.TILE_SIZE-20);
        classmentImg.setTranslateY(  0);
        blessureImg.relocate(this.getX(),this.getY());
        blessureImg.setTranslateX(0);
        blessureImg.setTranslateY(FXCarte.TILE_SIZE-10);
        if(sprites.getChildren().contains(selectionneImg)) {
        selectionneImg.relocate(getX(), getY());
        }
        
    }

    public Soldat getSoldat(){
    return s;
    }
    
    public void defaultFrame(){
        setFrame(defaultFrame);
    }
    public void selectioneFXSoldat(){
   
    if(!sprites.getChildren().contains(selectionneImg)) {
        sprites.getChildren().add(selectionneImg);
        selectionneImg.relocate(this.getX(), this.getY());
        selectionneImg.toBack();
    }else {
    selectionneImg.setVisible(true);
    }
    //setFrame(3);
    }
    public void deselectioneFXSoldat(){
    
    if(sprites.getChildren().contains(selectionneImg)) 
        selectionneImg.setVisible(false);

    //setFrame(3);
    }

    @Override
    public String toString() {
        return "s=" + s.toStringSimple() ;
    }
    

    
   public  void playMarche(MarcheAction  act){
        
        System.out.println("------------- FXSOLDAT CREATE-ANIM ---------------->"+act+"-------->"+act.getProtagoniste().toStringSimple()+"<---------");
        if(fxcarte.estFXSoldatView(act.getI1(), act.getJ1())){
        //System.out.println("soldato anim:"+act.getProtagoniste());
        Path p=new Path();
        this.deselectioneFXSoldat();
        Point2D p1=fxcarte.getSceneCoord(act.getI0(), act.getJ0());
        double x0=p1.getX()
                +(FXCarte.TILE_SIZE/2)
                ;
        double y0=p1.getY()
                +(FXCarte.TILE_SIZE/2)
                ;
        MoveTo mTo=new MoveTo(x0,y0);
        
        Point2D p2=fxcarte.getSceneCoord(act.getI1(), act.getJ1());
        double x1=p2.getX()
                +(FXCarte.TILE_SIZE/2)
                ;
        double y1=p2.getY()
                +(FXCarte.TILE_SIZE/2)
                ;
        
        LineTo l=new LineTo(x1,y1);
               
              p.getElements().addAll(mTo,l);

 
        //p.getElements().add(l);
//        Point2D derivedCoordp0=getSceneCoord(act.getDerivedAction().getI0(), act.getDerivedAction().getJ0());
//        Point2D derivedCoordp1=getSceneCoord(act.getDerivedAction().getI1(), act.getDerivedAction().getJ1());
        
        //double angle=Piece.getDirection(derivedCoordp0.getX(),derivedCoordp0.getY(),
         //       derivedCoordp1.getX(),derivedCoordp1.getY());
        //this.
        double angle=Piece.getDirection(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        setFXSoldatOrientation(angle);
                
        System.out.println("rotate "+angle);


        PathTransition  path=new PathTransition();
        path.setDuration(Duration.millis(1000));
        path.setPath(p);
        path.setNode(this);

        path.setRate(0.5);
        path.setOrientation(PathTransition.OrientationType.NONE);
        path.setCycleCount(1);
        path.setAutoReverse(false);
        createMove();
        //sprites.getChildren().add(p);
        ptList[0]=path;
        frameAnimTimer[0].start();
        ptList[0].play();     
        
        path.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              

              System.out.println("***************STATUS "+ptList[0].getStatus());
              frameAnimTimer[0].stop();
              fxcarte.setAnimOn(false);
              path.stop();
              
              //fxcarte.refreshCarte();
              //fxcarte.resetAllUSPositionFXSoldatView();
              //ptList[0].stop();
             // try{
              //fxcarte.getMj().getThreadTurn().notify();
              //}catch(java.lang.IllegalMonitorStateException e){
              // e.printStackTrace();
             // }
             System.out.println("FXSOLDAT ANIM----->STOP");
//              }
             event.consume();
            }
        });

        System.out.println("FXSOLDAT ANIM------> PLAY");

        }else{
            this.setVisible(false);
        }
System.out.println("------------- FXSOLDAT CREATE-ANIM ---------FINE------->--------><---------");
    }
        
    
    private void getAngle(Point2D p0,Point2D p2){

    }
    public void setFXSoldatOrientation(double angle){
      imgView.setRotate(-orientation  );
        updateSodlatOrientation(angle);
      orientation=angle;
      imgView.setRotate(angle);
    }
    
    void updateSodlatOrientation(double angle){
        if(angle>=-30 && angle<=30 ) 
            this.s.setFace(Piece.Direction.N);
        else if(angle>30 && angle<60 )s.setFace(Piece.Direction.NW);        
        else if(angle>=60 && angle<=120)s.setFace(Piece.Direction.W);
        else if(angle>120 && angle<150) s.setFace(Piece.Direction.SW);        
        else if((angle>=150 && 
                angle <180 )|| (angle<=-150 && angle>=-180))  
            s.setFace(Piece.Direction.S);
        else if(angle<-120 && angle>-150) s.setFace(Piece.Direction.SW);
        else if(angle<=-60 && angle>=-120) s.setFace(Piece.Direction.W);
        else if(angle<-30 && angle >-60) s.setFace(Piece.Direction.NE);
        System.out.println("------>"+angle+"-----orientation updataed--->"+s.getFace());
        }
    
    private void createMove(){
        frameAnimTimer[0]=new FrameAnimationTimer(1, 4, this, 0, true, 200, FrameAnimationTimer.MARCHE);
        
    }
    

}