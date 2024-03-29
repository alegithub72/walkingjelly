/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;

import java.io.IOException;
import java.net.URL;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.MoteurDeJoeur;
import jeu.patrouille.coeur.actions.AbstractAction;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.CoursAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.jeurs.FXAIJoueur;
import jeu.patrouille.fx.jeurs.FXMouseJeurs;
import jeu.patrouille.fx.menu.AbstractMenuItemButton;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuConfirmActionEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuRangeDisplayHandler;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsFXCarteEventHandler;
import jeu.patrouille.fx.pieces.FXHostile;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.sprite.CursorHelper;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.fx.sprite.Sprite;
import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author Alessio Sardaro
 */
public  class FXCarte extends Parent implements GraficCarteInterface{
    public static double INCHxPIXEL=0.02;
    public static final int TOP_H = 0;
    public static final int BAR_H = 50;
    public static final int DROIT_BAR_W = 200;
    public static final int TILE_SIZE = 50;
    public static double PIXEL_SCROLL_AREA_H=600;
    public static double PIXEL_SCROLL_AREA_W=800;
    public static int AREA_SCROLL_J_W;
    public static int AREA_SCROLL_I_H;

    Group rootGroup;

    Canvas c1;
    Canvas c2;
    Carte carte;
    MoteurDeJoeur mj;
    int posJ = 0;
    int posI = 0;
    boolean switchsCanvas = true;
    int mapW;
    int mapH;
    FXPlanche fxpl;
    FXItemsPointerHelper fxIMHelper;
    boolean commanNotvalid;
    public Cursor current;
    boolean animOn;
    FXAIJoueur jHOST ;
    FXMouseJeurs jUS ;
    FXSoldat[] fxequipeUS ;
    FXSoldat[] fxequipeHost ;
    FXPatrouilleSprite actionIcon;
    boolean debug=true;
    
    public FXCarte(FXPlanche fxpl) throws IOException{
        
        // arrow = new Sprite(100, 100, 100, 100, "arrowPng.png", null);
        this.fxpl=fxpl;
        rootGroup = new Group();    
        

        carte = new Carte("src/mapDesert.txt");
        carte.loadMap();
        

        // mj.setActiveJeur(MoteurDeJoeur.JEUR_US);
 
        AREA_SCROLL_J_W = (int) (PIXEL_SCROLL_AREA_W / TILE_SIZE);
        AREA_SCROLL_I_H = (int) (PIXEL_SCROLL_AREA_H / TILE_SIZE);
        System.out.println(PIXEL_SCROLL_AREA_W + "," + PIXEL_SCROLL_AREA_H);

        
        mapW = carte.getMapW();
        mapH = carte.getMapH();
        c1 = new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        rootGroup.getChildren().add(c1);
        c2 = new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        rootGroup.getChildren().add(c2);
        setCursor(Cursor.HAND);

                    
        //ImageView test = new ImageView(new Image("menuItem.png"));

        
        

       
              
        
    }

    public FXPlanche getFxpl() {
        return fxpl;
    }

    public FXItemsPointerHelper getFxIMHelper() {
        return fxIMHelper;
    }

    public void initFXCarte() throws IOException{
        
        jHOST = new FXAIJoueur(this);
        jUS = new FXMouseJeurs(GeneriqueJoeurs.JOEUR_US, this);

        fxequipeUS = jUS.getFxEquipe();
        fxequipeHost = jHOST.getFxEquipe();
       
        this.mj = new MoteurDeJoeur(jUS, jHOST, carte);
        mj.add(this);
        buildCarteScroll(c1);
        this.getChildren().add(rootGroup);
         
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
            }

        });
        this.setOnScroll(new ScrollEventHandler(this));

        this.setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(getMenu()));
        animOn=false;
       
    }

    public FXMenuItemsDossier getMenu() {
        return fxpl.menu;
    }
    
    public void playTurn(){
        
        getMenu().closeFXCarteMenuItems();
        setOnMouseMoved(null);
        setOnMouseClicked(null);
        setOnScroll(null);
        deselectionneAllSoldats();
        removeMenuItemsMenuOnFXUSEquipe();
        removeMenuItemsonFXHostileEquipe();
        //fxpl.bar=new ProgressIndicator();
        //fxpl.bar.setTranslateY(PIXEL_SCROLL_AREA_H);
        //fxpl.bar.setTranslateX(PIXEL_SCROLL_AREA_W+DROIT_BAR_W-50);
        //fxpl.rootScene.getChildren().add(fxpl.bar);
        fxpl.endButton.setEffect(new GaussianBlur());
        mj.debutRond();
        fxpl.sendMessageToPlayer("      START TURN "+(mj.getTurn()+1));
        
    }
    @Override
    public boolean isAnimOn() {
        return animOn;
    }

    @Override
   synchronized public void setAnimOn(boolean animPiece) {
        this.animOn = animPiece;
    }
     

   

    @Override
    public  void play(BaseAction act1,BaseAction act2) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("*****************FXCARTE PLAY****INIZIO****************");
  
        
        //initFXHelperInstance(null);
        if (act1.isProtagonisteTypeSoldat()
                && act1.isProtagonisteHostile()) 
            playHostile(act1,act2);
        
        else if (act1.isProtagonisteTypeSoldat() 
                && !act1.isProtagonisteHostile()) 
            
            playUSSoldat(act1,act2);
        
        else {
            
            setAnimOn(false);
            throw new RuntimeException("NOT US OR HOSTILE SOLDAT????");

        }

        System.out.println("*****************FXCARTE PLAY****FINE****************");
        
    }
    
