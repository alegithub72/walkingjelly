/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.MoteurDeJoeur;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.jeurs.FXAIJoueur;
import jeu.patrouille.fx.jeurs.FXMouseJeurs;
import jeu.patrouille.fx.menu.DisableMenuItems;
import jeu.patrouille.fx.menu.FeuItem;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.OpFeuItem;
import jeu.patrouille.fx.menu.RunItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuConfirmActionEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuRangeDisplayHandler;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatClickedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsFXCarteEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
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

    
    
    MenuItem[] actionMenu;


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
        actionMenu = new MenuItem[20];
        
        mapW = carte.getMapW();
        mapH = carte.getMapH();
        c1 = new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        rootGroup.getChildren().add(c1);
        c2 = new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        rootGroup.getChildren().add(c2);

                    
        //ImageView test = new ImageView(new Image("menuItem.png"));

        
        

       
              
        
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
        this.setOnMouseMoved(new ScrollEventHandler(this));
        this.setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        animOn=false;
    }
    
    public void playTurn(){
               
        setOnMouseMoved(null);
        setOnMouseClicked(null);
        removeMenuItemsMenuOnFXUSEquipe();
        removeMenuItemsonFXHostileEquipe();
        mj.debutRond();
        
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
    public  void play(BaseAction b) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("---------------FXCARTE (NEW THREAD " + Thread.currentThread().getName() + ")----------CREATE ANIM  -----INIZIO-----"
                + "-----------------------------------------><------");

        Soldat sl
                = b.getProtagoniste().getBoss().findSquadLeader();

        // System.out.println("squad leader "+sl);
        //System.out.println("posi=" + posI + "posj=" + posJ + " leader --->" + sl);
        // suprimmerSoldatsNotEnView();

        int centerI = sl.getI(), centerJ = sl.getJ();

        if (isNeedeCenterScrollAreaUpdate(centerI, centerJ)||
               fxIMHelper.isLastUSVisualizationTurn(sl) ){
            centerScrollArea(centerI, centerJ);

        }

        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        initFXHelperInstance(null);
        if (b.isProtagonisteTypeSoldat()
                && b.isProtagonisteHostile()) 
            playHostile(b);
        
        else if (b.isProtagonisteTypeSoldat() 
                && !b.isProtagonisteHostile()) 
            
            playUSSoldat(b);
        
        else {
            
            setAnimOn(false);
            throw new RuntimeException("NOT US OR HOSTILE SOLDAT????");

        }

        System.out.println("---------------FXCARTE (NEW THREAD " + Thread.currentThread().getName() + ")----------CREATE ANIM  ----FINE------"
                + "-----------------------------------------><------");

    }
    
