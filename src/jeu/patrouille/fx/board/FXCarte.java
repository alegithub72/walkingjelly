/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;

import java.awt.PointerInfo;
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
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
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
import jeu.patrouille.fx.menu.eventhandler.ItemMenuConfirmMarcheEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuRangeDisplayHandler;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatClickedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsFXCarteEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXHostile;
import jeu.patrouille.fx.pieces.FXUSSoldat;
import jeu.patrouille.fx.sprite.Sprite;
import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author appleale
 */
public  class FXCarte extends Parent implements GraficCarteInterface{
    
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
    GeneriqueJoeurs usjoeurs;
    
    
    MenuItem[] actionMenu;
    @Deprecated
    Sprite arrow;

    FXPlanche fxpl;

    FXMouseJeurHelper helper;

    boolean commanNotvalid;
    public Cursor current;
    boolean animOn;
    FXAIJoueur jHOST ;
    FXMouseJeurs jUS ;
    FXUSSoldat[] fxequipeUS ;
    FXUSSoldat[] fxequipeHost ;
    
    
    public FXCarte(FXPlanche fxpl) throws IOException{
        
        // arrow = new Sprite(100, 100, 100, 100, "arrowPng.png", null);
        this.fxpl=fxpl;
        rootGroup = new Group();    
        

        carte = new Carte("src/mapDesert.txt");

        

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
       
        //Platform.setImplicitExit(true);
        mj.debutRond();
        
    }
    @Override
    public boolean isAnimOn() {
        return animOn;
    }

    @Override
    public void setAnimOn(boolean animPiece) {
        this.animOn = animPiece;
    }


    @Override
    public  void play(BaseAction b) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("---------------FXCARTE (NEW THREAD "+Thread.currentThread().getName()+")----------CREATE ANIM  -----INIZIO-----"
                + "-----------------------------------------><------");
        //TODO disegnare lo schermo intorno al leader.....ma dove prendo l posizione del leader se faccio make e gia tutto cambiato
        //TODO devo fare una copia di tutte le posizioni dei soldati al momento dell'azione...?????no faccio prima l'animazione e poi
        //TODO cambio lo stato......
        //TODO quindi disegni intorno al leader e fai l'animazione se il protagonista e nel range....se e' un
        //TODO movimento ...se e' uno sparo visualizzi prima il soldato che spara al centro..e animi
        //TODO poi visualizzi il bersaglio e animi..........
        //TODO per il momento.....
         Soldat sl
             =b.getProtagoniste().getBoss().findSquadLeader();
      
         
       // System.out.println("squad leader "+sl);
            
        System.out.println("posi="+posI+"posj="+posJ);
       // suprimmerSoldatsNotEnView();
        
       
        setOnMouseMoved(null);
        centerScrollArea(sl.getI(), sl.getJ());
        refreshCarte();
        refreshCarteAllFXSoldatViewPosition();  
        initFXHelperInstance(null);
        if(b.isProtagonisteTypeSoldat()
                && b.isProtagonisteHostile()){
            //refreshScrollArea(sl.getI(), sl.getJ());
            //defaceFXSoldatUSPaView();
            playHostile(b);
        }
        else if(b.isProtagonisteTypeSoldat() && !b.isProtagonisteHostile()) {
                  
            //refreshScrollArea(sl.getI(), sl.getJ()); 
            //defaceFXSoldatHostPaView();
 
            playUSSoldat(b);
        }

        System.out.println("---------------FXCARTE (NEW THREAD "+Thread.currentThread().getName()+")----------CREATE ANIM  ----FINE------"
                + "-----------------------------------------><------");
   
    }
