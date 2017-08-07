/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.EndTurnEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuConfirmMarcheEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ItemMenuRangeDisplayHandler;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsFXCarteEventHandler;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.sprite.Sprite;
import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author appleale
 */
public class FXPlanche extends Application {

    public static void main(String[] args) {

        launch(args);
    }
    Path p;
    BorderPane borderPan;
    Canvas topPan;
    Canvas canvasBar;
    Group rootBarGroup;
    Group rootDroitBarGroup;
    Canvas droitCanvasBar;
    boolean scroolEnd = true;
    FXCarte fxCarte;
    Label message;
    List<Sprite> fxActionsPoolSelectionee;

     FXInfoPanel infPl;
    Font fontTitle;

    public void playFXCarteTurn() {
        fxCarte.playTurn();
    }

    
    
    
    public void addFxActionPoolSelectionee(Sprite sp) {
        fxActionsPoolSelectionee.add(sp);
    }

    Group getRootBarGroup() {
        return rootBarGroup;
    }

    FXCarte getFXCarte() {
        return fxCarte;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream i = ClassLoader.getSystemResourceAsStream("Lintsec Regular.ttf");
        borderPan = new BorderPane();
       
        
        //i = ClassLoader.getSystemResourceAsStream("BlackOpsOne-Regular.ttf");        
        fontTitle =Font.loadFont(i, 18);

        fxActionsPoolSelectionee = new ArrayList<>();
        Image img = new Image("bc.jpeg");
        BackgroundImage bckImg = new BackgroundImage(img, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bc = new Background(bckImg);
        // borderPan.setBackground(  bc);
        buildBar();
        // buildTop();        
        buildDroitBar();
        ImageView test = new ImageView(new Image("menuItem.png"));

        fxCarte = new FXCarte(this);
        borderPan.setCenter(fxCarte);
        // Pane pan = new Pane(rootGroup);
        

        primaryStage.setResizable(false);
        //reg.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Scene sc = new Scene(borderPan);
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.show();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

    }

    public Font getFontTitle() {
        return fontTitle;
    }
    
    void defaceMenuItems() {
        this.fxCarte.defaceMenuItems();

    }

    void addHelperInstance(BaseAction act) {
        fxCarte.addHelperInstance(act);
    }

    final void setFXCarteOnMouseClicked(EventHandler<? super MouseEvent> value) {
        //fxCarte.setOnMouseClicked(value);
        fxCarte.setOnMouseClicked(value);
    }


    final void setFXCarteCursor(Cursor value) {
        fxCarte.current = value;
        if(value!=fxCarte.getCursor())
        fxCarte.setCursor(value);
    }

    int getFXCartePosI() {
        return fxCarte.getPosI();
    }

    int getFXCartePosJ() {
        return fxCarte.getPosJ();
    }

    final void setFXCarteOnMouseMoved(EventHandler<? super MouseEvent> value) {
        fxCarte.setOnMouseMoved(value);
    }

    FXMouseJeurHelper getFXCarteActionHelper() {
        return fxCarte.getHelper();
    }

    void addFXCarteSoldataSelectioneeAction() {
        fxCarte.addSoldataSelectioneeAction();
    }

    void resetFXCarteHelperAction() {
        fxCarte.resetHelperAction();
    }

    void setFXCarteHelperConmmanNoTValid(boolean value) {
        this.fxCarte.getHelper().setCommanNotvalid(value);
    }

    void deactiveFXCarteRangePointer() {
        fxCarte.deactiveRangePointer();
    }

    void visualizeFXCarteDisplayRange(double mousex, double mousey) {
        fxCarte.visualizeRangePointer(mousex, mousey);
    }

    Canvas getFXCarteActiveCanvas() {
        return fxCarte.activeCanvas();
    }

    public synchronized boolean scrollFXCarteCanvas(double x, double y) {
        return fxCarte.scrollCanvas(x, y);
    }

    void initFXCarteHelperInstance(FXSoldat s) {
        fxCarte.initHelperInstance(s);
    }
    void initFXCarteSoldatHelperInstance(FXSoldat s) {
        fxCarte.initHelperSoldatInstance(s);
    }    

    void setFXCarteHelperArrivalCarteCoord(int i1, int j1) {
        fxCarte.setFXHelperArrivalCarteCoord(i1, j1);
    }

    synchronized public void closeFXCarteMenuItems() {
        
        this.fxCarte.devisualizeMenuItems();

    }

    void buildFXCarteMenuItems() {
        this.fxCarte.buildMenuItems();

    }



    void buildBar() throws IOException {
        
        canvasBar = new Canvas(FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);
        rootBarGroup = new Group();
        rootBarGroup.getChildren().add(canvasBar);
        GraphicsContext gc = canvasBar.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);
        gc.drawImage(new Image("barConsole.png"), 0, 0);
        
        
        //gc.setFont(fontTitle);
        //gc.setFill(Color.BISQUE);
        //gc.fillText("Message:", 10, 18);
        
        message = new Label("");
        message.setTextFill(Color.WHITE);
        message.setFont(fontTitle);
        message.relocate(10, 5);
        message.setStyle("");


        borderPan.setBottom(rootBarGroup);

        Sprite endButton = new Sprite(120, 50, 120, 50, "endturn.png", this);
        endButton.setX(880);
        endButton.setY(0);
        EventHandler e=new EndTurnEventHandler(this, endButton);
        endButton.setOnMousePressed(e);
        endButton.setOnMouseReleased(e);
        rootBarGroup.getChildren().add(endButton);
        rootBarGroup.getChildren().add(message);
    }
 
