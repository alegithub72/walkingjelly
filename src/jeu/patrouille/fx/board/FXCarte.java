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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.MoteurDeJoeur;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.AIJoeur;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.jeurs.FXAIJoueur;
import jeu.patrouille.fx.jeurs.FXMouseJeurs;
import jeu.patrouille.fx.menu.DisableMenuItems;
import jeu.patrouille.fx.menu.FeuItem;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.OpFeuItem;
import jeu.patrouille.fx.menu.RunItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatClickedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsFXCarteEventHandler;
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
    FXUSSoldat[]  fxequipeHost;
    FXUSSoldat[]  fxequipeUS;
    FXMouseJeurHelper helper;
    Sprite displayRange;
    boolean commanNotvalid;
    public Cursor current;

    
    
    
    public void playTurn() {
        mj.playTurn();
    }

    @Override
    public void play(BaseAction b) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("play grafic fx");
        //TODO disegnare lo schermo intorno al leader.....ma dove prendo l posizione del leader se faccio make e gia tutto cambiato
        //TODO devo fare una copia di tutte le posizioni dei soldati al momento dell'azione...?????no faccio prima l'animazione e poi
        //TODO cambio lo stato......
        //TODO quindi disegni intorno al leader e fai l'animazione se il protagonista e nel range....se e' un
        //TODO movimento ...se e' uno sparo visualizzi prima il soldato che spara al centro..e animi
        //TODO poi visualizzi il bersaglio e animi..........
        //TODO per il momento.....
        
    }

    public void setActionSeletione(boolean actionSeletione) {
        helper.setActionSeletione(actionSeletione);
    }

    public boolean isActionSeletione() {
        return helper.isActionSeletione();
    }
    
    
    public FXCarte(FXPlanche fxpl) throws IOException{
        
        arrow = new Sprite(100, 100, 100, 100, "arrowPng.png", null);
        this.fxpl=fxpl;
        displayRange=null;
        carte = new Carte("src/mapDesert.txt");
        FXAIJoueur  jHOST = new FXAIJoueur(carte,fxpl);
        FXMouseJeurs jUS= new FXMouseJeurs(GeneriqueJoeurs.JOEUR_US,carte,fxpl);
        
        fxequipeUS=jUS.getFxEquipe();
        fxequipeHost=jHOST.getFxEquipe();      
                
        this.mj=new MoteurDeJoeur(jUS,jHOST,carte);
        // mj.setActiveJeur(MoteurDeJoeur.JEUR_US);
        rootGroup = new Group();
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
        ImageView test = new ImageView(new Image("menuItem.png"));
        buildCarte(c1);
        
        
        this.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
            }
        
        });
        this.setOnMouseMoved(new ScrollEventHandler(this));
        this.setOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler( this.fxpl));
        this.getChildren().add(rootGroup);
        
    }



    private MoteurDeJoeur getMj() {
        return mj;
    }

   private  GeneriqueJoeurs getActiveJeur() {
        return mj.getActiveJeur();
    }

   protected boolean carteValiderRoute(){
       return helper.carteValiderRoute();
   }  

    protected int mapLastJ() {
        return helper.mapLastJ();
    }

    protected int mapLastI() {
        return helper.mapLastI();
    }
    
    
    
    
    protected void addHelperInstance(BaseAction act){
     
        if(helper!=null) {
            helper.setAct(act);

        }


        
    }
   protected void initHelperInstance(FXUSSoldat s){
       if (s != null) {
           if (s instanceof FXUSSoldat) {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           } else {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           }
           mj.getActiveJeur().setPieceSelectionee(s.getSoldat());
       }
       helper= new FXMouseJeurHelper(s, carte);
    }
   
   protected void initHelperSoldatInstance(FXUSSoldat s){
       if (s != null) {
           if (s instanceof FXUSSoldat) {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           } else {
               mj.setActiveJeur(GeneriqueJoeurs.JOEUR_HOST);
           }
           mj.getActiveJeur().setPieceSelectionee(s.getSoldat());
       }
       helper= new FXMouseJeurHelper(s, carte);
    }   
   

   
 

    
    @Deprecated
    private Sprite getArrow() {
        return arrow;
    }
   @Deprecated
    private void setArrow(Sprite arrow) {
        this.arrow = arrow;
    }


    protected Group getRootGroup() {
        return rootGroup;
    }

    protected void visualizeRangePointer(double mousex,double mousey){
         
            int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
            int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);  
            System.out.println("---------visualizeRangePointer----------------------_->"+scrollMousej);
            System.out.println("------------visualizeRangePointer-------------------_->"+scrollMousei);             
            double arrowPosx=(scrollMousej * FXCarte.TILE_SIZE);
            double arrowPosY=(scrollMousei * FXCarte.TILE_SIZE) ;
            if(arrowPosx>0 && arrowPosx<FXCarte.PIXEL_SCROLL_AREA_W) 
                displayRange.setX( arrowPosx);
            if (arrowPosY >0 && arrowPosY<FXCarte.PIXEL_SCROLL_AREA_H)
                displayRange.setY(arrowPosY);    
            System.out.println("arrowrange (x,y) =("+arrowPosx+","+arrowPosY+")");

            displayRange.setVisible(true);
        
    
    }
    protected void deactiveRangePointer(){
    if(displayRange!=null) {
        displayRange.setVisible(false);
      
    }
    
    
        
    }

    public int getRangeCursorHelper() {
        return helper.getRangeCursorHelper();
    }

    public void setRangeCursorHelper(int rangeCursorHelper) {
        helper.setRangeCursorHelper(rangeCursorHelper);        
        buildDisplayRange();
        
    }

    public void resetCursorHelper() {
        helper.resetCursorHelper();
        buildDisplayRange();
    }
    
    
    
    
    private void buildDisplayRange(){
        
        
        if (displayRange == null) {
            if(helper.getRangeCursorHelper()==ImageChargeur.CURSOR_HOST_RANGE)
            displayRange = new Sprite(50, 50, 50, 50, "rangeArrowHost.png", null);
            else if(helper.getRangeCursorHelper()==ImageChargeur.CURSOR_US_RANGE) 
                displayRange = new Sprite(50, 50, 50, 50, "rangeArrow2.png", null);
            else if(helper.getRangeCursorHelper()==ImageChargeur.CURSOR_FORBIDDEN)
                displayRange=new Sprite(50, 50, 50, 50, "forbiddenCursor.png", null);
            this.rootGroup.getChildren().add(displayRange);
        }else{

         Image img=null;
            if (helper.getRangeCursorHelper() == ImageChargeur.CURSOR_FORBIDDEN) {
                img = ImageChargeur.getInstance().getImage(ImageChargeur.CURSOR_FORBIDDEN);
            }
            if (helper.getRangeCursorHelper() == ImageChargeur.CURSOR_HOST_RANGE) {
                img = ImageChargeur.getInstance().getImage(ImageChargeur.CURSOR_HOST_RANGE);
            } else if (helper.getRangeCursorHelper() == ImageChargeur.CURSOR_US_RANGE) {
                img = ImageChargeur.getInstance().getImage(ImageChargeur.CURSOR_US_RANGE);
            }
            displayRange.setFrameImages(img);
      
         
        }

        
    }


    private void setFXSoldatSelectionee(FXUSSoldat selectionee) {

    }

    protected void setFXHelperArrivalCarteCoord(int i1, int j1) {
        helper.setArrivalCarteCoord(i1, j1);
    }

    protected void addSoldataSelectioneeAction() {
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

    protected boolean  rangeMarcheSoldat(double range) {
       return helper.rangeMarcheSoldat(range);
    }
    
    protected void resetHelperAction(){
        helper.setAct(null);
    }



    
    
    protected Canvas activeCanvas(){
        if (switchsCanvas) {
            return c1;
        }
        return c2;
    }
    protected void refreshCarte(){
        if(this.switchsCanvas)
        buildCarte(c1);
        else buildCarte(c2);
    }
    void buildCarte(Canvas canv) {
        String a = "";
        canv.getGraphicsContext2D().setFill(Color.rgb(64, 128, 0, 1));
        canv.getGraphicsContext2D().fillRect(0, 0, PIXEL_SCROLL_AREA_W, PIXEL_SCROLL_AREA_H);
        System.out.println("scroolw,scrollh=" + AREA_SCROLL_J_W + "," + AREA_SCROLL_I_H);
        FXUSSoldat[] fxs = new FXUSSoldat[8];
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
                Piece piece = null;
                if (tile != null) {
                    piece = tile.getPiece();
                }
                if (piece != null) {
                    if (piece instanceof AISoldat) {
                        fxs[k] = fxequipeHost[piece.getarrayN()];
                        enableSoldatoInView(fxs[k], x0, y0);
                        k++;
                    } else if (piece instanceof Soldat) {
                        fxs[k] = fxequipeUS[piece.getarrayN()];
                        enableSoldatoInView(fxs[k], x0, y0);
                        k++;
                    }
                    //System.out.println("here ="+x0+","+y0+" ");
                }
                //if(ob!=null)System.out.println(" i,j"+(m+posX)+","+(n+posY));
                //if(ob!=null)
            }
        }
    }

    void enableSoldatoInView(FXUSSoldat s, double x0, double y0) {
        s.setX(x0);
        s.setY(y0);  
        s.defaultFrame();
        if (!rootGroup.getChildren().contains(s)) {
            rootGroup.getChildren().add(s);
        }
        s.toFront();
        s.buildSprite();
        s.setVisible(true);
        
    }

    void suprimmerSoldatNotEnView() {
        for (int h = 0; h < fxequipeHost.length; h++) {
            int i = ((AISoldat) fxequipeHost[h].getSoldat()).getI();
            int j = ((AISoldat) fxequipeHost[h].getSoldat()).getJ();
            if (j > (posJ + (AREA_SCROLL_J_W - 1))) {
                fxequipeHost[h].setVisible(false);
            }
            if (j < (posJ)) {
                fxequipeHost[h].setVisible(false);
            }
            if (i > (posI + (AREA_SCROLL_I_H - 1))) {
                fxequipeHost[h].setVisible(false);
            }
            if (i < (posI)) {
                fxequipeHost[h].setVisible(false);
            }
        }
        for (int h = 0; h < fxequipeUS.length; h++) {
            int i = fxequipeUS[h].getSoldat().getI();
            int j = fxequipeUS[h].getSoldat().getJ();
            if (j > (posJ + AREA_SCROLL_J_W - 1)) {
                //rootGroup.getChildren().remove(fxequipeUS[h]);
                fxequipeUS[h].setVisible(false);
            }
            if (j < (posJ)) {
                fxequipeUS[h].setVisible(false);
            }
            if (i > (posI + AREA_SCROLL_I_H - 1)) {
                //rootGroup.getChildren().remove(fxequipeUS[h]);
                fxequipeUS[h].setVisible(false);
            }
            if (i < posI) {
                //rootGroup.getChildren().remove(fxequipeUS[h]);
                fxequipeUS[h].setVisible(false);
            }
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
            suprimmerSoldatNotEnView();
            if (switchsCanvas) {
                buildCarte(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarte(c1);
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
            suprimmerSoldatNotEnView();
            if (switchsCanvas) {
                buildCarte(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarte(c1);
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
            suprimmerSoldatNotEnView();
            if (switchsCanvas) {
                buildCarte(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarte(c1);
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
            suprimmerSoldatNotEnView();
            if (switchsCanvas) {
                buildCarte(c2);
                c1.setVisible(false);
                c2.setVisible(true);
            } else {
                buildCarte(c1);
                c1.setVisible(true);
                c2.setVisible(false);
            }
            switchsCanvas = !switchsCanvas;
        } else {
                  
           // arrow.setFrame(1);
        }
        return updateScroll;
    }

    protected int getPosI() {
        return posI;
    }

    protected int getPosJ() {
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
    

   private Point2D getMenuCoord(){
   
        FXUSSoldat s=helper.getFXSoldatSelectionee();
        

            int relativeI = s.getSoldat().getI() - posI;
            int relativeJ = s.getSoldat().getJ() - posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + relativeI + "," + relativeJ);

            // Point2D d2s = s.localToScene(s.getX(), s.getY());
            double sx = s.getX();
            double sy = s.getY();
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
   
   
   
    protected synchronized void buildMenuItems() {
            Point2D spritecoord2D=getMenuCoord();
            FXUSSoldat s=helper.getFXSoldatSelectionee();
            double r= (2 * Math.PI) / 8;
            s.surlignerSoldat();
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

    protected void openMenuItems(){
     for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
               actionMenu[k].setVisible(true);
               actionMenu[k].release();
            }
        }
    
    }
    
    protected void removeMenuItems() {
        for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
                rootGroup.getChildren().remove(actionMenu[k]);
            }
        }
    }
    protected void devisualizeMenuItems() {
        for (int k = 0; k < actionMenu.length; k++) {
            if (actionMenu[k] != null) {
                actionMenu[k].setVisible(false);
            }
        }

    }    
    

    

  protected void defaceMenuItems(){
      //deselectionneSoldats();
      //newHelperInstance((FXSoldat)null);
      removeMenuItems();
  
  }

  protected void deselectionneSoldats() {
      if(helper!=null){
      FXUSSoldat s = helper.getFXSoldatSelectionee();

      for (int k = 0; k < fxequipeHost.length; k++) {

              fxequipeHost[k].defaultFrame();
              fxequipeHost[k].soldatDeselectionne();

      }
      for (int k = 0; k < fxequipeUS.length; k++) {

              fxequipeUS[k].defaultFrame();
              fxequipeUS[k].soldatDeselectionne();
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
            m.setX(menuItemx);
            m.setY(menuItemy);
            rootGroup.getChildren().add(m);
            if(s.getSoldat().getActionPoint()>=BaseAction.ACTIONPOINTVALOR[BaseAction.MARCHE]) {
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m,  fxpl));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m, fxpl));
            }
            else m.setEffect(new GaussianBlur());
            
       
    
    }
   private void buildCoreurMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem  m = new RunItem(s);
            double x = (100 * Math.cos(1 * grad));
            double y = (100 * Math.sin(1 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setX(menuItemx);
            m.setY(menuItemy);   
            rootGroup.getChildren().add(m);   
            m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m, fxpl));            
            actionMenu[1] = m;            
    
    }
    private void buildFeuMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem m = new FeuItem(s);
            double x = (100 * Math.cos(2 * grad));
            double y = (100 * Math.sin(2 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setX(menuItemx);
            m.setY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            rootGroup.getChildren().add(m);
            //rootGroup.getChildren().add(l);
           m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m,  fxpl));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m, fxpl));            
            actionMenu[2] = m;    
        
    }
    
    
    private void buildOpFeuMenuItem(FXUSSoldat s,double spritecenterx,double spritecentery,double grad){
            MenuItem m = new OpFeuItem(s);
            double x = (100 * Math.cos(3 * grad));
            double y = (100 * Math.sin(3 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setX(menuItemx);
            m.setY(menuItemy);
           m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m,  fxpl));
            m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m, fxpl));            
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
            double sx = s.getX();
            double sy = s.getY();
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
            m.setX(menuItemx);
            m.setY(menuItemy);
            rootGroup.getChildren().add(m);
            //m.setOnMouseClicked(new SoldatClickOnActionItemsEventHandler(m, actionMenu, fxpl));
           

}    




}