private void centerScrollArea(int i,int j){
        int h2=(FXCarte.AREA_SCROLL_I_H/2);
        int w2=(FXCarte.AREA_SCROLL_J_W/2);
        if((i+h2)>=(Carte.CARTE_SIZE_I-1))
            posI=i-FXCarte.AREA_SCROLL_I_H;
        if(i<=h2)
            posI=0;        
        else  posI=i-h2;
        
        if((j+w2)>=(Carte.CARTE_SIZE_J-1))
            posJ=j-(FXCarte.AREA_SCROLL_J_W);
        else if(j<=w2)
            posJ=0;
        else posJ=j-w2;
       
}


private boolean isScrollAreaChanged(int i1,int j1){
        int centerI=i1-(FXCarte.AREA_SCROLL_I_H/2),
            centerJ=j1-(FXCarte.AREA_SCROLL_J_W/2);
        return !(centerI==posI && centerJ==posJ);
       

}
    
//    private void refreshScrollArea(int i1,int j1){
//       if(isScrollAreaChanged(i1, j1)){
//           centerScrollArea(i1, j1);
//           System.out.println(" posi,posj "+posI+","+posJ);
//            if (switchsCanvas) {
//                buildCarteOnly(c2);
//                c1.setVisible(false);
//                c2.setVisible(true);
//                //rootGroup.getChildren().remove(c1);
//                //c1=new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
//                //rootGroup.getChildren().add(c1);
//            } else {
//                buildCarteOnly(c1);
//                c1.setVisible(true);
//                c2.setVisible(false);
//                //rootGroup.getChildren().remove(c2);
//               // c2=new Canvas(PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);    
//               // rootGroup.getChildren().add(c2);
//            }
//            switchsCanvas = !switchsCanvas;
//       
//       }else
//            refreshCarte();
    
    
 //   }
    private void playHostile(BaseAction b){
        FXHostile sfx=(FXHostile)findFXHostile((Soldat)b.getProtagoniste());
        if(sfx!=null) {
            helper.setFXSeletctionee(sfx);            
            if(estFXSoldatView(sfx)
                    && b.getType()==BaseAction.MARCHE)
            sfx.playMarche((MarcheAction)b);
           
        }    
   
    
    }
    
    private void playUSSoldat(BaseAction b){
        
        FXUSSoldat sfx=findFXUSSoldat((Soldat)b.getProtagoniste());

        if(sfx!=null) {
            helper.setFXSeletctionee(sfx);            
            if(estFXSoldatView(sfx) && 
                    b.getType()==BaseAction.MARCHE) {
                sfx.playMarche((MarcheAction)b);
            }
             
        }    
    }


    

    
    private FXUSSoldat findFXUSSoldat(Soldat s){
        FXUSSoldat searchedFX=null;
        for(FXUSSoldat sfx:  this.jUS.getFxEquipe()){
            if(sfx.getSoldat()==s)searchedFX=sfx;
        }
        return searchedFX;
    }
    private FXUSSoldat findFXHostile(Soldat s){
        
        FXUSSoldat searchedFX=null;
        for(FXUSSoldat sfx:  this.fxequipeHost){
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

            helper.setAct(act);




        
    }    
    

    public  boolean isFXHelperActionSeletiones() {
        return helper.isActionSelectione();
    }

    

    
    synchronized public void annulleCommand() {
        removeDisplayRange();
        helper.setCommanNotvalid(true);
        setOnMouseMoved(null);
        setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setOnMouseMoved(new ScrollEventHandler(this));
        setCursor(Cursor.DEFAULT);
        fxpl.sendMessageToPlayer("Action effacer");

    }

   
    
 synchronized public void confirmMarcheActionCommand(MenuItem item, double mousex, double mousey) {

        double x = mousex;
        double y = mousey;
        double tmpI = (y / FXCarte.TILE_SIZE);
        double tmpJ = (x / FXCarte.TILE_SIZE);
        System.out.println(x + "," + y);
        int i1 = ((int) tmpI) + posI;
        int j1 = ((int) tmpJ) + posJ;
        System.out.println("walk here i1=" + i1 + " j1=" + j1);
        helper.setArrivalCarteCoord(i1, j1);
        removeDisplayRange();

        setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setCursor(Cursor.DEFAULT);
        if (!helper.isCommanNotvalid()) {
            
            
            
            helper.addSoldataSelectioneeAction();

            imprimerFXHelperSoldatProfile();
            fxpl.visualizeActionBarActual();
            closeFXCarteMenuItems();
            //buildFXCarteMenuItems();
            //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
            fxpl.sendMessageToPlayer(helper.toString());
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
            helper.setActionSeletione(false);
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

        if (act.getType() == BaseAction.MARCHE) {
            
            addHelperInstance(act);
            helper.setActionSeletione(true);
            setOnMouseClicked(null);
            setOnMouseClicked(new ItemMenuConfirmMarcheEventHandler((WalkItem) item, this));
            setFXCarteCursor(Cursor.HAND);
            setOnMouseMoved(new ItemMenuRangeDisplayHandler(this,item.getActionType()));
            fxpl.sendMessageToPlayer("Choisir un emplacement");
            closeFXCarteMenuItems();

        } else if (act.getType() == BaseAction.FEU) {
            setFXCarteCursor(Cursor.CROSSHAIR);
        }

    }
    

    public MoteurDeJoeur getMj() {
        return mj;
    }

   private  GeneriqueJoeurs getActiveJeur() {
        return mj.getActiveJeur();
    }

   protected boolean carteValiderRoute(){
       return helper.carteValiderRoute();
   }  

    private int mapLastJ() {
        return helper.mapLastJ();
    }

    private int mapLastI() {
        return helper.mapLastI();
    }
    
    
    
    


   
   private void initFXHelperInstance(FXUSSoldat sfx){
       if (sfx != null) {
           Soldat s=sfx.getSoldat();
           
           if (s.getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST) {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           } else {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_US);
           }
           mj.getActiveJeur().setPieceSelectionee(s);
       }
       helper= new FXMouseJeurHelper(sfx, carte);
       
    }   
   

   
 

    


    public void addSprite(Sprite s){
        rootGroup.getChildren().add(s);
    
    }
    public Group getRootGroup() {
        return rootGroup;
    }

    private void removeDisplayRange(){
    
        if(rootGroup.getChildren().contains(helper.getDisplayRange()))
            rootGroup.getChildren().remove(helper.getDisplayRange());
            
    }
    private void visualizeRangePointers(double mousex,double mousey){
            if(!rootGroup.getChildren().contains(helper.getDisplayRange()))
                rootGroup.getChildren().add( helper.getDisplayRange());
            helper.getDisplayRange().toFront();
            helper.getDisplayRange().setVisible(true);
            int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
            int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);  
            System.out.println("---------visualizeRangePointer----------------------_->"+scrollMousej);
            System.out.println("------------visualizeRangePointer-------------------_->"+scrollMousei);             
            double arrowPosx=(scrollMousej * FXCarte.TILE_SIZE);
            double arrowPosY=(scrollMousei * FXCarte.TILE_SIZE) ;
            if(arrowPosx>0 && arrowPosx<FXCarte.PIXEL_SCROLL_AREA_W) 
                helper.getDisplayRange().setTranslateX( arrowPosx);
            if (arrowPosY >0 && arrowPosY<FXCarte.PIXEL_SCROLL_AREA_H)
                helper.getDisplayRange().setTranslateY(arrowPosY);    
            System.out.println("arrowrange (x,y) =("+arrowPosx+","+arrowPosY+")");
            
        
    
    }



    
    
        
    

    private void setRangeCursorHelpers(int rangeCursorHelper) {
        helper.setRangeCursorHelper(rangeCursorHelper);        

        
    }

    private void resetCursorHelper() {
        helper.resetCursorHelper();

    }
    
    
    
    







    private void addSoldataSelectioneeAction() {
        helper.addSoldataSelectioneeAction();
    }
    
    
    


  
    
   
   private BaseAction getMJActualAction(){
       
      return getActiveJeur().getActual();

   } 
   


    private void setMJActualAction(BaseAction act){
        getActiveJeur().setActual(act);
    }    



    protected FXMouseJeurHelper getHelper() {
        return helper;
    }

    private void setHelper(FXMouseJeurHelper helper) {
        this.helper = helper;
    }

    private boolean  rangeMarcheSoldat(double range) {
       return helper.rangeMarcheSoldat(range);
    }
    
    private void resetHelperAction(){
        helper.setAct(null);
    }



    
    
    private Canvas activeCanvas(){
        if (switchsCanvas) {
            return c1;
        }
        return c2;
    }
    
    
    
    public synchronized void displayMarcheRangeAction(double mousex, double mousey) {

        
       // visualizeFXCarteDisplayRange(mousex, mousey);

        int mapLastActI = helper.mapLastI();
        int mapLastActJ = helper.mapLastJ();
        double mapLastActy = mapLastActI * FXCarte.TILE_SIZE;
        double mapLastActx = mapLastActJ * FXCarte.TILE_SIZE;
        System.out.println(" (mapLastActI,mapLastActJ)=" + mapLastActI + "," + mapLastActJ);
        int posi = posI;
        int posj = posJ;
        
        System.out.println(" (posi,posj)=" + posI + "," + posJ);

        //TODO usare scrollmousej, and scrollMouei per verificare la validita del percorso
        int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
        int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);
        
        helper.setArrivalCarteCoord(scrollMousei+posI, scrollMousej+posJ);
       

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
        
        g.strokeLine(relativex, relativey,
                (scrollMousej * FXCarte.TILE_SIZE) + 25, (scrollMousei * FXCarte.TILE_SIZE) + 25);
        double angle =Piece.getDirection(relativex, relativey, (scrollMousej * FXCarte.TILE_SIZE) + 25, (scrollMousei * FXCarte.TILE_SIZE) + 25);
        FXUSSoldat s=helper.getFXSoldatSelectionee();
        //s.setFXSoldatOrientation(angle);
