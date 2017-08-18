/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public abstract class FXSoldat extends FXPatrouilleSprite {
    
    Soldat s;
    ImageView blessureImg;
    ImageView flagImg;
    ImageView classmentImg;
    ImageView selectionneImg;
    double orientation;
    int pos;
    public FXSoldat(int w, int h, int pos,Soldat s, String img,FXCarte fxcarte) {
        super(w, h, img, fxcarte);
        this.s=s;
        blessureImg=new ImageView("wound.png");
        flagImg=new ImageView("americanFlag.png");
        if(s.getClassement()==Soldat.CLASS_SGT)  classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Soldat.CLASS_SOLDAT)classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
        orientation=0;
        this.pos=pos;
    }

    protected void buildGroupSigns() {
        classmentImg.setTranslateX(FXCarte.TILE_SIZE - 20);
        classmentImg.setTranslateY(0);
        blessureImg.setTranslateX(0);
        blessureImg.setTranslateY(FXCarte.TILE_SIZE - 10);
    }
    protected void buildShadow(){
    
        DropShadow ds=new DropShadow(5, Color.BLACK);
        ds.setOffsetX(5);
        ds.setOffsetY(5);        
        imgView.setEffect(ds);    
    
    }
    public Soldat getSoldat() {
        return s;
    }

    public void selectioneFXSoldat() {
        if (!getChildren().contains(selectionneImg)) {
            getChildren().add(selectionneImg);
            selectionneImg.toBack();
        }
        selectionneImg.setVisible(true);
        //setFrame(3);
    }

    public void deselectioneFXSoldat() {
        if (getChildren().contains(selectionneImg)) {
            selectionneImg.setVisible(false);
        }
    }



    protected void getAngle(Point2D p0, Point2D p2) {
    }

    public void setFXSoldatOrientation(double angle) {
        imgView.setRotate(-orientation);
        updateSodlatOrientation(angle);
        orientation = angle;
        imgView.setRotate(angle);
    }



    Point2D getSceneCoordMove(int i, int j) {
        int posj = fxcarte.getPosJ();
        int posi = fxcarte.getPosI();
        //System.out.println(" get coord "+i+","+j+" posI,posj="+posi+","+posj);
        int scrollI = i - posi;
        int scrollJ = j - posj;
        double x0 = scrollJ * FXCarte.TILE_SIZE;
        double y0 = scrollI * FXCarte.TILE_SIZE;
        y0 = esteticCorrectionY0(scrollI, y0);
        x0 = esteticCorrectionX0(scrollJ, x0);
        Point2D p = new Point2D(x0, y0);
        return p;
    }

    protected boolean estFXSoldatView(int i1, int j1) {
        boolean b = true;
        int posJ = fxcarte.getPosJ();
        int posI = fxcarte.getPosI();
        int H = (FXCarte.AREA_SCROLL_I_H) - 1;
        int W = (FXCarte.AREA_SCROLL_J_W) - 1;
        if (j1 > (posJ + W)) {
            b = false;
        }
        if (j1 < posJ) {
            b = false;
        }
        if (i1 > (posI + H)) {
            b = false;
        }
        if (i1 < posI) {
            b = false;
        }
        return b;
    }

    public boolean estFXSoldatView() {
        return estFXSoldatView(s.getI(), s.getJ());
    }

    public void enableSoldatoInView(int k) {
        System.out.println("reset position " + s.toStringSimple());
        int i0 = s.getI();
        int j0 = s.getJ();
        Point2D p = fxcarte.getSceneCoordForRefreshCarte(i0, j0);
        double x0 = p.getX();
        double y0 = p.getY();
        x0 = x0 + (20 * k);
        y0 = y0 + (20 * k);
        int scrollJ = j0 - fxcarte.getPosJ();
        int scrollI = i0 - fxcarte.getPosI();
        System.out.println(" enable node--->" + s + "x0,y0=" + x0 + "," + y0);
        x0 = esteticCorrectionX0(scrollJ, x0);
        y0 = esteticCorrectionY0(scrollI, y0);
        setTranslateX(x0);
        setTranslateY(y0);
        toFront();
        setVisible(true);
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
    
    public void createFXSoldat(){
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
        buildShadow();
 
        
    
    }    
   public  void playMarche(MarcheAction  act){
        
        System.out.println("------------- FXSOLDAT CREATE-ANIM ---------------->"+act+"-------->"+act.getProtagoniste().toStringSimple()+"<---------");
        if(estFXSoldatView(act.getI1(), act.getJ1())){
        //System.out.println("soldato anim:"+act.getProtagoniste());
        Path p=new Path();
        this.deselectioneFXSoldat();
        Point2D p1=getSceneCoordMove(act.getI0(), act.getJ0());
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
 
        Point2D p2=getSceneCoordMove(act.getI1(), act.getJ1());
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
        
        LineTo l=new LineTo(x1,y1);
        
        p.getElements().addAll(mTo,l);

        System.out.println(" p1="+p1+" move to p2="+p2);

        double angle=Piece.getDirection(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        setFXSoldatOrientation(angle);
                
        System.out.println("rotate "+angle);

        PathTransition  path=new PathTransition();
        path.setDuration(Duration.millis(500));
        path.setPath(p);
        path.setNode(this);

        path.setRate(0.5);
        path.setOrientation(PathTransition.OrientationType.NONE);
        path.setCycleCount(1);
        path.setAutoReverse(false);
        buildFramesMarcheAnim();

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
    protected abstract void buildFramesMarcheAnim();    
    
}