private boolean isNeedeCenterScrollAreaUpdate(int centerI,int centerJ){
    boolean b = false;
    return (((centerI - posI) < 3)
            || ((FXCarte.AREA_SCROLL_I_H - (centerI - posI)) < 3)
            || ((centerJ - posJ) < 3)
            || ((FXCarte.AREA_SCROLL_J_W - (centerJ - posJ)) < 3));


}
private void centerScrollArea(int i,int j){
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
    


    private void playHostile(BaseAction b){
        FXHostile sfx = (FXHostile) findFXHostile((Soldat) b.getProtagoniste());
        fxIMHelper.setFXSeletctionee(sfx);
        if (sfx.isVisible() &&  b.getType() == ActionType.MARCHE) {
            sfx.playMarche((MarcheAction) b);
        } else {
            System.out.println("$$$$NON animation$$$$");
            setAnimOn(false);
        }

   
    
    }
    
    private void playUSSoldat(BaseAction b){

        FXSoldat sfx = findFXUSSoldat((Soldat) b.getProtagoniste());
        fxIMHelper.setFXSeletctionee(sfx);
        if (sfx.isVisible() && b.getType() == ActionType.MARCHE) {
            sfx.playMarche((MarcheAction) b);
        } else {
            System.out.println("$$$$NON animation$$$$");
            setAnimOn(false);
        }
  
    }


    

    
    private FXSoldat findFXUSSoldat(Soldat s){
        FXSoldat searchedFX=null;
        for(FXSoldat sfx:  this.fxequipeUS){
            if(sfx.getSoldat()==s)searchedFX=sfx;
        }
        return searchedFX;
    }
    private FXSoldat findFXHostile(Soldat s){
        
        FXSoldat searchedFX=null;
        for(FXSoldat sfx:  this.fxequipeHost){
            if(sfx.getSoldat()==s)searchedFX=sfx;
        }
        return searchedFX;
    }    
    
    synchronized public void closeFXCarteMenuItems() {
        
       devisualizeMenuItems();

    }
    protected void devisualizeMenuItems() {
        for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
                actionMenu[k].setVisible(false);
            }
        }

    }
    
    private void addHelperInstance(BaseAction act){

            fxIMHelper.setAct(act);




        
    }    
    

    public  boolean isFXHelperActionSeletiones() {
        return fxIMHelper.isActionSelectione();
    }

    

    
    synchronized public void annulleCommand() {
        removeDisplayRange();
        fxIMHelper.setCommanNotvalid(true);
        setOnMouseMoved(null);
        setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setOnMouseMoved(new ScrollEventHandler(this));
        setCursor(Cursor.DEFAULT);
        fxpl.sendMessageToPlayer("Action effacer");

    }

   
    
 synchronized public void confirmMarcheActionCommand(MenuItem item, double mousex, double mousey)throws Exception {

        double x = mousex;
        double y = mousey;
        double tmpI = (y / FXCarte.TILE_SIZE);
        double tmpJ = (x / FXCarte.TILE_SIZE);
        System.out.println(x + "," + y);
        int i1 = ((int) tmpI) + posI;
        int j1 = ((int) tmpJ) + posJ;
        System.out.println(fxIMHelper.getSeletctionee()+" walk here i1=" + i1 + " j1=" + j1);

        removeDisplayRange();

        setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setCursor(Cursor.DEFAULT);
        if (!fxIMHelper.isCommanNotvalid()) {
            
            
            fxIMHelper.setArrivalCarteCoord(i1, j1);
            fxIMHelper.addActionToSoldat();

            imprimerFXHelperSoldatProfile();
            fxpl.visualizeActionBarActual();
            closeFXCarteMenuItems();
            //buildFXCarteMenuItems();
            //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
            fxpl.sendMessageToPlayer(fxIMHelper.toString());
            //resetFXCarteHelperAction();
            BaseAction act=  item.buildMenuItemAction();
            addHelperInstance(act);

        } else {
       
            removeDisplayRange();
            BaseAction act=  item.buildMenuItemAction();
            addHelperInstance(act);
            fxpl.sendMessageToPlayer("Action non valide");
        }
            setOnMouseMoved(new ScrollEventHandler(this));
            fxIMHelper.setActionSeletione(false);
    }





    
    @Override
    public void reMountFXCarteMenuItemsAndScroll(){
        this.setOnMouseMoved(new ScrollEventHandler(this));
        this.setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
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
    
    synchronized public void clickOnButtonItems(MenuItem item) {

        item.setFrame(1);
        BaseAction act = item.buildMenuItemAction();

        if (act.getType() == ActionType.MARCHE) {
            
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler( item, this));
            setFXCarteCursor(Cursor.HAND);
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this,item.getActionType()));
            fxpl.sendMessageToPlayer("Choisir un emplacement");
            closeFXCarteMenuItems();

        } else if (act.getType() == ActionType.FEU) {
            addHelperInstance(act);
            fxIMHelper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmActionEventHandler(item,this));
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this, item.getActionType()));
            setFXCarteCursor(Cursor.HAND);
            
            fxpl.sendMessageToPlayer("Choisir an Ojective");
            closeFXCarteMenuItems();
        }

    }
    

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
    
    
    
    


   
   private void initFXHelperInstance(FXSoldat sfx){
       if (sfx != null) {
           Soldat s=sfx.getSoldat();
           
           if (s.getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST) {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           } else {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_US);
           }
           mj.getActiveJeur().setPieceSelectionee(s);
       }
       fxIMHelper= new FXItemsPointerHelper(sfx, carte);
       
    }   
   

   
 

    


    public void addSprite(Sprite s){
        rootGroup.getChildren().add(s);
    
    }
    private Group getRootGroup() {
        return rootGroup;
    }

    private void removeDisplayRange(){
    
        if(rootGroup.getChildren().contains(fxIMHelper.getDisplayRange()))
            rootGroup.getChildren().remove(fxIMHelper.getDisplayRange());
            
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

        
       // visualizeFXCarteDisplayRange(mousex, mousey);

        int mapLastActI = fxIMHelper.mapLastI();
        int mapLastActJ = fxIMHelper.mapLastJ();
        double mapLastActy = mapLastActI * FXCarte.TILE_SIZE;
        double mapLastActx = mapLastActJ * FXCarte.TILE_SIZE;
        System.out.println(" (mapLastActI,mapLastActJ)=" + mapLastActI + "," + mapLastActJ);
        int posi = posI;
        int posj = posJ;
        
        System.out.println(" (posi,posj)=" + posI + "," + posJ);

        //TODO usare scrollmousej, and scrollMouei per verificare la validita del percorso
        int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
        int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);
        
        fxIMHelper.setArrivalCarteCoord(scrollMousei+posI, scrollMousej+posJ);
       

        double mouseMapx = (scrollMousej + posj) * FXCarte.TILE_SIZE;
        double mouseMapy = (scrollMousei + posi) * FXCarte.TILE_SIZE;

        double circleX = Math.pow(mouseMapx - mapLastActx, 2);
        double circleY = Math.pow(mouseMapy - mapLastActy, 2);
        double r = Math.sqrt(circleX + circleY);

        double relativex = relativeJ(mapLastActJ) * FXCarte.TILE_SIZE + 25;
        double relativey = relativeI(mapLastActI) * FXCarte.TILE_SIZE + 25;
        System.out.println("relativex,relativey " + relativex + "," + relativey);
        GraphicsContext g=activeCanvas().getGraphicsContext2D();
        //g.setLineWidth(10);
        g.setStroke(Color.FLORALWHITE);
        
        g.setLineJoin(StrokeLineJoin.ROUND);
        
        g.strokeLine(
                relativex,
                relativey,
                (scrollMousej * FXCarte.TILE_SIZE) + 25,
                (scrollMousei * FXCarte.TILE_SIZE) + 25);
        
        double angle =FXCarte.angleRotation(
                relativex, 
                relativey, 
                (scrollMousej * FXCarte.TILE_SIZE) + 25, 
                (scrollMousei * FXCarte.TILE_SIZE) + 25);
        
        fxIMHelper.getFXSoldatSelectionee().setFXSoldatOrientation(angle);
       //TODO mettere una variabile per debug
        PointCarte obst=fxIMHelper.carteValiderRoute();
        if (fxIMHelper.isDistanceLessMarcheMax(r)
                && obst==null ) {
            fxIMHelper.setCommanNotvalid(false);
            setFXCarteCursor(Cursor.HAND);
            removeDisplayRange();
            resetCursorHelper();
            fxpl.sendMessageToPlayer("");
            visualizeRangePointers(mousex, mousey);
            
            System.out.println("raggio---->" + r);
            System.out.println("angle------------------------------------------>"+angle);
        } else {
            removeDisplayRange();
            fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
            fxIMHelper.setCommanNotvalid(true);
            if(obst!=null) fxpl.sendMessageToPlayer("Obstacole sur sentier: "+obst.getI()+","+obst.getJ());
            visualizeRangePointers(mousex, mousey);
            System.out.println("Horse de Rayon---->" + r);
        }
        
    }
    
    
    public static double angleRotation(double x0,double y0,double x1,double y1){
        Point2D pv=new Point2D(x0, y0);
        Point2D p0=new Point2D(0, y0);
        Point2D p1=new Point2D(x1, y1);
        if((y1-y0)<0 && (x1-x0)<0) 
            return pv.angle(p0, p1)-90;
        else if((y1-y0)<0 && (x1-x0)>0)
            return pv.angle(p0, p1)-90;        
        else  if((y1-y0)>0 && (x1-x0)>0 ) return (180-pv.angle(p0, p1))+90;
        else if((y1-y0)>0 && (x1-x0)<0) return -pv.angle(p0,p1)-90;
        else if((x1-x0)==0 && (y1-y0)<0) return pv.angle(p0,p1)-90;
        else if((x1-x0)==0 && (y1-y0)>0) return -pv.angle(p0,p1)-90;
        else if((y1-y0)==0 && (x1-x0)>0) return pv.angle(p0,p1)-90;
        else if((y1-y0)==0 && (x1-x0)<0)return pv.angle(p0,p1)-90;
        return pv.angle(p0,p1);
      
            
    }
    public synchronized void displayFeuRangeAction(double mousex, double mousey) {
        int i=(int)(mousey/FXCarte.TILE_SIZE);
        int j=(int)(mousex/FXCarte.TILE_SIZE);
        double xgrid=(j*FXCarte.TILE_SIZE);
        double ygrid=(i*FXCarte.TILE_SIZE);
        fxIMHelper.getFXSoldatSelectionee().setFrame(7);
        Soldat s=fxIMHelper.getSeletctionee();
        int lasti=mapLastI();
        int lastj=mapLastJ();
        double protx=(relativeJ(lastj)*TILE_SIZE)+(TILE_SIZE/2);
        double proty=(relativeI(lasti)*TILE_SIZE)+(TILE_SIZE/2);

        GraphicsContext gc =activeCanvas().getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.strokeLine(protx, proty, xgrid+(TILE_SIZE/2), ygrid+(TILE_SIZE/2));
        Point2D p0=new Point2D(protx, proty);
        double dpixel= p0.distance(xgrid+(TILE_SIZE/2), ygrid+(TILE_SIZE/2));
        double angle=FXCarte.angleRotation(protx, proty, xgrid+(TILE_SIZE/2), ygrid+(TILE_SIZE/2));
        fxIMHelper.getFXSoldatSelectionee().setFXSoldatOrientation(angle);
        
        double inch=(dpixel*0.02);
        System.out.println("distance"+(inch*1.8280)+" metri o inches:"+inch);
        if(s.isFeuArmePaPorte(inch)){            
            removeDisplayRange();
            fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
            fxIMHelper.setCommanNotvalid(true);

        }else{
            removeDisplayRange();
            fxIMHelper.setCommanNotvalid(false);
            fxIMHelper.setRangeCursorHelper(ImageChargeur.CURSOR_CROSSHAIR);            

          
        }
        visualizeRangePointers(mousex,mousey);        
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
        Piece piece = null;

        piece = tile.getPiece();

        //  System.out.println(tile+","+piece);
        if (piece != null
                && piece.isHostile()) {

           fxequipeHost[piece.getarrayN()].enableSoldatoInView(0);

        } else if (piece != null
                && piece.isUS()) {

            fxequipeUS[piece.getarrayN()].enableSoldatoInView(0);

        }
            
        int k = 1;
        for (Piece p : tile.getExtraPiece()) {
            if (p != null && p.isUS()) {
               fxequipeUS[p.getarrayN()].enableSoldatoInView(k);
            } else if (p != null && p.isHostile()) {
               fxequipeHost[p.getarrayN()].enableSoldatoInView(k);
            }
            k++;
                          
            
            }
            
            //System.out.println("here ="+x0+","+y0+" ");
        
    
    }
    


    private void visibilityOFFAllFXSoldatsPatEnView() {
 
        for (FXSoldat fxequipeUS1 : fxequipeUS) {
            fxequipeUS1.setVisible(false);
        }
        for (FXSoldat fxequipeHost1 : fxequipeHost) {
            fxequipeHost1.setVisible(false);
        }
    }    
    

   
   private void refreshCarteAllFXSoldatViewPosition() {
 
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
            sfx.setVisible(false);
        } else {

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
        if(cp.getPiece()!=s && cp.isInExtra(s)){
            x0=(scrollJ*FXCarte.TILE_SIZE)+((cp.extraPiecePostion(s)+1)*20);
            y0=(scrollI*FXCarte.TILE_SIZE)+((cp.extraPiecePostion(s)+1)*20);
        }
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

    public synchronized boolean scrollCanvas(double x, double y) {

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
   private void visualizeBarSoldatAction(){
        Soldat s=fxIMHelper.getSeletctionee();
        fxpl.suprimerActionVisualization();
        int acN=s.actionSize();
        for(int k=0; k<acN;k++){
            BaseAction ac=s.nextAction(k);
            fxpl.visualizeActionBar(ac,k);

        }

    }
    

   private Point2D getMenuCoord(double sx,double sy,int i,int j){
   
            FXSoldat s=fxIMHelper.getFXSoldatSelectionee();
        

            int relativeI = i - posI;
            int relativeJ = j - posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + 
                    relativeI + "," + relativeJ);

            // Point2D d2s = s.localToScene(s.getX(), s.getY());

            double spritecentery = sy + (TILE_SIZE / 2);
            double spritecenterx = sx + (TILE_SIZE / 2);
            if (relativeI <= 3) {
                spritecentery = spritecentery + (MenuItem.MENU_H * 2);
            }
            if (relativeJ <= 3) {
                spritecenterx = spritecenterx + (MenuItem.MENU_W * 2);
            }
            if (relativeI >= (AREA_SCROLL_I_H - 3)) {
                spritecentery = spritecentery - (MenuItem.MENU_H * 2);
            }
            if (relativeJ >= (AREA_SCROLL_J_W - 3)) {
                spritecenterx = spritecenterx - (MenuItem.MENU_W  * 2);
            }      
            Point2D spritecenter2D=new Point2D(spritecenterx, spritecentery);
         
            return spritecenter2D;
            
   } 
   
   
   
    protected synchronized void buildMenuItems(double sx,double sy,int i,int j) throws Exception{
            FXSoldat sfx=fxIMHelper.getFXSoldatSelectionee();

            Point2D spritecoord2D=getMenuCoord(sx,sy,i,
                    j);

            double r= (2 * Math.PI) / 8;
            sfx.selectioneFXSoldat();
            Soldat s=sfx.getSoldat();
            if (s.isPossileDesplacer() ) {
            
          
            
            buildMarcheMenuItem(sfx,spritecoord2D.getX(),spritecoord2D.getY());
            buildCoreurMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildFeuMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildOpFeuMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
           
            //rootGroup.getChildren().add(l);

            visualizeBarSoldatAction();
            fxpl.sendMessageToPlayer("Choisir une action");

        }else {
            defaceMenuItems();
            buildDisableMenu(sfx);
            String mes="";
            if(s.getStatu()!=Lesion.Statu.NORMAL) mes=s.getStatu().name();
            fxpl.sendMessageToPlayer("Il ne peut pas agir "+mes);
        }
    
    }
    
    public  void visualizeMiniIconAction(BaseAction act){
        removeMiniIcon();
        if(act.getType()==ActionType.MARCHE){
            actionIcon =new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE, "actionWalk.png", this);
        }if(act.getType()==ActionType.FEU){
            actionIcon =new FXPatrouilleSprite(FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE, "crossHairSign.png", this);        
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
    
    
    private void openMenuItems(){
     for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
               actionMenu[k].setVisible(true);
               actionMenu[k].release();
            }
        }
    
    }
        
    public Point2D getSceneCoordForRefreshCarte(int  i,int j){
        
        //System.out.println(" get coord "+i+","+j+" posI,posj="+posI+","+posJ);
        double x0=((j-posJ)*FXCarte.TILE_SIZE);
        double y0=((i-posI)*FXCarte.TILE_SIZE); 
        Point2D p=new Point2D(x0, y0);
        return p;
    }  
    private void imprimerFXHelperSoldatProfile() {
        Soldat  s = fxIMHelper.getSeletctionee();
 
        fxpl.imprimerFXPLInfo(s);
    }   
    
    synchronized public void openSoldatMenuItems(FXSoldat sfx) {
        try{
            
            defaceMenuItems();
            deselectionneAllSoldats();  
            initFXHelperInstance(sfx);
            imprimerFXHelperSoldatProfile();
            double sx=sfx.getTranslateX();
            double sy=sfx.getTranslateY();
            int i=sfx.getSoldat().getI();
            int j=sfx.getSoldat().getJ();
            buildMenuItems(sx,sy,i,j);
            fxpl.buildFXSoldatEquipement(sfx.getSoldat());
            fxpl.sendMessageToPlayer("Choisir une action from ("+i+","+j+")");
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }    
    
    synchronized public void openCurrentSoldatMenuItems(double sx,double sy) {
        try{
            PointCarte p=convertSceneCoord(sx, sy);
            FXSoldat s=fxIMHelper.getFXSoldatSelectionee();
            if(s!=null){
                System.out.println("-------->"+s);
                fxIMHelper.setCommanNotvalid(true);
                imprimerFXHelperSoldatProfile();
                initFXHelperInstance(s);
                closeFXCarteMenuItems();
                buildMenuItems(sx,sy,p.getI(),p.getJ());
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }    
    
    private void removeMenuItems() {
        for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
                rootGroup.getChildren().remove(actionMenu[k]);
            }
        }
    }
    
    

    

  protected void defaceMenuItems(){
      //deselectionneSoldats();
      //newHelperInstance((FXSoldat)null);
      removeMenuItems();
  
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
 
    private void buildMarcheMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery)throws Exception{
        
            MenuItem m = new WalkItem(sfx);
            Soldat s=sfx.getSoldat();
            


            double x = MenuItem.MENU_W * Math.cos(0);
            double y = MenuItem.MENU_H * Math.sin(0);

            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            //m.setOnMouseClicked(new ActionMenuSelectionEventHandler(m, actionMenu, this));
            actionMenu[0] = m;
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            rootGroup.getChildren().add(m);   
            if(s.isTempDisponiblePour(ActionType.MARCHE)) {
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));
            }
            else {
                m.setEffect(new GaussianBlur());
                m.setOnMouseClicked(null);
            }
            
       
    
    }
   private void buildCoreurMenuItem(FXSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem  m = new RunItem(s);
            double x = (100 * Math.cos(1 * grad));
            double y = (100 * Math.sin(1 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);   
            rootGroup.getChildren().add(m);   
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            actionMenu[1] = m;            
    
    }
    private void buildFeuMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad)throws Exception{
        
            MenuItem m = new FeuItem(sfx);
            double x = (100 * Math.cos(2 * grad));
            double y = (100 * Math.sin(2 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            rootGroup.getChildren().add(m);
            //rootGroup.getChildren().add(l);
           m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            actionMenu[2] = m;    
            Soldat s=sfx.getSoldat();
            if(!s.isTempDisponiblePour(ActionType.FEU)){
                m.setEffect(new GaussianBlur());
                m.setOnMouseClicked(null);
            }
    }
    
    
    private void buildOpFeuMenuItem(FXSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem m = new OpFeuItem(s);
            double x = (100 * Math.cos(3 * grad));
            double y = (100 * Math.sin(3 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            rootGroup.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            actionMenu[3] = m;

}
 
    
protected void buildDisableMenu(FXSoldat s){

            MenuItem m = new DisableMenuItems(s);

            int relativeI = s.getSoldat().getI() - posI;
            int relativeJ = s.getSoldat().getJ() - posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + relativeI + "," + relativeJ);
            double x = 100 * Math.cos(0);
            double y = 100 * Math.sin(0);
            // Point2D d2s = s.localToScene(s.getX(), s.getY());
            double sx = s.getTranslateX();
            double sy = s.getTranslateY();
            double spritecentery = sy + (TILE_SIZE / 2);
            double spritecenterx = sx + (TILE_SIZE / 2);
            if (relativeI <= 3) {
                spritecentery = spritecentery + (m.getH() * 2);
            }
            if (relativeJ <= 3) {
                spritecenterx = spritecenterx + (m.getW() * 2);
            }
            if (relativeI >= (AREA_SCROLL_I_H - 3)) {
                spritecentery = spritecentery - (m.getH() * 2);
            }
            if (relativeJ >= (AREA_SCROLL_J_W - 3)) {
                spritecenterx = spritecenterx - (m.getW() * 2);
            }
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
           // m.setOnMouseClicked(new SoldatClickOnActionItemsEventHandler(m, actionMenu, fxpl));
            actionMenu[0] = m;
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            rootGroup.getChildren().add(m);
            //m.setOnMouseClicked(new SoldatClickOnActionItemsEventHandler(m, actionMenu, fxpl));
           

}    


    private int relativeI(int reali) {
        int relativei = -1;
        int posi = posI;
        if (reali < posi) {
            relativei = -(posi - reali);
        } else if (reali >= (posi)) {
            relativei = (reali - posi);
        }
        return relativei;

    }

    private int relativeJ(int realj) {

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

        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();
        
    }
  
    public void confirmFEUAction(MenuItem item,double x,double y)throws Exception{
        int i=(int)(y/TILE_SIZE);
        int j=(int)(x/TILE_SIZE);
        BaseAction act=item.buildMenuItemAction();
        if(!fxIMHelper.isCommanNotvalid()){
            i=posI+i;
            j=posJ+j;
            fxIMHelper.buildFeuAction(act, i, j);
            fxIMHelper.addActionToSoldat();
            removeDisplayRange();
            fxpl.imprimerFXPLInfo(fxIMHelper.getSeletctionee());
            fxpl.sendMessageToPlayer(act.toString());
            reMountFXCarteMenuItemsAndScroll();
            visualizeBarSoldatAction();
        }else{
            fxpl.sendMessageToPlayer("Objective pa valide");
            closeFXCarteMenuItems();
            removeDisplayRange();
        }
   
        
    }

    
}