//        fxCarte.getRootGroup().getChildren().remove(p);
//       // p.getElements().removeAll()
//        p=new Path();
//        //fxCarte.getRootGroup().getChildren().remove(p);
//        LineTo lto=new LineTo( (scrollMousej * FXCarte.TILE_SIZE) + 25, (scrollMousei * FXCarte.TILE_SIZE) + 25);
//        MoveTo mto=new MoveTo(relativex, relativey);
//        mto.setAbsolute(true);
//        lto.setAbsolute(true);
//        p.getElements().add(mto);
//        p.getElements().add(lto);
//        
//        fxCarte.getRootGroup().getChildren().add(p);

        
        
        //p.setTranslateX(relativex);
        //p.setTranslateY(relativey);

       // fxCarte.refreshCarte();
       //TODO mettere una variabile per debug
        if (helper.rangeMarcheSoldat(r)
                && helper.carteValiderRoute()) {
            helper.setCommanNotvalid(false);
            setFXCarteCursor(Cursor.HAND);
            removeDisplayRange();
            resetCursorHelper();
            
            visualizeRangePointers(mousex, mousey);
           
            System.out.println("raggio---->" + r);
             System.out.println("angle------------------------------------------>"+angle);
        } else {
            removeDisplayRange();
            helper.setRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
            helper.setCommanNotvalid(true);
            //setFXCarteCursor(Cursor.CLOSED_HAND);
            //deactiveFXCarteRangePointer();
            
            visualizeRangePointers(mousex, mousey);
            System.out.println("fuori raggio---->" + r);
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
                canv.getGraphicsContext2D().fillText(i + "," + j, x0 + 10, y0 + 25);
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

        System.out.println("------------------SCROLL PRINT----------------------------------");
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
                canv.getGraphicsContext2D().fillText(i + "," + j, x0 + 10, y0 + 25);
                // canv.getGraphicsContext2D().strokeRect(x0, y0, PointCarte.TILE_SIZE, PointCarte.TILE_SIZE);
                //System.out.println("scrollI,scrollJ"+i+","+j);
                 x0=esteticCorrectionX0(j0, x0);
                 y0=esteticCorrectionY0(i0, y0);
                 enableAllFXSoldat(tile, x0, y0);
                //if(ob!=null)System.out.println(" i,j"+(m+posX)+","+(n+posY));
                //if(ob!=null)
            }
        }