    void buildTop() {
        topPan = new Canvas(FXCarte.PIXEL_SCROLL_AREA_W, FXCarte.TOP_H);
        //borderPan.setTop(topPan);      
        //borderPan.setTop(new Label("STATUS DEL SOLDAT"));      
        //topPan.getGraphicsContext2D().setFill(Color.DARKKHAKI);
        //topPan.getGraphicsContext2D().fillRect(0, 0, w, TOP_H);

    }

    void buildDroitBar() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setSpread(1);
        dropShadow.setColor(Color.BLACK);
        rootDroitBarGroup = new Group();
        droitCanvasBar = new Canvas(FXCarte.DROIT_BAR_W, FXCarte.PIXEL_SCROLL_AREA_H);
        GraphicsContext gc = droitCanvasBar.getGraphicsContext2D();
        Image img = new Image("backCamo2.png");
        //gc.setFill(Color.rgb(64, 128, 0, 1));
        gc.setFill(Color.TRANSPARENT);

        Text t = new Text();

        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        gc.drawImage(img, 0, 0);
        //gc.setFill(Color.rgb(128,0,255)); grape
        gc.setFill(Color.WHITE); 
        gc.setFont(fontTitle);
 
        borderPan.setRight(rootDroitBarGroup);
        
        gc.fillText("Nom:", 2, 26);
        gc.fillText("Classment:", 2, 78+10);
        gc.fillText("TD:", 2, 130+30);
        gc.fillText("EQUIPMENT:", 11, 315);
        infPl=new FXInfoPanel(this, 10, 40);
        
        rootDroitBarGroup.getChildren().add(droitCanvasBar);
        rootDroitBarGroup.getChildren().add(infPl);

      
        BorderWidths bw = new BorderWidths(3);
        

        //Border b = new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, cr, bw));
        //borderPan.setBorder(b);
        //droitPan.getGraphicsContext2D().setFill(Color.DARKKHAKI);