private boolean isNeedeCenterScrollAreaUpdate(int centerI,int centerJ){
    boolean b = false;
    return (((centerI - posI) < 3)
            || ((FXCarte.AREA_SCROLL_I_H - (centerI - posI)) < 3)
            || ((centerJ - posJ) < 3)
            || ((FXCarte.AREA_SCROLL_J_W - (centerJ - posJ)) < 3));


}
public void centerScrollArea(int i,int j){
        int h2=(FXCarte.AREA_SCROLL_I_H/2);
        int w2=(FXCarte.AREA_SCROLL_J_W/2);
        if((i+h2)>(Carte.CARTE_SIZE_I) )
            posI=i-FXCarte.AREA_SCROLL_I_H+1;
        else if(i-h2<=0)
            posI=0;        
        else  posI=i-h2;
        
        if( ( (j+w2)>(Carte.CARTE_SIZE_J))  )
            posJ=j-FXCarte.AREA_SCROLL_J_W+1;
        else if(j-w2<=0)
            posJ=0;
        else posJ=j-w2;
       
}


private boolean isScrollAreaChanged(int i1,int j1){
        int centerI=i1-(FXCarte.AREA_SCROLL_I_H/2),
            centerJ=j1-(FXCarte.AREA_SCROLL_J_W/2);
        return !(centerI==posI && centerJ==posJ);
       

}
    


    private void playHostile(BaseAction act1,BaseAction act2){
        Soldat sold=(Soldat)act1.getProtagoniste();
        FXHostile sfx = (FXHostile) findFXHostile(sold);
       // fxIMHelper.setFXSeletctionee(sfx);
        centerScrollArea(sold.getI(), sold.getJ());
        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        ActionType type=act1.getType();
        switch (type) {
            case MARCHE:
                sfx.playMarche((MarcheAction) act1);
                break;
            case COURS:
                sfx.playMarche((CoursAction)act1);
                break;
            case FEU:
            case FEU_VISER:
                sfx.playFeu(act2);
                break;
            case BANDAGE:
                if(act1.getVersus()==AbstractAction.SubjectType.MYSELF)
                    sfx.playBandage();
                else {
                    Soldat target=(Soldat)act1.getAntagoniste();                    
                    FXSoldat sfxTarget=findFXHostile(target);
                    if(sfxTarget==null) sfxTarget=findFXUSSoldat(target);
                    sfxTarget.playBandage();
                }
                break;
            case ARME_RECHARGE:
                sfx.playReloadMag();
                break;
             default:
                System.out.println("$$$$NON animation$$$$");
                setAnimOn(false);
                break;
        }
        

   
    
    }
    
    private void playUSSoldat(BaseAction act1,BaseAction act2){
        Soldat sold=(Soldat) act1.getProtagoniste();
        FXSoldat sfx = findFXUSSoldat(sold);//TODO cercare by name or id
        centerScrollArea(sold.getI(), sold.getJ());
        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        // fxIMHelper.setFXSeletctionee(sfx);
        
        switch (act1.getType()) {
            case MARCHE:
                sfx.playMarche((MarcheAction) act1);
                break;
            case COURS:
                sfx.playMarche((CoursAction)act1);
                break;     
            case FEU:    
            case FEU_VISER:
                sfx.playFeu(act2);
                break;
            case BANDAGE:
                if(act1.getVersus()==AbstractAction.SubjectType.MYSELF)
                    sfx.playBandage();
                else  {
                    Soldat target=(Soldat)act1.getAntagoniste();
                    FXSoldat sfxTarget=findFXUSSoldat(target);
                    if(sfxTarget==null) sfxTarget=findFXHostile(target);
                    sfxTarget.playBandage();
                }
                break;
            case ARME_RECHARGE:
                 sfx.playReloadMag();
                break;
            default:
                System.out.println("$$$$non animation$$$");
                setAnimOn(false);
                break;
        }
  
    }


    

    
    public FXSoldat findFXUSSoldat(Soldat s){
        FXSoldat searchedFX=null;
        for(FXSoldat sfx:  this.fxequipeUS){
            if(sfx.getSoldat().getNomDeFamilie().equals(s.getNomDeFamilie())&&
                    sfx.getSoldat().getNom().equals(s.getNom()))searchedFX=sfx;
        }
        return searchedFX;
    }
    public FXHostile findFXHostile(Soldat  s){
        
        FXHostile searchedFX=null;
        for(FXSoldat sfx:  this.fxequipeHost){
            if(sfx.getSoldat().getNomDeFamilie().equals(s.getNomDeFamilie())&&
                    sfx.getSoldat().getNom().equals(s.getNom()))
                searchedFX=(FXHostile)sfx;
        }
        return searchedFX;
    }    
    


    public void sendMessageToPlayer(String text) {
        fxpl.sendMessageToPlayer(text);
    }
    
    

    
    private void addHelperInstance(BaseAction act){
            fxIMHelper.setAct(act);
    }    
    

    public  boolean isFXHelperActionSeletiones() {
        return fxIMHelper.isActionSelectione();
    }

    

    
    synchronized public void annulleCommand() {
        fxIMHelper.removeDisplayRange();
        fxIMHelper.setCommanNotvalid(true);
        setOnMouseMoved(null);
        reMountFXCarteMenuItemsAndScroll();
        setCursor(Cursor.DEFAULT);
        fxpl.sendMessageToPlayer("Action effacer");

    }

   
    
 synchronized public void confirmMarcheActionCommand(AbstractMenuItemButton item, double mousex, double mousey)throws Exception {

        PointCarte p=getAbsoluteIJCoord(mousex, mousey);
        //System.out.println(fxIMHelper.getSeletctionee()+" walk here i1=" + i1 + " j1=" + j1);
        Soldat s=fxIMHelper.getSeletctionee();
        double dpixel=displayGraficRangeHelper(mousex, mousey,Color.WHITE);
        
        double inch=(dpixel*INCHxPIXEL);
        fxIMHelper.removeDisplayRange();

        PointCarte obst=fxIMHelper.carteValiderRoute();
        setCursor(Cursor.HAND);
        if (!fxIMHelper.isCommanNotvalid() && s.isPossibleAchive(fxIMHelper.getAct().getType(),inch)
                && obst==null ) {
            
            BaseAction act=  item.buildMenuItemAction();
            addHelperInstance(act);
            fxIMHelper.setArrivalCarteCoord(p.getI(),p.getJ());
            fxIMHelper.addActionToSoldat();
            fxpl.imprimerFXPLInfo(s);
            fxpl.visualizeActionBarActual();
            getMenu().closeFXCarteMenuItems();
            //buildFXCarteMenuItems();
            //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
            fxpl.sendMessageToPlayer(s.toStringSimple()+" " +fxIMHelper.toString());
            //resetFXCarteHelperAction();
         

        } else {
       
            fxIMHelper.removeDisplayRange();
            BaseAction act=  item.buildMenuItemAction();
            addHelperInstance(act);
            fxpl.sendMessageToPlayer("Action non valide");
                     
        }
            setOnMouseMoved(null);
            reMountFXCarteMenuItemsAndScroll();   
            fxIMHelper.setActionSeletione(false);
    }





    
    @Override
    public void reMountFXCarteMenuItemsAndScroll(){
        setCursor(Cursor.HAND);
       // fxpl.rootScene.getChildren().remove(fxpl.bar);
        fxpl.endButton.setEffect(null);
        //fxpl.bar=null;
        this.setOnScroll(new ScrollEventHandler(this));
        this.setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(getMenu()));
        mountMenuItemsOnFXHostileEquipe();
        mountMenuItemsOnFXUSEquipe();
        
    }

    public Carte getCarte() {
        return carte;
    }


    
    final void setFXCarteCursor(Cursor value) {
        current = value;
        if(value!=getCursor())
        setCursor(value);
    }    
    
    synchronized public void clickOnButtonItems(AbstractMenuItemButton item) {

 
        BaseAction act = item.buildMenuItemAction();
        setOnScroll(null);
        if (act.getType() == ActionType.MARCHE) {
            
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler( item, this));
            setFXCarteCursor(Cursor.HAND);
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this,item.getActionType()));
            getMenu().closeFXCarteMenuItems();
            fxpl.sendMessageToPlayer("Choisir un emplacement");
           

        } else if (act.getType() == ActionType.FEU_VISER||
                act.getType()==ActionType.FEU) {
            if(fxIMHelper.getSeletctionee().isUS()) 
                this.removeMenuItemsonFXHostileEquipe();
            else this.removeMenuItemsMenuOnFXUSEquipe();
           
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler(item,this));
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this, item.getActionType()));
            setFXCarteCursor(Cursor.HAND);
            getMenu().closeFXCarteMenuItems();
            fxpl.sendMessageToPlayer("Choisir an Ojective");
            
        }else if(act.getType()==ActionType.BANDAGE || 
                act.getType()==ActionType.ARME_RECHARGE){
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler(item, this));
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this, item.getActionType()));
            getMenu().closeFXCarteMenuItems();
            setFXCarteCursor(Cursor.HAND);
            removeMenuItemsMenuOnFXUSEquipe();
            removeMenuItemsonFXHostileEquipe();
            fxpl.sendMessageToPlayer("Bandage yourself or a close teammate to you");
        }else if(act.getType()==ActionType.COURS){
            
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler( item, this));
            setFXCarteCursor(Cursor.HAND);
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this,item.getActionType()));
            getMenu().closeFXCarteMenuItems();
            fxpl.sendMessageToPlayer("Choisir un emplacement");
            
            
        
        }

    }
    
    @Deprecated
    private MoteurDeJoeur getMj() {
  
      return mj;
    }
   private  GeneriqueJoeurs getActiveJeur() {
        return mj.getActiveJeur();
    }

   protected PointCarte carteValiderRoute(){
       return fxIMHelper.carteValiderRoute();
   }  

    private int mapLastJ() {
        return fxIMHelper.mapLastJ();
    }

    private int mapLastI() {
        return fxIMHelper.mapLastI();
    }
    
    
    
    


   
   protected void initFXHelperInstance(FXSoldat sfx){
       if (sfx != null) {
           Soldat s=sfx.getSoldat();
           
           if (s.getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST) {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           } else {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_US);
           }
           mj.getActiveJeur().setPieceSelectionee(s);
       }
       fxIMHelper= new FXItemsPointerHelper(sfx, this);
       
    }   
   

   
 

    


    public void addSprite(Sprite s){
        rootGroup.getChildren().add(s);
    
    }
    private Group getRootGroup() {
        return rootGroup;
    }


    private void visualizeRangePointers(double mousex,double mousey){
        CursorHelper c=fxIMHelper.getDisplayRange();
        if(!rootGroup.getChildren().contains(c))    
          rootGroup.getChildren().add(c);          
        
        int i=(int)(mousey/FXCarte.TILE_SIZE);
        int j=(int)(mousex/FXCarte.TILE_SIZE);
        double xgrid=(j*FXCarte.TILE_SIZE);
        double ygrid=(i*FXCarte.TILE_SIZE);        
        c.setTranslateX(xgrid);
        c.setTranslateY(ygrid);
        c.toFront();                        
    }



    
    
        
    

    private void setRangeCursorHelpers(int rangeCursorHelper) {
        fxIMHelper.setRangeCursorHelper(rangeCursorHelper);        

        
    }

    private void resetCursorHelper() {
        fxIMHelper.resetCursorHelper();

    }

    private void addSoldataSelectioneeAction() throws Exception{
        fxIMHelper.addActionToSoldat();
    }

   private BaseAction getMJActualAction(){
       
      return getActiveJeur().getActual();

   } 

    private void setMJActualAction(BaseAction act){
        getActiveJeur().setActual(act);
    }    



    protected FXItemsPointerHelper getHelper() {
        return fxIMHelper;
    }

    private void setHelper(FXItemsPointerHelper helper) {
        this.fxIMHelper = helper;
    }


    
    private void resetHelperAction(){
        fxIMHelper.setAct(null);
    }



    
    
    private Canvas activeCanvas(){
        if (switchsCanvas) {
            return c1;
        }
        return c2;
    }
    
    
    
    public synchronized void displayMarcheRangeAction(double mousex, double mousey) {


        Soldat s=fxIMHelper.getSeletctionee();
        double dpixel=displayGraficRangeHelper(mousex, mousey,Color.WHITE);
        FXSoldat sfx= fxIMHelper.getFXSoldatSelectionee();
        double inch=(dpixel*INCHxPIXEL);
        PointCarte p=getAbsoluteIJCoord(mousex, mousey);
        fxIMHelper.setArrivalCarteCoord(p.getI(), p.getJ());

        double angle=angleDisplayRange(mousex, mousey);
        fxIMHelper.getFXSoldatSelectionee().setFXSoldatOrientation(angle);
       
        
        PointCarte obst=fxIMHelper.carteValiderRoute();
        
        if (  s.isPossibleAchive(fxIMHelper.getAct().getType(),inch)
                && obst==null ) {
            fxIMHelper.setCommanNotvalid(false);
            setFXCarteCursor(Cursor.HAND);
            fxIMHelper.removeDisplayRange();
            resetCursorHelper();
             if(!s.isUS()) sfx.setFrame(2);
            fxpl.sendMessageToPlayer("");
            visualizeRangePointers(mousex, mousey);

           
        } else {
            fxIMHelper.removeDisplayRange();
            fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
            fxIMHelper.setCommanNotvalid(true);
            if(obst!=null) fxpl.sendMessageToPlayer("Obstacole sur sentier: "+obst.getI()+","+obst.getJ());
            visualizeRangePointers(mousex, mousey);
            if(!s.isUS()) sfx.setFrame(3);
            System.out.println("---->out of range<----");
        }
        System.out.println("distance"+(inch*1.8280)+" metri o inches:"+inch+" pixel"+dpixel );
        System.out.println("angle------------------------------------------>"+angle);        
    }
    

    public static double  angleRotation(double x0,double y0,double x1,double y1){
        
        double x$=x1-x0,y$=y1-y0;
        double angle=0;
        if(x$==0 && y$>0)  angle=90; 
        else if(x$==0 && y$<=0)  angle=(270);
        else if(y$==0 && x$<=0) angle=180;
        else if(x$>0  && y$>0) angle=Math.toDegrees( Math.atan(y$/x$));
        else if(x$>0 && y$<0)  angle= 90-Math.abs(Math.toDegrees(Math.atan(y$/x$)))+270;
        else if(x$<0 && y$>0) angle= (90-Math.abs(Math.toDegrees(Math.atan(y$/x$))))+90;
        else if(x$<0 && y$<0) angle= Math.toDegrees(  Math.atan(y$/x$))+180;
        
        System.out.println("x0,y0="+x0+","+y0+" x1,y1="+x1+","+y1);
        System.out.println(" x$,y$ "+x$+","+y$ );
            return angle;
    
    }
    
    PointCarte getSceneIJCoord(double x,double y){
            int i=(int)(y/TILE_SIZE);
            int j=(int)(x/TILE_SIZE);
            return new PointCarte(i, j);
    }
    Point2D getSceneGridCoord(double x,double y) {
            PointCarte p=getSceneIJCoord(x, y);
            double xgrid=(p.getJ()*TILE_SIZE)+(TILE_SIZE/2);//x1 monitor
            double ygrid=(p.getI()*TILE_SIZE)+(TILE_SIZE/2);//y1 monitor
            Point2D gridP=new Point2D(xgrid, ygrid);
        return gridP;
        
    }
    PointCarte getAbsoluteIJCoord(double x, double y){
            PointCarte sceneIJ=getSceneIJCoord(x, y);
            return new PointCarte(sceneIJ.getI()+posI,sceneIJ.getJ()+posJ);

    }
   static public Point2D getXYCarteAbsoluteCoord(int i,int j){
        double x=j*TILE_SIZE+TILE_SIZE/2;
        double y=i*TILE_SIZE+TILE_SIZE/2;
        return new Point2D(x, y);
    
    }
    Point2D getXYSceneAbsoluteCoord(double x,double y){
        PointCarte pAbs=  getAbsoluteIJCoord(x, y);
        double absX=pAbs.getJ()*TILE_SIZE+TILE_SIZE/2;
        double absY=pAbs.getI()*TILE_SIZE+TILE_SIZE/2;
        Point2D p=new Point2D(absX,absY);
        return p;
    
    }
    Point2D getOutSceneGridCoord(int i,int j){
        double transX=(relativeJ(j)*TILE_SIZE)+(TILE_SIZE/2);
        double transY=(relativeI(i)*TILE_SIZE)+(TILE_SIZE/2);
        return new Point2D(transX, transY);
    
    }
    void drawRangeDisplayLine(int i ,int j,double x,double y,Color c){
        Point2D outXY=getOutSceneGridCoord(i, j);
        Point2D gridXY=getSceneGridCoord(x, y);
        GraphicsContext gc =activeCanvas().getGraphicsContext2D();
        gc.setStroke(c);
        gc.setLineJoin(StrokeLineJoin.BEVEL);
    //    gc.setLineDashes(4,4);
        gc.setLineCap(StrokeLineCap.ROUND);        
        gc.strokeLine(outXY.getX(), outXY.getY(), gridXY.getX(), gridXY.getY());
    }
    
    double displayGraficRangeHelper(double x,double y,Color c){
        
        int lasti=mapLastI();
        int lastj=mapLastJ();
        Point2D absXY1=getXYSceneAbsoluteCoord(x, y);
        Point2D absXY2=getXYCarteAbsoluteCoord(lasti, lastj);
        
        double dpixel= absXY1.distance(absXY2.getX(), absXY2.getY());
        //double dpixel= p0.distance(xgrid+(TILE_SIZE/2), ygrid+(TILE_SIZE/2));
        drawRangeDisplayLine(lasti, lastj, x, y,c);
        return dpixel;
    
    }
    double angleDisplayRange(double x,double y){
        Point2D  gridXY1=getXYCarteAbsoluteCoord(mapLastI(), mapLastJ());
        Point2D gridXY2=getXYSceneAbsoluteCoord(x, y);
        double angle=FXCarte.angleRotation(gridXY1.getX(),gridXY1.getY(), gridXY2.getX(),gridXY2.getY());
        System.out.println(" angle "+angle);
        return angle;
    }
    
    public synchronized void displayFeuRangeAction(double mousex, double mousey) {

        try{
            
            fxIMHelper.getFXSoldatSelectionee().feuFrame();
            Soldat s=fxIMHelper.getSeletctionee();
            double dpixel=displayGraficRangeHelper(mousex, mousey,Color.RED);

            double inch=(dpixel*0.02);

            FXSoldat sfx=  fxIMHelper.getFXSoldatSelectionee();
            double angle=angleDisplayRange(mousex, mousey);
            if(!s.isImmobilize()) sfx.setFXSoldatOrientation(angle);



            if(s.getArmeUtilise()==null || s.isFeuArmePaPorte(inch) || 
                   ! s.isTempDisponiblePour(ActionType.FEU)){            
                fxIMHelper.removeDisplayRange();
                fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
                fxIMHelper.setCommanNotvalid(true);

                if(s.getArmeUtilise()==null) fxpl.sendMessageToPlayer("Arme tombe.");
                else if(s.isTempDisponiblePour(ActionType.FEU)) fxpl.sendMessageToPlayer("Temp fini.");            
                else fxpl.sendMessageToPlayer("Être hors de portée.");

            }else{
                fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_CROSSHAIR);
                fxIMHelper.setCommanNotvalid(false);
                           


            }

            System.out.println("distance"+(inch*1.8280)+" metri o inches:"+inch+" pixel"+dpixel );
            visualizeRangePointers(mousex,mousey);        
        }catch(ModeDeFeuException|TomberArmeException ex){
            throw new RuntimeException(ex);
        }
    }    


    public void displayRangeAction(double mousex,double mousey,ActionType type){
          
        double range=displayGraficRangeHelper(mousex, mousey, Color.WHITE);
        if(range>=(2*TILE_SIZE) ) {
            fxIMHelper.removeDisplayRange();
            fxIMHelper.setCommanNotvalid(true);
            fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
        }
        else {
            fxIMHelper.removeDisplayRange();
            if(type==ActionType.BANDAGE) 
                fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSO_HELPER_BANDGAE);
            else if(type==ActionType.ARME_RECHARGE) 
                fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSO_HELPER_LOADMAG);
            
            fxIMHelper.setCommanNotvalid(false);
            
        }
       // System.out.println(" range="+range);
        visualizeRangePointers(mousex, mousey);
    


    }    
    public void confirmAction(AbstractMenuItemButton item,double mousex,double mousey){
        try{
            
            PointCarte p=getAbsoluteIJCoord(mousex, mousey);
            Soldat s2=fxIMHelper.getSeletctionee();
            if(fxIMHelper.isCommanNotvalid() || 
                    s2==null ){
              fxIMHelper.removeDisplayRange(); 
              fxIMHelper.setCommanNotvalid(true);
              fxpl.sendMessageToPlayer("Action pa possible,", Color.TOMATO);
     
            }else if(!fxIMHelper.isCommanNotvalid()) {
                    BaseAction act= item.buildMenuItemAction();
                    
                    fxIMHelper.setAct(act);
                    fxIMHelper.setArrivalCarteCoord(p.getI(), p.getJ());
                    fxIMHelper.builtAction(item.getActionType());
                    fxIMHelper.addActionToSoldat();
                    visualizeBarSoldatAction();
                    fxpl.imprimerFXPLInfo(s2);
                    fxIMHelper.removeDisplayRange();
                    System.out.println("action valid:"+act);
                    fxpl.sendMessageToPlayer(s2.toStringSimple()+" :"+ act.toString());
                
            
            }
            fxIMHelper.setActionSeletione(false);
            setOnMouseMoved(null);
            setOnMouseClicked(null);
                        
            reMountFXCarteMenuItemsAndScroll();
            
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    
    }
    public  void refreshCarte(){
        if(this.switchsCanvas){
             
                buildCarteOnly(c1);

        }
        else {
            
                buildCarteOnly(c2);

        }
    }
    
    private void buildCarteOnly(Canvas canv) {
        String a = "";
        canv.getGraphicsContext2D().setFill(Color.rgb(64, 128, 0, 1));
        canv.getGraphicsContext2D().fillRect(0, 0, PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);

        int k = 0;
        for (int i0 = 0; i0 < AREA_SCROLL_I_H; i0++) {
            for (int j0 = 0; j0 < AREA_SCROLL_J_W; j0++) {
                int j = j0 + posJ;
                int i = i0 + posI;
                Terrain tile = carte.getPointCarte(i, j);
                double x0 = j0 * TILE_SIZE;
                double y0 = i0 * TILE_SIZE;
                if (tile != null) {
                    canv.getGraphicsContext2D().drawImage(tile.getImg(), x0, y0);
                }
                canv.getGraphicsContext2D().setFill(Color.BLACK);
                //canv.getGraphicsContext2D()
                if(debug)canv.getGraphicsContext2D().fillText(i + "," + j, x0 + 10, y0 + 25);
                // canv.getGraphicsContext2D().strokeRect(x0, y0, PointCarte.TILE_SIZE, PointCarte.TILE_SIZE);
                //if(ob!=null)System.out.println(" i,j"+(m+posX)+","+(n+posY));
                //if(ob!=null)
            }
        }
    }    
    private void buildCarteScroll(Canvas canv) {
        String a = "";
        canv.getGraphicsContext2D().setFill(Color.rgb(64, 128, 0, 1));
        canv.getGraphicsContext2D().fillRect(0, 0, PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        //System.out.println("scroolw,scrollh=" + AREA_SCROLL_J_W + "," + AREA_SCROLL_I_H);

       // System.out.println("------------------SCROLL PRINT----------------------------------");
        for (int i0 = 0; i0 < AREA_SCROLL_I_H; i0++) {
            for (int j0 = 0; j0 < AREA_SCROLL_J_W; j0++) {
                int j = j0 + posJ;
                int i = i0 + posI;
                Terrain tile = carte.getPointCarte(i, j);
                double x0 = j0 * TILE_SIZE;
                double y0 = i0 * TILE_SIZE;
                if (tile != null) {
                    canv.getGraphicsContext2D().drawImage(tile.getImg(), x0, y0);
                }
                canv.getGraphicsContext2D().setFill(Color.BLACK);
                //canv.getGraphicsContext2D()
                if(debug)canv.getGraphicsContext2D().fillText(i + "," + j, x0 + 10, y0 + 25);
                // canv.getGraphicsContext2D().strokeRect(x0, y0, PointCarte.TILE_SIZE, PointCarte.TILE_SIZE);
                //System.out.println("scrollI,scrollJ"+i+","+j);

                 enableAllFXSoldat(tile, x0, y0);
                //if(ob!=null)System.out.println(" i,j"+(m+posX)+","+(n+posY));
                //if(ob!=null)
            }
        }
//System.out.println("------------------SCROLL PRINT----------------------------------");
    }
    void enableAllFXSoldat(Terrain tile,double x0,double y0){

        GeneriquePiece[] pieces= tile.getPieces();
        for (int i = 0; i < pieces.length; i++) {
            GeneriquePiece piece = pieces[i];
            if(piece!=null && 
            piece.getPieceType()==Piece.ActeurType.SOLDAT  ){
            Soldat s=(Soldat)piece;    
            if (s.isHostile()) 
                fxequipeHost[s.getEquipeArrayPlace()].enableSoldatoInView(i);
            else if (s.isUS()) 
                fxequipeUS[s.getEquipeArrayPlace()].enableSoldatoInView(i);            
            
            }

        }


        
        }

        
    
    
    


    private void visibilityOFFAllFXSoldatsPatEnView() {
 
        for (FXSoldat fxequipeUS1 : fxequipeUS) {
            fxequipeUS1.setVisible(false);
        }
        for (FXSoldat fxequipeHost1 : fxequipeHost) {
            fxequipeHost1.setVisible(false);
        }
    }    
    

   
   public void refreshCarteAllFXSoldatViewPosition() {
 
            refreshCarteHostFXSoldatPosition();
            refreshCarteUSFXSoldatPosition();
    }   


   private void refreshCarteUSFXSoldatPosition(){
        for (FXSoldat fxequipeUS1 : fxequipeUS) {
            refreshCarteFXSoldatPosition(fxequipeUS1);
        }
   
   }
   private void refreshCarteHostFXSoldatPosition(){
        for (FXSoldat fxequipeHost1 : fxequipeHost) {
            refreshCarteFXSoldatPosition(fxequipeHost1);
        }
   }     
    

private void refreshCarteFXSoldatPosition(FXSoldat sfx){
        if (!sfx.estFXSoldatView()) {
            //System.out.println("e' invisibile booooooooooh"+sfx.getSoldat().getNom());
            sfx.setVisible(false);
        } else {
            sfx.setVisible(true);
            //Soldat s=sfx.getSoldat();
            
            sfx.enableSoldatoInView(0);
        }    
    }
    
    @Deprecated 
    public Point2D getSceneCoordScroll(Piece s,int  i,int j){
      //  System.out.println(" get coord "+i+","+j+" posI,posj="+posI+","+posJ);
        PointCarte cp=carte.getPointCarte(i, j);
        int scrollI=i-posI,scrollJ=j-posJ;
        double x0=(scrollJ*FXCarte.TILE_SIZE);
        double y0=(scrollI*FXCarte.TILE_SIZE); 
        //TODO ripensare la sovrapposizione dei soldati al limmite fare la scelta sul click...

        //y0=esteticCorrectionY0(scrollI,y0);
        //x0=esteticCorrectionX0(scrollJ,x0);
        Point2D p=new Point2D(x0, y0);
        return p;
    }    
 

    


    


    
    private void removeMenuItemsMenuOnFXUSEquipe(){
            this.jUS.removeMenuItemsOnFXEquipe();
    
    }
        private void removeMenuItemsonFXHostileEquipe(){
            jHOST.removeMenuItemsOnFXEquipe();
    
    }
        
   private void mountMenuItemsOnFXUSEquipe(){
    jUS.mountMenuItemOnFXEquipe();   
   }     
   private void mountMenuItemsOnFXHostileEquipe(){
       jHOST.mountMenuItemOnFXEquipe();
   }
    public synchronized boolean scrollCanvasBorder(double x, double y) {

         boolean updateScroll=false;
        if (y < (PIXEL_SCROLL_AREA_H + TOP_H) && y > ((PIXEL_SCROLL_AREA_H - 10) + TOP_H)) {
            //rootGroup.getChildren().add(arrow);
            setCursor(ImageChargeur.getInstance().getArrowCRDown());
            //arrow.setVisible(true);
            //arrow.setRotate(90);
            //arrow.setX(PIXEL_SCROLL_AREA_W / 2);
            //arrow.setY(PIXEL_SCROLL_AREA_H - 100);

           updateScroll= scrollDown();
        } else if (y > (TOP_H) && y < (10 + TOP_H)) {
            setCursor(ImageChargeur.getInstance().getArrowCRUp());
//            arrow.setVisible(true);
//            arrow.setRotate(-90);
//            arrow.setX(PIXEL_SCROLL_AREA_W / 2);
//            arrow.setY(0);
          
            updateScroll=scrollUp();
        } else if (x > (PIXEL_SCROLL_AREA_W - 10) && x < (PIXEL_SCROLL_AREA_W)) {
            setCursor(ImageChargeur.getInstance().getArrowCRRight());
//            arrow.setVisible(true);
//            arrow.setX(PIXEL_SCROLL_AREA_W  - 100);
//            arrow.setY(PIXEL_SCROLL_AREA_H / 2);
//            arrow.setRotate(0);
          
            updateScroll= scrollRight();
        } else if (x < 10) {
            setCursor(ImageChargeur.getInstance().getArrowCRLeft());
//            arrow.setVisible(true);
//            arrow.setRotate(-180);
//            arrow.setX(0);
//            arrow.setY(PIXEL_SCROLL_AREA_H / 2);
          
           updateScroll= scrollLeft();
        } else {
            this.setCursor(current); 
//            arrow.setVisible(false);
        }
        return updateScroll;
    }
    public synchronized boolean scrollCanvas(double deltax, double deltay) {

         boolean updateScroll=false;
        if (deltay<0) {
            //rootGroup.getChildren().add(arrow);
            setCursor(ImageChargeur.getInstance().getArrowCRDown());
            //arrow.setVisible(true);
            //arrow.setRotate(90);
            //arrow.setX(PIXEL_SCROLL_AREA_W / 2);
            //arrow.setY(PIXEL_SCROLL_AREA_H - 100);

           updateScroll= scrollDown();
        } else if (deltay>0) {
            setCursor(ImageChargeur.getInstance().getArrowCRUp());
//            arrow.setVisible(true);
//            arrow.setRotate(-90);
//            arrow.setX(PIXEL_SCROLL_AREA_W / 2);
//            arrow.setY(0);
          
            updateScroll=scrollUp();
        } else if (deltax<0) {
            setCursor(ImageChargeur.getInstance().getArrowCRRight());
//            arrow.setVisible(true);
//            arrow.setX(PIXEL_SCROLL_AREA_W  - 100);
//            arrow.setY(PIXEL_SCROLL_AREA_H / 2);
//            arrow.setRotate(0);
          
            updateScroll= scrollRight();
        } else if (deltax>0) {
            setCursor(ImageChargeur.getInstance().getArrowCRLeft());
//            arrow.setVisible(true);
//            arrow.setRotate(-180);
//            arrow.setX(0);
//            arrow.setY(PIXEL_SCROLL_AREA_H / 2);
          
           updateScroll= scrollLeft();
        } 
        return updateScroll;
    }

     private boolean scrollDown() {
         boolean updateScroll=false;
        if ((posI + 1 + AREA_SCROLL_I_H) <= mapH) {
    
            //arrow.setFrame(0);
            posI++;
            updateScroll=true;
            visibilityOFFAllFXSoldatsPatEnView();
            if (switchsCanvas) {
                buildCarteScroll(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarteScroll(c1);
                c1.setVisible(true);
                c2.setVisible(false);
            }
            switchsCanvas = !switchsCanvas;
        } else {
            setCursor(ImageChargeur.getInstance().getForbidden());
            //arrow.setFrame(1);
            posI = posI;
        }
        return updateScroll;
    }

    private boolean scrollUp() {
           boolean updateScroll=false;
        if ((posI - 1) >= 0) {

            updateScroll=true;
            //arrow.setFrame(0);
            posI--;
            visibilityOFFAllFXSoldatsPatEnView();
            if (switchsCanvas) {
                buildCarteScroll(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarteScroll(c1);
                c1.setVisible(true);
                c2.setVisible(false);
            }
            switchsCanvas = !switchsCanvas;
        } else {
           setCursor(ImageChargeur.getInstance().getForbidden());
           // arrow.setFrame(1);
        }
        return updateScroll;
    }

    private boolean scrollRight() {
          boolean updateScroll=false;
        if ((posJ + 1 + AREA_SCROLL_J_W) <= mapW) {
               
            //arrow.setFrame(0);
            updateScroll=true;
            posJ++;
            visibilityOFFAllFXSoldatsPatEnView();
            if (switchsCanvas) {
                buildCarteScroll(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarteScroll(c1);
                c1.setVisible(true);
                c2.setVisible(false);
            }
            switchsCanvas = !switchsCanvas;
        } else {
            setCursor(ImageChargeur.getInstance().getForbidden());
            //arrow.setFrame(1);
            posJ = posJ;
        }
        return updateScroll;
    }

    private boolean scrollLeft() {
        boolean updateScroll=false;
        if ((posJ - 1) >= 0) {
            //this.setCursor(arrowCRLeft);  
            //arrow.setFrame(0);
            updateScroll=true;
            posJ--;
            visibilityOFFAllFXSoldatsPatEnView();
            if (switchsCanvas) {
                buildCarteScroll(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarteScroll(c1);
                c1.setVisible(true);
                c2.setVisible(false);
            }
            switchsCanvas = !switchsCanvas;
        } else {
           setCursor(ImageChargeur.getInstance().getForbidden());
           // arrow.setFrame(1);
        }
        return updateScroll;
    }

    public int getPosI() {
        return posI;
    }

    public int getPosJ() {
        return posJ;
    }
   protected void visualizeBarSoldatAction(){
        Soldat s=fxIMHelper.getSeletctionee();
        fxpl.suprimerActionVisualization();
        int acN=s.actionSize();
        for(int k=0; k<acN;k++){
            BaseAction ac=s.nextAction(k);
            fxpl.visualizeActionBar(ac,k);

        }

    }
    

   protected Point2D getMenuCoord(double sx,double sy,int i,int j){
   
            FXSoldat s=fxIMHelper.getFXSoldatSelectionee();
        

            int relativeI = i - posI;
            int relativeJ = j - posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + 
                    relativeI + "," + relativeJ);

            // Point2D d2s = s.localToScene(s.getX(), s.getY());

            double spritecentery = sy + (TILE_SIZE / 2);
            double spritecenterx = sx + (TILE_SIZE / 2);
            if (relativeI <= 3) {
                spritecentery = spritecentery + (AbstractMenuItemButton.MENU_H * 2);
            }
            if (relativeJ <= 3) {
                spritecenterx = spritecenterx + (AbstractMenuItemButton.MENU_W * 2);
            }
            if (relativeI >= (AREA_SCROLL_I_H - 3)) {
                spritecentery = spritecentery - (AbstractMenuItemButton.MENU_H * 2);
            }
            if (relativeJ >= (AREA_SCROLL_J_W - 3)) {
                spritecenterx = spritecenterx - (AbstractMenuItemButton.MENU_W  * 2);
            }      
            Point2D spritecenter2D=new Point2D(spritecenterx, spritecentery);
         
            return spritecenter2D;
            
   } 
   
   
   


    public  void visualizeMiniIconAction(BaseAction act){
        removeMiniIcon();
        if(act.getType()==ActionType.MARCHE ||
                act.getType()==ActionType.COURS){
            actionIcon =new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE, "actionWalk.png", this);
        }if(act.getType()==ActionType.FEU){
            actionIcon =new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE, "crossHairSign.png", this);        
        }else if(act.getType()==ActionType.ARME_RECHARGE  ){
            actionIcon=new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE, "cursorHelperLodaMag.png", this);  
        }else if(act.getType()==ActionType.BANDAGE){
            actionIcon=new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                    FXCarte.TILE_SIZE, "cursorHelperBandage.png", this);
        }
        actionIcon.create();
        //TODO icona da sistemare sullo schermo se ce la visuale ......scrollare anche queste ...
        //TODO se clicckate la action bar  si centra sull'azione ordinata......
        centerScrollArea(act.getI1(), act.getJ1());
        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        Point2D p=getSceneCoordForRefreshCarte(act.getI1(), act.getJ1());
        actionIcon.setTranslateX(p.getX());
        actionIcon.setTranslateY(p.getY());
        actionIcon.setVisible(true);
        actionIcon.toFront();
        rootGroup.getChildren().add(actionIcon);
        
    
    }
    
    public void removeMiniIcon(){
    rootGroup.getChildren().remove(actionIcon);
    }
    

        
    public Point2D getSceneCoordForRefreshCarte(int  i,int j){
        
        //System.out.println(" get coord "+i+","+j+" posI,posj="+posI+","+posJ);
        double x0=((j-posJ)*FXCarte.TILE_SIZE);
        double y0=((i-posI)*FXCarte.TILE_SIZE); 
        Point2D p=new Point2D(x0, y0);
        return p;
    }  
  
    
   
    
  
    

    
    

    



  protected void deselectionneAllSoldats() {
      if(fxIMHelper!=null){
      FXSoldat s = fxIMHelper.getFXSoldatSelectionee();

      for (int k = 0; k < fxequipeHost.length; k++) {

              fxequipeHost[k].defaultFrame();
              fxequipeHost[k].deselectioneFXSoldat();

      }
      for (int k = 0; k < fxequipeUS.length; k++) {

              fxequipeUS[k].defaultFrame();
              fxequipeUS[k].deselectioneFXSoldat();
          }

 
      }
  }
 
 


     int relativeI(int reali) {
        int relativei = -1;
        int posi = posI;
        if (reali < posi) {
            relativei = -(posi - reali);
        } else if (reali >= (posi)) {
            relativei = (reali - posi);
        }
        return relativei;

    }

     int relativeJ(int realj) {

        int posj =posJ;
        int relativej = -1;

        if (realj < posj) {
            relativej = -(posj - realj);
        } else if (realj >= (posj)) {
            relativej = (realj - posj);
        }
        return relativej;

    }
    
    
    PointCarte convertSceneCoord(double x,double y){
        int scrollMousej = (int) (x / FXCarte.TILE_SIZE);
        int scrollMousei = (int) (y / FXCarte.TILE_SIZE);
        int j = (scrollMousej + posJ);
        int i = (scrollMousei + posI);
        PointCarte p=new PointCarte(i, j);
        return p;
    
    }

    @Override
    public void refreshGraficCarte() {
        double progress=(double)(mj.td)/10d;
      //  fxpl.bar.setProgress(progress);
        System.out.println("progress------>"+progress);
        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        
    }
  
    public void confirmFEUAction(AbstractMenuItemButton item,double x,double y)throws Exception{

        PointCarte p=getAbsoluteIJCoord(x, y);
        Terrain t=this.carte.getPointCarte(p);
       if (t.getPiece()==null || (t.getPiece()!=null  && !t.getPiece().isTargetable()))
             fxIMHelper.setCommanNotvalid(true);
           
        if(!fxIMHelper.isCommanNotvalid()){

            BaseAction act=item.buildMenuItemAction();

            double angle=angleDisplayRange(x, y);
            
            fxIMHelper.buildFeuAction(act, p.getI(), p.getJ(),angle);
            fxIMHelper.addActionToSoldat();
            Soldat s=fxIMHelper.getSeletctionee();
            fxpl.imprimerFXPLInfo(fxIMHelper.getSeletctionee());
            fxpl.sendMessageToPlayer(s.toStringSimple()+" "+act.toString());
             
       
            visualizeBarSoldatAction();
            
        }else{
           
            visualizeRangePointers(x,y); 
            BaseAction act=  item.buildMenuItemAction();
            addHelperInstance(act);            
            fxpl.sendMessageToPlayer("Objective pa valide");
            ClassLoader classLoader = getClass().getClassLoader();
            URL url=classLoader.getResource("plich.aif");
            AudioClip audio=new AudioClip(url.toString());
            System.out.println(url.toString());
            audio.play();
            
            
        }
        System.out.println("cursor-->"+fxIMHelper.getRangeCursorHelper());
        fxIMHelper.removeDisplayRange();
        
        fxIMHelper.setActionSeletione(false);
        setOnMouseMoved(null);
        
        reMountFXCarteMenuItemsAndScroll();
        
    }
    
    
public boolean isEnemy(Soldat s1,Soldat s2){
    return !s1.isFriend(s2);

}
    
}
