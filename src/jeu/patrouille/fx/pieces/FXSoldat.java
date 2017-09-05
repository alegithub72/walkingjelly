/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces;


import java.net.URL;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import jeu.patrouille.coeur.actions.BandageAction;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.FeuAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.coeur.pieces.Soldat.Classment;
import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.fx.menu.eventhandler.EndAnimPauseHandler;
import jeu.patrouille.fx.menu.eventhandler.StartDeamonThreadEventHandler;
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
    double initialAngle;
    int pos;
    public FXSoldat(int w, int h, int pos,Soldat s, String img,FXCarte fxcarte) {
        super(w, h, img, fxcarte);
        this.s=s;
        blessureImg=new ImageView[20];                
        flagImg=new ImageView("americanFlag.png");
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
        stImage=Soldat.Statut.NORMAL;

    }



    protected void buildGroupSigns() {
        classmentImg.setTranslateX(FXCarte.TILE_SIZE - 20);
        classmentImg.setTranslateY(0);
        
      //  blessureImg.setTranslateX(0);
     //   blessureImg.setTranslateY(FXCarte.TILE_SIZE - 10);
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
        //setFrame(0);
        //setFrame(3);
    }

    public void deselectioneFXSoldat() {
        if (getChildren().contains(selectionneImg)) {
            selectionneImg.setVisible(false);
        }
    }

    public void pronePostion(){
        this.setW(100);
        this.buildFrameImages(new Image("proneUS.png"));
        
    }



    public void setFXSoldatOrientation(double angle) {
        double angleStart=angle+initialAngle;
        if(angleStart>=360) angleStart=angleStart-360;
        imgView.setRotate(0);
        updateSodlatOrientation(angleStart);
        orientation =angleStart+ angle;
        imgView.setRotate(angleStart);
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
        //System.out.println("reset position " + s.toStringSimple());
        int i0 = s.getI();
        int j0 = s.getJ();
        Point2D p = fxcarte.getSceneCoordForRefreshCarte(i0, j0);
        double x0 = p.getX();
        double y0 = p.getY();
        x0 = x0 + (20 * k);
        y0 = y0 + (20 * k);
        //int scrollJ = j0 - fxcarte.getPosJ();
        //int scrollI = i0 - fxcarte.getPosI();
        //System.out.println(" enable node--->" + s.getNom() + "x0,y0=" + x0 + "," + y0);
        //x0 = esteticCorrectionX0(scrollJ, x0);
        //y0 = esteticCorrectionY0(scrollI, y0);
        setTranslateX(x0);
        setTranslateY(y0);
        toFront();
        this.setVisible(true);
        if(s.getStatu().ordinal()>stImage.ordinal()) updateBlesseImage(s.getStatu());
        signON();
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

    public boolean isDistanceLessMarcheMax(double dist){  
      return ( dist>0 
              && s.getTempDisponible()>0 
              && s.isDistanceLessMarcheMax(dist));
    
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
   public  void playMarche(MarcheAction  act){
        
        System.out.println("------------- FXSOLDAT PLAY ANIM MARCHE---------------->"+act+"-------->"+act.getProtagoniste().toStringSimple()+"<---------");

        //System.out.println("soldato anim:"+act.getProtagoniste());
        System.out.println(" IN PLAY MARCHE");
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

        double angle=FXCarte.angleRotation(p1.getX(),p1.getY(),p2.getX(),p2.getY());
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
              defaultFrame();
              path.stop();
              fxcarte.setAnimOn(false);
              System.out.println("FXSOLDAT ANIM----->STOP");
//              }
             event.consume();
            }
        });

        System.out.println("FXSOLDAT ANIM------> PLAY");


        System.out.println("------------- FXSOLDAT PLAY ANIM MARCHE ---------FINE------->--------><---------");
    } 
    public void playBandage(){
        fxcarte.centerScrollArea(s.getI(), s.getJ());
        fxcarte.refreshCarte();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource("bandage.mp3");        
        AudioClip media=new AudioClip(url.toString());
        media.play();
        PauseTransition p=new PauseTransition(Duration.seconds(2));
        
        p.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateBandageImage(s.getStatu());
                fxcarte.refreshCarte();
                fxcarte.setAnimOn(false);
                event.consume();
            }
        });
        p.play();
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
       if(!s.isKIA() && s.getSante()>0 )   {
           int blN=s.isLesion(Lesion.Degre.LEGER);
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
    public void playFeu(FeuAction act){
       buildFramesFeuAnim();
       this.fxcarte.centerScrollArea(s.getI(), s.getJ());
       fxcarte.refreshCarte();
       fxcarte.refreshCarteAllFXSoldatViewPosition();
       setFXSoldatOrientation(act.getAngle());
       signOff();
       this.frameAnimTimer[0].start();
       
       Soldat target=(Soldat)act.getAntagoniste();
       
       Thread t=new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println("new thread");
               
               while(!frameAnimTimer[0].isStopped()) 
               System.out.print("");
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                        System.out.println("$$$$$$$$$$$$$$$$$$$$ RUN TARGETPLAY BLESSED $$$$$$$$$$$$$$$$$$$");
                        int i1=act.getI1(),j1=act.getJ1();
                        if(target!=null){
                            i1=target.getI();j1=target.getJ();
                        }
                        fxcarte.centerScrollArea(i1, j1);
                        fxcarte.refreshCarte();
                        fxcarte.refreshCarteAllFXSoldatViewPosition();
                        if(target!=null){                       
                        FXSoldat fxtarget=null;
                        if( !target.isUS()) fxtarget=  fxcarte.findFXHostile(target);
                        else fxtarget=fxcarte.findFXUSSoldat(target);
                            fxtarget.fxPlayTargetBlesse((Soldat)act.getAntagoniste());
                            fxtarget.toBack();
                            fxcarte.refreshCarte();
                            fxcarte.refreshCarteAllFXSoldatViewPosition();                            
                        }
                        PauseTransition pause=new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(new EndAnimPauseHandler(fxcarte));
                        pause.play();
                        System.out.println("$$$$$$$$$$$$$$$$$$$$ RUN TARGETPLAY BLESSED $$$$$$------FINE--------$$$$$$$$$$$$$");
                   }
               });
           }
       });
        PauseTransition pause=new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(new StartDeamonThreadEventHandler(t));
        pause.play();
        
       
   
   
   }
    protected abstract void buildFramesMarcheAnim();    
    public abstract void buildFramesFeuAnim();
    public abstract void buildBlessAnim();
    public abstract void feuFrame();
     
    public void fxPlayTargetBlesse(Soldat target){

        Lesion l=target.getLastLesion();
        System.out.println("load new image blessed of "+target.getStatu()+", last blessed "+l);
        
        
        if(l==null) return ;
        System.out.println("ordinal s.getStatut"+s.getStatu().ordinal()+" ordinal blessed "+l.getStatu().ordinal());        
        if(s.getStatu().ordinal()> stImage.ordinal())updateBlesseImage(s.getStatu());
    }
    
    