        //droitPan.getGraphicsContext2D().fillRect(w, 0, w+DROIT_BAR_W, h);
    }

    void sendMessageToPlayer(String text) {
        message.setText(text);

    }

    void visualizeActionBarActual() {
        int type = fxCarte.getHelper().getAct().getType();
        int k = fxCarte.getHelper().getSeletctionee().actionSize() - 1;
        visualizeActionBar(type, k);

    }

    void visualizeActionBar(int actionType, int k) {
        //TODO compliche trought scroll panel !!!!!!!

        if (actionType == BaseAction.MARCHE) {
            Sprite spAct = new WalkItem(null);
            this.getRootBarGroup().getChildren().add(spAct);
            spAct.setScaleX(0.5);
            spAct.setScaleY(0.5);
            spAct.setFrame(1);
            spAct.toFront();

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            spAct.setEffect(dropShadow);
            spAct.setX(500 + (k * (spAct.getW() / 2)));
            if (k < 10) {
                spAct.setY(-23);
            } else {
                spAct.setY((spAct.getH() / 2));
            }
            this.fxActionsPoolSelectionee.add(spAct);
        }

    }

    void imprimerProfile() {
        Soldat s = null;
        if (this.fxCarte.getHelper() != null) {
            s = this.fxCarte.getHelper().getSeletctionee();
        }
        this.infPl.imprimerInfo(s);
    }

    void suprimerActionVisualization() {

        for (int k = 0; k < fxActionsPoolSelectionee.size(); k++) {
            rootBarGroup.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }

    synchronized public void openSoldatMenuItems(FXSoldat s) {
        defaceMenuItems();
        fxCarte.deselectionneSoldats();        
        initFXCarteSoldatHelperInstance(s);
        imprimerProfile();
        buildFXCarteMenuItems();
        sendMessageToPlayer("Choisir une action");

    }
    synchronized public void openCurrentSoldatMenuItems() {
        FXSoldat s=getFXCarteActionHelper().getFXSoldatSelectionee();
        System.out.println("-------->"+s);
        setFXCarteHelperConmmanNoTValid(true);
        imprimerProfile();
        initFXCarteSoldatHelperInstance(s);
      
        closeFXCarteMenuItems();
        buildFXCarteMenuItems();
        

    }

    public void setFXCarteActionSeletione(boolean actionSeletione) {
        fxCarte.setActionSeletione(actionSeletione);
    }

    public boolean isFXCarteActionSeletione() {
        return fxCarte.isActionSeletione();
    }
    
    
    
    
    synchronized public void clickOnButtonItems(MenuItem item) {

        item.setFrame(1);
        BaseAction act = item.buildMenuItemAction();

        if (act.getType() == BaseAction.MARCHE) {
            addHelperInstance(act);
            setFXCarteActionSeletione(true);
            setFXCarteOnMouseClicked(null);
            setFXCarteOnMouseClicked(new ItemMenuConfirmMarcheEventHandler((WalkItem) item, this));
            setFXCarteCursor(Cursor.HAND);
            setFXCarteOnMouseMoved(new ItemMenuRangeDisplayHandler(this,item.getActionType()));
            sendMessageToPlayer("Choisir un emplacement");
            closeFXCarteMenuItems();

        } else if (act.getType() == BaseAction.FEU) {
            setFXCarteCursor(Cursor.CROSSHAIR);
        }

    }

    public synchronized void refreshFXCarteCarte() {
        fxCarte.refreshCarte();
    }

    public int getFXCarteRangeCursorHelper() {
        return fxCarte.getRangeCursorHelper();
    }

    public void setFXCarteRangeCursorHelper(int rangeCursorHelper) {
        fxCarte.setRangeCursorHelper(rangeCursorHelper);
    }

    
    
    public synchronized void displayMarcheRangeAction(double mousex, double mousey) {

        
       // visualizeFXCarteDisplayRange(mousex, mousey);

        int mapLastActI = getFXCarteActionHelper().mapLastI();
        int mapLastActJ = getFXCarteActionHelper().mapLastJ();
        double mapLastActy = mapLastActI * FXCarte.TILE_SIZE;
        double mapLastActx = mapLastActJ * FXCarte.TILE_SIZE;
        System.out.println(" (mapLastActI,mapLastActJ)=" + mapLastActI + "," + mapLastActJ);
        int posi = getFXCartePosI();
        int posj = getFXCartePosJ();
        System.out.println(" (posi,posj)=" + getFXCartePosI() + "," + getFXCartePosJ());

        //TODO usare scrollmousej, and scrollMouei per verificare la validita del percorso
        int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
        int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);

        setFXCarteHelperArrivalCarteCoord(scrollMousei, scrollMousej);

        double mouseMapx = (scrollMousej + posj) * FXCarte.TILE_SIZE;
        double mouseMapy = (scrollMousei + posi) * FXCarte.TILE_SIZE;

        double circleX = Math.pow(mouseMapx - mapLastActx, 2);
        double circleY = Math.pow(mouseMapy - mapLastActy, 2);
        double r = Math.sqrt(circleX + circleY);

        double relativex = relativeJ(mapLastActJ) * FXCarte.TILE_SIZE + 25;
        double relativey = relativeI(mapLastActI) * FXCarte.TILE_SIZE + 25;
        System.out.println("relativex,relativey " + relativex + "," + relativey);
        GraphicsContext g=getFXCarteActiveCanvas().getGraphicsContext2D();
        //g.setLineWidth(10);
        g.setStroke(Color.FLORALWHITE);
        g.strokeLine(relativex, relativey,
                (scrollMousej * FXCarte.TILE_SIZE) + 25, (scrollMousei * FXCarte.TILE_SIZE) + 25);
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
        if (getFXCarteActionHelper().rangeMarcheSoldat(r)
                && getFXCarteActionHelper().carteValiderRoute()) {
            setFXCarteHelperConmmanNoTValid(false);
            setFXCarteCursor(Cursor.HAND);
            resetCursorHelper();
            visualizeFXCarteDisplayRange(mousex, mousey);
           
            System.out.println("raggio---->" + r);
        } else {
            setFXCarteRangeCursorHelper(ImageChargeur.CURSOR_FORBIDDEN);
            setFXCarteHelperConmmanNoTValid(true);
            //setFXCarteCursor(Cursor.CLOSED_HAND);
            //deactiveFXCarteRangePointer();
            visualizeFXCarteDisplayRange(mousex, mousey);
            System.out.println("fuori raggio---->" + r);
        }
        
    }

    public void resetCursorHelper() {
        fxCarte.resetCursorHelper();
    }

    private int relativeI(int reali) {
        int relativei = -1;
        int posi = getFXCartePosI();
        if (reali < posi) {
            relativei = -(posi - reali);
        } else if (reali >= (posi)) {
            relativei = (reali - posi);
        }
        return relativei;

    }

    private int relativeJ(int realj) {

        int posj = getFXCartePosJ();
        int relativej = -1;

        if (realj < posj) {
            relativej = -(posj - realj);
        } else if (realj >= (posj)) {
            relativej = (realj - posj);
        }
        return relativej;

    }

   synchronized public void confirmMarcheActionCommand(MenuItem item, double mousex, double mousey) {

        double x = mousex;
        double y = mousey;
        double tmpI = (y / FXCarte.TILE_SIZE);
        double tmpJ = (x / FXCarte.TILE_SIZE);
        System.out.println(x + "," + y);
        int i1 = ((int) tmpI) + getFXCartePosI();
        int j1 = ((int) tmpJ) + getFXCartePosJ();
        System.out.println("walk here i1=" + i1 + " j1=" + j1);
        setFXCarteHelperArrivalCarteCoord(i1, j1);
        deactiveFXCarteRangePointer();

        setFXCarteOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setFXCarteCursor(Cursor.DEFAULT);
        if (!getFXCarteActionHelper().isCommanNotvalid()) {

            addFXCarteSoldataSelectioneeAction();

            imprimerProfile();
            visualizeActionBarActual();
            closeFXCarteMenuItems();
            //buildFXCarteMenuItems();
            //fxcarte.buildMenuItem((FXSoldat)item.getFXSoldat());
            sendMessageToPlayer(getFXCarteActionHelper().toString());
            //resetFXCarteHelperAction();

        } else {
       

            
            sendMessageToPlayer("Action non valide");
        }
            setFXCarteOnMouseMoved(new ScrollEventHandler(fxCarte));
            setFXCarteActionSeletione(false);
    }

    synchronized public void annulleCommand() {
        getFXCarteActionHelper().setCommanNotvalid(true);
        setFXCarteOnMouseMoved(null);
        setFXCarteOnMouseClicked(new SoldatOpenMenuItemsFXCarteEventHandler(this));
        setFXCarteOnMouseMoved(new ScrollEventHandler(fxCarte));
        setFXCarteCursor(Cursor.DEFAULT);
        deactiveFXCarteRangePointer();
        sendMessageToPlayer("Action effacer");

    }

}
