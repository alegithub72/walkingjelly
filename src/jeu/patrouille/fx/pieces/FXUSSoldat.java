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
import jeu.patrouille.coeur.actions.BaseAction;
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
public class FXUSSoldat extends Sprite implements Runnable{

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
        defaultFrame();

        
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
        defaultFrame();

        
    }    

    public void setS(Soldat s) {
        this.s = s;
    }
    
    public void setDeafultFrme(int n){
    this.defaultFrame=n;
    }
    public void buildSprite(){
        if(!this.getChildren().contains(blessureImg))this.getChildren().add(blessureImg);
        if(!this.getChildren().contains(flagImg))this.getChildren().add(flagImg);
        if(!this.getChildren().contains(classmentImg))this.getChildren().add(classmentImg);
        flagImg.relocate(this.getX(),this.getY());
        classmentImg.relocate(this.getX(),this.getY());
        classmentImg.setTranslateX(FXCarte.TILE_SIZE-20);
        classmentImg.setTranslateY(  0);
        blessureImg.relocate(this.getX(),this.getY());
        blessureImg.setTranslateX(0);
        blessureImg.setTranslateY(FXCarte.TILE_SIZE-10);
        if(getChildren().contains(selectionneImg)) {
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
   
    if(!getChildren().contains(selectionneImg)) {
        getChildren().add(selectionneImg);
        selectionneImg.relocate(this.getX(), this.getY());
        selectionneImg.toBack();
    }else {
    selectionneImg.setVisible(true);
    }
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
    
    Point2D getSceneCoord(int i,int j){
        double x0=((j-fxcarte.getPosJ())*FXCarte.TILE_SIZE)+(FXCarte.TILE_SIZE/2);
        double y0=((i-fxcarte.getPosI())*FXCarte.TILE_SIZE)+(FXCarte.TILE_SIZE/2); 
        Point2D p=new Point2D(x0, y0);
        return p;
    }
    
    public  void playMove(MarcheAction  act){
    
        Path p=new Path();
        this.deselectioneFXSoldat();
        Point2D p1=getSceneCoord(act.getI0(), act.getJ0());
        MoveTo mTo=new MoveTo(p1.getX(),p1.getY() );
        Point2D p2=getSceneCoord(act.getI1(), act.getJ1());
        LineTo l=new LineTo(p2.getX(),p2.getY());
        p.getElements().add(mTo);
        p.getElements().add(l);
        Point2D derivedCoordp0=getSceneCoord(act.getDerivedAction().getI0(), act.getDerivedAction().getJ0());
        Point2D derivedCoordp1=getSceneCoord(act.getDerivedAction().getI1(), act.getDerivedAction().getJ1());
        
        //double angle=Piece.getDirection(derivedCoordp0.getX(),derivedCoordp0.getY(),
         //       derivedCoordp1.getX(),derivedCoordp1.getY());
        //this.
        double angle=Piece.getDirection(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        setSoldatOrintetion(angle  );
                
        System.out.println("rotate "+angle);


        PathTransition  path=new PathTransition();
        path.setDuration(Duration.millis(1000));
        path.setNode(this);
        path.setPath(p);
        
        path.setOrientation(PathTransition.OrientationType.NONE);
        path.setCycleCount(1);
        path.setAutoReverse(false);
        createMove();


        path.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              frameAnimTimer[0].stop();
              //path.stop();
              
            }
        });
     
       
         path.play();
         ptList[0]=path;
        // Thread t=new Thread(this);
        // t.start();
        run();
        
    }
    private void getAngle(Point2D p0,Point2D p2){

    }
    public void setSoldatOrintetion(double angle){
      imgView.setRotate(orientation  );
      orientation=angle;
      imgView.setRotate(angle);
    }
    
    @Override
    public void run() {
    frameAnimTimer[0].start();
     ptList[0].play();
    }
    
    private void createMove(){
        frameAnimTimer[0]=new FrameAnimationTimer(1, 4, this, 0, true, 200, null);
        
    }
    
    
    
}