System.out.println("------------------SCROLL PRINT----------------------------------");
    }
    void enableAllFXSoldat(Terrain tile,double x0,double y0){
        Piece piece = null;

        piece = tile.getPiece();

        //  System.out.println(tile+","+piece);
        if (piece != null) {
            if (piece.isHostile()) {
                System.out.println("----i,j=" + tile.getI() + "," + tile.getJ());
                enableSoldatoInView(fxequipeHost[piece.getarrayN()], x0, y0);
                int k=1;
                for(Piece p:tile.getExtraPiece()){
                    enableSoldatoInView(fxequipeHost[p.getarrayN()], x0+(20*k), y0+(k*20));
                    k++;
                }

            } else if (piece.isUS()) {
                System.out.println("----i,j=" + tile.getI() + "," + tile.getJ());
                enableSoldatoInView(fxequipeUS[piece.getarrayN()], x0, y0);
                int k=1;
                for(Piece p:tile.getExtraPiece()){
                    enableSoldatoInView(fxequipeUS[p.getarrayN()], x0+(20*k), y0+(20*k));
                    k++;
                }
            }
            //System.out.println("here ="+x0+","+y0+" ");
        } 
    
    }
    
    void enableSoldatoInView(FXUSSoldat s, double x0, double y0) {
        System.out.println("x0,y0="+x0+","+y0);
        //s.buildFXUSSoldat();
        s.setTranslateX(x0);
        s.setTranslateY(y0);
        //s.setLayoutX(x0);
        //s.setLayoutY(y0);
        s.toFront();
        s.setVisible(true);
        
    }

    private void visibilityOFFAllFXSoldatsPatEnView() {
 
        for (int h = 0; h < fxequipeUS.length; h++) {
            fxequipeUS[h].setVisible(false);
        }
        for (int h = 0; h < fxequipeHost.length; h++) {
            fxequipeHost[h].setVisible(false);
        }
    }    
    

   
   private void refreshCarteAllFXSoldatViewPosition() {
 
            refreshCarteHostFXSoldatPosition();
            refreshCarteUSFXSoldatPosition();
    }   


   private void refreshCarteUSFXSoldatPosition(){
           for (int h = 0; h < fxequipeUS.length; h++) 
               refreshCarteFXSoldatPosition(fxequipeUS[h]);
   
   }
   private void refreshCarteHostFXSoldatPosition(){
           for (int h = 0; h < fxequipeHost.length; h++) 
               refreshCarteFXSoldatPosition(fxequipeHost[h]);
   }     
    