public void updateBlesseImage(Soldat.Statut statut){        
    switch (statut) {
        case INCONSCENT:
            if (s.isUS()) {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(2);
            }
            this.stImage=Soldat.Statut.INCONSCENT;
            playBlessedAnim();
            break;
        case MORT:
            if (s.isUS()) {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(5);
            }
            this.stImage=Soldat.Statut.MORT;
            playBlessedAnim();            
            break;
        case CRITIQUE:

            if (s.isUS()) {
                setW(100);
                Image img = new Image("frameProneUSSoldierBlessed.png");
                buildFrameImages(img);
                setFrame(3);
                feu1 = 4;
                feu2 = 5;
                initialAngle=0;
            } else {
                setW(100);
                //TODO da cambiare con un prone....
                Image img = new Image("frameHostileBlessedCrititque.png");
                buildFrameImages(img);
                setFrame(0);
                feu1=1;
                feu2=2;
            }
            System.out.println("%%%%%%% CRITIQUE");
            playBlessedAnim();
            stImage=Soldat.Statut.CRITIQUE;

            break;
        case GRAVE:

            if (!s.isUS()) {

                Image img = new Image("frameHostileBlessed.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2Blessed.png");
                buildFrameImages(img);
                setFrame(0);
            }
            stImage=Soldat.Statut.GRAVE;
            break;
        case GRAVE_BRASE_DROITE:
            if (!s.isUS()) {

                Image img = new Image("frameHostileGraveBraseDroitePaArme.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2PaArmeBlessed.png");
                buildFrameImages(img);
                setFrame(0);
            }
            stImage=Soldat.Statut.GRAVE_BRASE_DROITE;
            break;
        case GRAVE_BRASE_GAUCHE:
            if (!s.isUS()) {

                Image img = new Image("frameHostileGraveBrasGaucheBlessedPaArme.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2PaArmeBlessed.png");
                buildFrameImages(img);
                setFrame(0);
            }
            stImage=Soldat.Statut.GRAVE_BRASE_GAUCHE;
            break;
        case GRAVE_TETE:

            setW(100);
            Image img = new Image("feritoUS.png");
            buildFrameImages(img);
            if (!s.isUS()) {
                setFrame(5);
            } else {
                setFrame(3);
            }
            System.out.println("%%%%%%% GRAVE_TETE");
            playBlessedAnim();
            stImage=Soldat.Statut.GRAVE_TETE;
            break;
        case LEGER_BLESSE:
            if (!s.isUS()) {

                //img=new Image("frameHostileBlessed.png");
                //buildFrameImages(img);
                //setFrame(0);
            } else {
                //img=new Image("frameSoldierUS2Blessed.png");
                //buildFrameImages(img);
                //setFrame(0);                    
            }
            System.out.println("%%%%%%% LEGER_BLESSE");
            //TODO nothing bandage ,sound grunt leggero
            stImage=Soldat.Statut.LEGER_BLESSE;
            break;
        case MANQUE:
            System.out.println("%%%%%%% MANQUE");
            //TODO nothing,  colpi vicono 
            break;
        default:

            break;

    }

 }

