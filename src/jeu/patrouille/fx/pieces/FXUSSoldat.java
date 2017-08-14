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
import static jeu.patrouille.fx.board.FXCarte.PIXEL_SCROLL_AREA_W;
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
                FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,"frameSoldierUS2.png",null);
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
    public void buildFXUSSoldat(){
        buildFrameImages();
        if(!getChildren().contains(blessureImg))
            getChildren().add(blessureImg);
        if(!getChildren().contains(flagImg))
            getChildren().add(flagImg);
        if(!getChildren().contains(classmentImg))
            getChildren().add(classmentImg);    

        defaultFrame();
        //this.relocate(x, y);
        buildGroupSigns();
 
        
    
    }
    private void buildGroupSigns(){
        
        classmentImg.setTranslateX(FXCarte.TILE_SIZE-20);
        classmentImg.setTranslateY(  0);
        blessureImg.setTranslateX(0);
        blessureImg.setTranslateY(FXCarte.TILE_SIZE-10);

        
    }

    public Soldat getSoldat(){
    return s;
    }
    
    public void defaultFrame(){
        setFrame(defaultFrame);
    }
    public void selectioneFXSoldat(){
   
    if(!getChildren().contains(selectionneImg)) {
        getChildren().add(selectionneImg);
        //selectionneImg.setLayoutX(this.getLayoutX());
        //selectionneImg.setLayoutY(this.getLayoutY());
        selectionneImg.toBack();
    }
    selectionneImg.setVisible(true);
    
    //setFrame(3);
    }
    public void deselectioneFXSoldat(){
    
    if(getChildren().contains(selectionneImg)) 
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
        Point2D p1=getSceneCoordMove(act.getProtagoniste(),act.getI0(), act.getJ0());
        float x0=((float)(p1.getX()
                //-this.getLayoutX()
                )
                +(FXCarte.TILE_SIZE/2)
                )
                ;
        float y0=((float)(p1.getY()
               // -this.getLayoutY()
                )
                +(FXCarte.TILE_SIZE/2)
                )
                ;

        MoveTo mTo=new MoveTo(x0,y0);
 
        Point2D p2=getSceneCoordMove(act.getProtagoniste(),act.getI1(), act.getJ1());
        float x1=((float)(p2.getX()
               // -this.getLayoutX()
                )
                +(FXCarte.TILE_SIZE/2)
                )
                ;
        float y1=((float)(p2.getY()
                //-this.getLayoutY()
                )
                +(FXCarte.TILE_SIZE/2)
                )
                ;
        //TODO esteticamente modificare i movimenti agli estremi dello schermo di scrolll
//        if(x1<=FXCarte.TILE_SIZE)x1=30;
//        if(y1<=FXCarte.TILE_SIZE)y1=30;
//        if(x1>=(FXCarte.PIXEL_SCROLL_AREA_W-FXCarte.TILE_SIZE))
//            x1=(float)FXCarte.PIXEL_SCROLL_AREA_W-FXCarte.TILE_SIZE-20;
//        if(y1>=(FXCarte.PIXEL_SCROLL_AREA_H-FXCarte.TILE_SIZE))
//            y1=(float)FXCarte.PIXEL_SCROLL_AREA_H-FXCarte.TILE_SIZE-20;
//        
//        if(x0<=FXCarte.TILE_SIZE)x0=30;
//        if(y0<=FXCarte.TILE_SIZE)y0=30;
//        if(x0>=(FXCarte.PIXEL_SCROLL_AREA_W-FXCarte.TILE_SIZE))
//            x0=(float)FXCarte.PIXEL_SCROLL_AREA_W-FXCarte.TILE_SIZE-20;
//        if(y0>=(FXCarte.PIXEL_SCROLL_AREA_H-FXCarte.TILE_SIZE))
//            y0=(float)FXCarte.PIXEL_SCROLL_AREA_H-FXCarte.TILE_SIZE-20;        
        
        LineTo l=new LineTo(x1,y1);
        
        p.getElements().addAll(mTo,l);

        System.out.println("2D coord p1="+p1+" p2="+p2);
        //System.out.println(this.getBoundsInLocal()+","+this.sett);
        //p.getElements().add(l);
//        Point2D derivedCoordp0=getSceneCoord(act.getDerivedAction().getI0(), act.getDerivedAction().getJ0());
//        Point2D derivedCoordp1=getSceneCoord(act.getDerivedAction().getI1(), act.getDerivedAction().getJ1());
        
        //double angle=Piece.getDirection(derivedCoordp0.getX(),derivedCoordp0.getY(),
         //       derivedCoordp1.getX(),derivedCoordp1.getY());
        //this.
        double angle=Piece.getDirection(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        setFXSoldatOrientation(angle);
                
        System.out.println("rotate "+angle);


        //this.setLayoutX(0);this.setLayoutY(0);
        PathTransition  path=new PathTransition();
        path.setDuration(Duration.millis(500));
        path.setPath(p);
        path.setNode(this);

        path.setRate(0.5);
        path.setOrientation(PathTransition.OrientationType.NONE);
        path.setCycleCount(1);
        path.setAutoReverse(false);
        createMove();
        
          
        //fxcarte.getRootGroup().getChildren().add(p);

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
            fxcarte.setAnimOn(false);
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
        frameAnimTimer[0]=new FrameAnimationTimer(1, 3, this, 0, true, 200, FrameAnimationTimer.MARCHE);
        
    }
    public Point2D getSceneCoordMove(Piece s,int  i,int j){
        int posj=fxcarte.getPosJ(),posi=fxcarte.getPosI();
        System.out.println(" get coord "+i+","+j+" posI,posj="+posi+","+posj);
        int scrollI=i-posi,scrollJ=j-posj;
     
        double x0=(scrollJ*FXCarte.TILE_SIZE);
        double y0=(scrollI*FXCarte.TILE_SIZE); 
         y0=esteticCorrectionY0(scrollI,y0);
         x0=esteticCorrectionX0(scrollJ,x0);
        Point2D p=new Point2D(x0, y0);
        return p;
    }         
    
    private double esteticCorrectionY0(int scrollI,double y){
        double y0=y;
        if(scrollI==0) y0=10;
        else if(scrollI==(FXCarte.AREA_SCROLL_I_H-1)) y0=FXCarte.PIXEL_SCROLL_AREA_H-FXCarte.TILE_SIZE-10;
        return y0;
    }
    private double esteticCorrectionX0(int scrollJ,double x){
        double x0=x;
        if(scrollJ==0) x0=10;
        else if(scrollJ==(FXCarte.AREA_SCROLL_J_W-1)) x0=PIXEL_SCROLL_AREA_W-FXCarte.TILE_SIZE-10;
        return x0;
    }    

}