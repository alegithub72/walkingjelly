/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces;


import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Piece.Pose;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.coeur.pieces.Soldat.Classment;
import jeu.patrouille.coeur.pieces.parts.Corp;
import jeu.patrouille.util.ISOCoverter;
/**
 *
 * @author appleale
 */
public abstract class FXSoldat extends FXPatrouilleSprite {

    Soldat s;
    ImageView blessureImg[];
    ImageView flagImg;
    ImageView classmentImg;
    ImageView selectionneImg;
    ImageView incoscientAuImmobilizer;
    ImageView deathIcon;
    ImageView scared;
    double orientation;
    int feu1;
    int feu2;
    Soldat.Statut stImage;
    Soldat.Pose poseImg;
    double initialAngle;
    public int ngrid;
    int pos;
    public FXSoldat(int w, int h, int pos,Soldat s, String img,FXCarte fxcarte) {
        super(w, h, img, fxcarte);

        this.s=s;
        blessureImg=new ImageView[20];                
        flagImg=new ImageView("americanFlag.png");
        flagImg.setScaleX(4);
        flagImg.setScaleY(4);
        if(s.getClassement()==Classment.SERGENT)  
            classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Classment.SOLDAT)
            classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
        incoscientAuImmobilizer=new ImageView("shocked.png");
        deathIcon=new ImageView("detah.png");
        scared=new ImageView("scared.png");
        initialAngle=90;
        orientation=270;
        this.pos=pos;
        this.poseImg=s.getPose();
        stImage=s.getStatu();
        ngrid=-1;

    }

    public Pose getPoseImg() {
        return poseImg;
    }



    protected void buildGroupSigns() {
        classmentImg.setTranslateX(this.getW());
        classmentImg.setTranslateY(0);
        
      //  blessureImg.setTranslateX(0);
     //   blessureImg.setTranslateY(FXCarte.TILE_SIZE - 10);
    }
    protected void buildShadow(){
    
        DropShadow ds=new DropShadow(5, Color.BLACK);
        //ds.setOffsetX(5);
      //  ds.setOffsetY(15);        
       // imgMainView.setEffect(ds);    
    
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
        //setFrame(0);
        //setFrame(3);
    }

    public void deselectioneFXSoldat() {
        if (getChildren().contains(selectionneImg)) {
            selectionneImg.setVisible(false);
        }
    }





    public void setFXSoldatOrientation(double angle) {
        double angleStart=angle+initialAngle;
        if(angleStart>=360) angleStart=angleStart-360;
        imgMainView.setRotate(0);
        updateSodlatOrientation(angleStart);
        orientation =angle;
        imgMainView.setRotate(angleStart);
    }


    public boolean limitAngleOrientation(double angleValue,double degrez){
        double orietationTemp2=orientation+degrez/2;
        if(orietationTemp2>=360) orietationTemp2=360-orietationTemp2;
        double orietationTemp1=orientation-degrez/2;
        if(orietationTemp2<0) orietationTemp2= 360+orietationTemp2;
        boolean b=angleValue<orietationTemp2 && angleValue>=orietationTemp1;
        System.out.println(orietationTemp1+ "<="+angleValue+" <="+orietationTemp2      );
        return b;
    }
    
    
    Point2D getSceneCoordMove(int i, int j) {
        int posj = fxcarte.getPosJ();
        int posi = fxcarte.getPosI();
        //System.out.println(" get coord "+i+","+j+" posI,posj="+posi+","+posj);
        int scrollI = i - posi;
        int scrollJ = j - posj;
        double x0 = scrollJ * FXCarte.TILE_SIZE;
        double y0 = scrollI * FXCarte.TILE_SIZE;
        //y0 = esteticCorrectionY0(scrollI, y0);
        //x0 = esteticCorrectionX0(scrollJ, x0);
        Point2D p = new Point2D(x0, y0);
        return p;
    }



    public void updatePose(){
    
    if(s.getPose()==Pose.PRONE) pronePosition();
    else if(s.getPose()==Pose.DROIT) droitPosition();
    
    }
    
    
    //TODO Da rifare con unn angolo 360 gradi....
    void updateSodlatOrientation(double angle){
        if(angle>=360-22.5 && angle<=360 ) 
            this.s.setFace(Piece.Direction.N);
        else if(angle>=0 && angle<=22.5)this.s.setFace(Piece.Direction.N);
        else if(angle>22.5 && angle<22.5+45 )s.setFace(Piece.Direction.NW);        
        else if(angle>=22.5+45 && angle<=22.5+90)s.setFace(Piece.Direction.W);
        else if(angle>22.5+90 && angle<22.5+125) s.setFace(Piece.Direction.SW);        
        else if((angle>=22.5+125 && angle <=170+22.5 ))  s.setFace(Piece.Direction.S);
        else if(angle>170+22.5 && angle<215+22.5) s.setFace(Piece.Direction.SE);
        else if(angle>=215+22.5 && angle<=260+22.5) s.setFace(Piece.Direction.E);
        else if(angle>260+22.5 && angle <305+22.5) s.setFace(Piece.Direction.NE);
        System.out.println("------>"+angle+"-----orientation updataed--->"+s.getFace());
        }    

    public boolean isPossibleAchive(ActionType type,double dist){  
      return ( dist>0 
              && s.getTempDisponible()>0 
              && s.isPossibleAchive(type,dist));
    
    }
    public void createFXSoldat(){
        buildFrameImages();

        if(!getChildren().contains(flagImg))
            getChildren().add(flagImg);
        if(!getChildren().contains(classmentImg))
            getChildren().add(classmentImg);    

        defaultFrame();
        //this.relocate(x, y);
        buildGroupSigns();
        buildShadow();
 
        
    
    }    
  

    
    public void playReloadMag(){
        System.out.println("--------------RELOAD----------------------");
        fxcarte.centerScrollArea(s.getI(), s.getJ());
        fxcarte.refreshCarte();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource("clipin3.wav");         
        AudioClip media=new AudioClip(url.toString());
        media.play();
       
        PauseTransition p=new PauseTransition(Duration.seconds(2));
        
        p.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(event.getEventType()==ActionEvent.ACTION)
                fxcarte.setAnimOn(false);
                event.consume();
            }
        });
        p.play();
        System.out.println("--------------RELOAD-----------------------");
    }    
    public void signOff(){
       flagImg.setVisible(false);
       classmentImg.setVisible(false);
       incoscientAuImmobilizer.setVisible(false);
       deathIcon.setVisible(false);
       getChildren().removeAll(blessureImg);
       scared.setVisible(false);
    }
    public void signON(){
       flagImg.setVisible(true);
       classmentImg.setVisible(true);
       flagImg.toFront();
       classmentImg.toFront();
       getChildren().removeAll(blessureImg);
       if(!s.isKIA()  )   {
           int blN=s.isLesion();
        for(int n=0;n<blN;n++){
            blessureImg[n] = new ImageView("wound.png");
            if(n>=3)blessureImg[n].setTranslateX((n-3)*10);
            else blessureImg[n].setTranslateX(n*10);
            if(n<3)blessureImg[n].setTranslateY(FXCarte.TILE_SIZE - 10);
            else blessureImg[n].setTranslateY(FXCarte.TILE_SIZE - 20);
            blessureImg[n].toFront();
            getChildren().add(blessureImg[n]);

        }
       }
       getChildren().remove(incoscientAuImmobilizer);
       getChildren().remove(scared);
       if((s.isIncoscient() || s.isImmobilize())    && !s.isKIA()) {
               incoscientAuImmobilizer.toFront();
               incoscientAuImmobilizer.relocate(FXCarte.TILE_SIZE-16, FXCarte.TILE_SIZE-32);
               incoscientAuImmobilizer.setVisible(true);
            if(!getChildren().contains(incoscientAuImmobilizer)) getChildren().add(incoscientAuImmobilizer);
       }
       else if(s.isKIA()){
           if(!getChildren().contains(deathIcon)) {
               getChildren().add(deathIcon);
               deathIcon.relocate(FXCarte.TILE_SIZE-16, 0);
           }
           deathIcon.setVisible(true);
           
       }
       if(s.isChoc() && !s.isKIA() && !s.isIncoscient()){
           if(!getChildren().contains(scared))getChildren().add(scared);
           scared.relocate(FXCarte.TILE_SIZE-16, FXCarte.TILE_SIZE-16);
           scared.setVisible(true);
       }else if(s.isIncoscient() ){
           scared=new ImageView("incoscient.png");
           if(!getChildren().contains(scared))getChildren().add(scared);
           scared.relocate(FXCarte.TILE_SIZE-16, FXCarte.TILE_SIZE-16);
           scared.setVisible(true);           
       }
      
    }

    protected abstract void buildFramesMarcheAnim();    
    public abstract void buildFramesFeuAnim();
    public abstract void buildBlessAnim();
    protected abstract void buildCrawlAnim();
    protected abstract void buildFramesCoursAnim();    
    public abstract void feuFrame();
    public abstract void droitPosition();
    public abstract void pronePosition();
    abstract void blessFrame(Corp.CorpParts location);
        

     

    
    





 
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    double angleRotation(int i2 ,int j2){
        Point2D gridXY1=FXCarte.getXYCarteAbsoluteCoord(s.getI(),s.getJ());
        Point2D gridXY2=FXCarte.getXYCarteAbsoluteCoord(i2,j2);
        double angle=FXCarte.angleRotation(gridXY1.getX(),gridXY1.getY(), gridXY2.getX(),gridXY2.getY());    
        return  angle;
    }
}