public void updateBandageImage(Soldat.Statut statut){        
    switch (statut) {
        case INCONSCENT:
            if (s.isUS()) {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(7);
            } else {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                setFrame(1);
            }

            playBlessedAnim();
            break;
        case MORT:
       
            break;
        case CRITIQUE:

            if (s.isUS()) {
                setW(100);
                Image img = new Image("frameProneUSSoldierBlessed.png");
                buildFrameImages(img);
                setFrame(0);
                feu1 = 1;
                feu2 = 2;
                initialAngle=0;
            } else {
                setW(100);
                //TODO da cambiare con un prone....
                Image img = new Image("frameHostileBlessedCrititque.png");
                buildFrameImages(img);
                setFrame(3);
                feu1=4;
                feu2=5;
            }
            System.out.println("%%%%%%% CRITIQUE");
            playBlessedAnim();


            break;
        case GRAVE:

            if (!s.isUS()) {

                Image img = new Image("frameHostileBandageGraveBraseGauche.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2Bendage.png");
                buildFrameImages(img);
                setFrame(0);
            }

            break;
        case GRAVE_BRASE_DROITE:
            if (!s.isUS()) {

                Image img = new Image("frameHostileBandageGraveBraseGauche.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2Bendage.png");
                buildFrameImages(img);
                setFrame(0);
            }

            break;
        case GRAVE_BRASE_GAUCHE:
            if (!s.isUS()) {

                Image img = new Image("frameHostileBandageGraveBraseGauche.png");
                buildFrameImages(img);
                setFrame(3);
            } else {
                Image img = new Image("frameSoldierUS2Bendage.png");
                buildFrameImages(img);
                setFrame(0);
            }

            break;
        case GRAVE_TETE:

            setW(100);
            Image img = new Image("feritoUS.png");
            buildFrameImages(img);
            if (!s.isUS()) {
                setFrame(1);
            } else {
                setFrame(7);
            }
            System.out.println("%%%%%%% GRAVE_TETE");
            playBlessedAnim();

            break;
        case LEGER_BLESSE:
            if (!s.isUS()) {

                //img=new Image("frameHostileBlessed.png");
                //buildFrameImages(img);
                //setFrame(0);
            } else {
                //img=new Image("frameSoldierUS2Blessed.png");
                //buildFrameImages(img);
                //setFrame(0);                    
            }
            System.out.println("%%%%%%% LEGER_BLESSE");
            //TODO nothing bandage ,sound grunt leggero
            stImage=Soldat.Statut.LEGER_BLESSE;
            break;
        case MANQUE:
            System.out.println("%%%%%%% MANQUE");
            //TODO nothing,  colpi vicono 
            break;
        default:

            break;

    }

 }    


  void playBlessedAnim(){
      buildBlessAnim();
      if((s.getStatu()==Soldat.Statut.MORT ||
         s.getStatu()==Soldat.Statut.INCONSCENT ||
         s.getStatu()==Soldat.Statut.GRAVE_TETE  ) 
         && !s.isUS() )
          imgView.setTranslateX(-FXCarte.TILE_SIZE);
      else     
          imgView.setTranslateX(-25);
      //imgView.setTranslateY(25);
      setFXSoldatOrientation(Math.random()*360 );
      frameAnimTimer[0].start();
      signOff();
      buildShadow();
  
  
  }
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