private void refreshCarteFXSoldatPosition(FXUSSoldat sfx){
        if (!estFXSoldatView(sfx)) {
            sfx.setVisible(false);
        } else {
            Soldat s = sfx.getSoldat();
            System.out.println("reset position "+s.toStringSimple());
            Point2D p = getSceneCoordAnim(s,s.getI(), s.getJ());
            enableSoldatoInView(sfx, p.getX(), p.getY());
        }    
    }
    
    @Deprecated 
    public Point2D getSceneCoordScroll(Piece s,int  i,int j){
        System.out.println(" get coord "+i+","+j+" posI,posj="+posI+","+posJ);
        PointCarte cp=carte.getPointCarte(i, j);
        int scrollI=i-posI,scrollJ=j-posJ;
        double x0=(scrollJ*FXCarte.TILE_SIZE);
        double y0=(scrollI*FXCarte.TILE_SIZE); 
        //TODO ripensare la sovrapposizione dei soldati al limmite fare la scelta sul click...
        if(cp.getPiece()!=s && cp.isInExtra(s)){
            x0=(scrollJ*FXCarte.TILE_SIZE)+((cp.extraPiecePostion(s)+1)*20);
            y0=(scrollI*FXCarte.TILE_SIZE)+((cp.extraPiecePostion(s)+1)*20);
        }
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
    
    public Point2D getSceneCoordAnim(Piece s,int  i,int j){
        System.out.println(" get coord "+i+","+j+" posI,posj="+posI+","+posJ);
        double x0=((j-posJ)*FXCarte.TILE_SIZE);
        double y0=((i-posI)*FXCarte.TILE_SIZE); 
        Point2D p=new Point2D(x0, y0);
        return p;
    }   

    
    public boolean estFXSoldatView(FXUSSoldat sfx){
        Soldat s=sfx.getSoldat();
        return estFXSoldatView(s.getI(), s.getJ());
        
    }
    public boolean estFXSoldatView(int i1,int j1){
         boolean b=true;
            if (j1 > (posJ + (AREA_SCROLL_J_W ))) {
                b=false;
            }
            if (j1 < (posJ )) {
                b=false;
            }
            if (i1 > (posI  + (AREA_SCROLL_I_H))) {
                b=false;
            }
            if (i1 < (posI )) {
                b=false;
            }        
     return b;
    
    }   
    
    private void removeHostileEquipe(){
            for (int h = 0; h < fxequipeHost.length; h++) {
            fxequipeHost[h].setVisible(false);
            
        }
    
    }
        private void removeUSEquipe(){
            for (int h = 0; h < fxequipeUS.length; h++) {
            fxequipeUS[h].setVisible(false);
            
        }
    
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
   private void buildSoldatAction(){
        Soldat s=helper.getSeletctionee();
        fxpl.suprimerActionVisualization();
        int acN=s.actionSize();
        for(int k=0; k<acN;k++){
            BaseAction ac=s.nextAction(k);
            if(ac.getType()==BaseAction.MARCHE){
                    fxpl.visualizeActionBar(BaseAction.MARCHE,k);
            }
        }

    }
    

   private Point2D getMenuCoord(double sx,double sy,int i,int j){
   
            FXUSSoldat s=helper.getFXSoldatSelectionee();
        

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
   
   
   
    protected synchronized void buildMenuItems(double sx,double sy,int i,int j) {
            FXUSSoldat s=helper.getFXSoldatSelectionee();

            Point2D spritecoord2D=getMenuCoord(sx,sy,i,
                    j);

            double r= (2 * Math.PI) / 8;
            s.selectioneFXSoldat();
            if (s.getSoldat().isPossileDesplacer()) {
            
          
            
            buildMarcheMenuItem(s,spritecoord2D.getX(),spritecoord2D.getY());
            buildCoreurMenuItem(s, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildFeuMenuItem(s, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildOpFeuMenuItem(s, spritecoord2D.getX(),spritecoord2D.getY(), r);
           
            //rootGroup.getChildren().add(l);

            buildSoldatAction();
            fxpl.sendMessageToPlayer("Choisir une action");

        }else {
            defaceMenuItems();
            buildDisableMenu(s);
            fxpl.sendMessageToPlayer("Il ne peut pas agir");
        }
    
    }

    private void openMenuItems(){
     for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
               actionMenu[k].setVisible(true);
               actionMenu[k].release();
            }
        }
    
    }
    
    private void imprimerFXHelperSoldatProfile() {
        Soldat  s = helper.getSeletctionee();
 
        fxpl.imprimerFXPLInfo(s);
    }   
    
    synchronized public void openSoldatMenuItems(FXUSSoldat s) {
        defaceMenuItems();
        deselectionneAllSoldats();  
        initFXHelperInstance(s);
        imprimerFXHelperSoldatProfile();
        double sx=s.getTranslateX();
        double sy=s.getTranslateY();
        int i=s.getSoldat().getI();
        int j=s.getSoldat().getJ();
        buildMenuItems(sx,sy,i,j);
        fxpl.sendMessageToPlayer("Choisir une action from ("+i+","+j+")");

    }    
    
    synchronized public void openCurrentSoldatMenuItems(double sx,double sy) {
        PointCarte p=convertSceneCoord(sx, sy);
        FXUSSoldat s=helper.getFXSoldatSelectionee();
        if(s!=null){
            System.out.println("-------->"+s);
            helper.setCommanNotvalid(true);
            imprimerFXHelperSoldatProfile();
            initFXHelperInstance(s);
            closeFXCarteMenuItems();
            buildMenuItems(sx,sy,p.getI(),p.getJ());
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
      if(helper!=null){
      FXUSSoldat s = helper.getFXSoldatSelectionee();

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
 
    private void buildMarcheMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery){
        
            MenuItem m = new WalkItem(s);
            
            


            double x = MenuItem.MENU_W * Math.cos(0);
            double y = MenuItem.MENU_H * Math.sin(0);

            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            //m.setOnMouseClicked(new ActionMenuSelectionEventHandler(m, actionMenu, this));
            actionMenu[0] = m;
            m.setLayoutX(menuItemx);
            m.setLayoutY(menuItemy);
            rootGroup.getChildren().add(m);
            if(s.getSoldat().getActionPoint()>=BaseAction.ACTIONPOINTVALOR[BaseAction.MARCHE]) {
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));
            }
            else m.setEffect(new GaussianBlur());
            
       
    
    }
   private void buildCoreurMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem  m = new RunItem(s);
            double x = (100 * Math.cos(1 * grad));
            double y = (100 * Math.sin(1 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setLayoutX(menuItemx);
            m.setLayoutY(menuItemy);   
            rootGroup.getChildren().add(m);   
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            actionMenu[1] = m;            
    
    }
    private void buildFeuMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem m = new FeuItem(s);
            double x = (100 * Math.cos(2 * grad));
            double y = (100 * Math.sin(2 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setLayoutX(menuItemx);
            m.setLayoutY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            rootGroup.getChildren().add(m);
            //rootGroup.getChildren().add(l);
           m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            actionMenu[2] = m;    
        
    }
    
    
    private void buildOpFeuMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem m = new OpFeuItem(s);
            double x = (100 * Math.cos(3 * grad));
            double y = (100 * Math.sin(3 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setLayoutX(menuItemx);
            m.setLayoutY(menuItemy);
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  this));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            rootGroup.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            actionMenu[3] = m;

}
 
    
protected void buildDisableMenu(FXUSSoldat s){

            MenuItem m = new DisableMenuItems(s);

            int relativeI = s.getSoldat().getI() - posI;
            int relativeJ = s.getSoldat().getJ() - posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + relativeI + "," + relativeJ);
            double x = 100 * Math.cos(0);
            double y = 100 * Math.sin(0);
            // Point2D d2s = s.localToScene(s.getX(), s.getY());
            double sx = s.getLayoutX();
            double sy = s.getLayoutY();
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
            m.setLayoutX(menuItemx);
            m.setLayoutY(menuItemy);
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
        
//        for(int k=0;k<fxequipeHost.length;k++)
//            this.rootGroup.getChildren().remove(fxequipeHost[k]);
//        
//        for(int k=0;k<fxequipeUS.length;k++)
//            this.getChildren().remove(fxequipeUS[k]);
//        
//        fxequipeHost=  jHOST.rebuildFXEquipe();
//        fxequipeUS=jUS.getFxEquipe();
        refreshCarte();
        //resetAllPositionFXSoldatView();
        
    }
  
    
    
    
}
