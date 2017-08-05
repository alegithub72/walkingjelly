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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.ActionConfirmMarcheEventHandler;
import jeu.patrouille.fx.menu.eventhandler.ActionRangeDisplayHandler;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXPlanche extends Application {
    
    
    public static void main(String[] args) {
        
        launch(args);
    }

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
    Text profile;
    Font font ;
    Font fontTitle ;

    public void addFxActionPoolSelectionee(Sprite sp) {
        fxActionsPoolSelectionee.add(sp);
    }

    public Group getRootBarGroup() {
        return rootBarGroup;
    }

    public FXCarte getFXCarte() {
        return fxCarte;
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        InputStream i = ClassLoader.getSystemResourceAsStream("Lintsec Regular.ttf");
        borderPan = new BorderPane();
        font = Font.loadFont(i, 12);
        //i = ClassLoader.getSystemResourceAsStream("BlackOpsOne-Regular.ttf");        
        fontTitle = Font.font(font.getName(),16);
        
        fxActionsPoolSelectionee = new ArrayList<>();
        Image img=new Image("bc.jpeg");
        BackgroundImage bckImg=new BackgroundImage(img,  BackgroundRepeat.REPEAT, 
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bc=new Background(bckImg);
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

    public void defaceMenuItems() {
        this.fxCarte.defaceMenuItems();

    }

    public void addHelperInstance(BaseAction act) {
        fxCarte.addHelperInstance(act);
    }

    public final void setFXCarteOnMouseClicked(EventHandler<? super MouseEvent> value) {
        fxCarte.setOnMouseClicked(value);
    }

    public final void setFXCarteCursor(Cursor value) {
        fxCarte.current=value;
        fxCarte.setCursor(value);
    }

    public int getFXCartePosI() {
        return fxCarte.getPosI();
    }

    public int getFXCartePosJ() {
        return fxCarte.getPosJ();
    }


    
    

    public final void setFXCarteOnMouseMoved(EventHandler<? super MouseEvent> value) {
        fxCarte.setOnMouseMoved(value);
    }

    public FXMouseJeurHelper getFXCarteActionHelper() {
        return fxCarte.getHelper();
    }

    public void addFXCarteSoldataSelectioneeAction() {
        fxCarte.addSoldataSelectioneeAction();
    }

    public void resetFXCarteHelperAction() {
        fxCarte.resetHelperAction();
    }

    public void setFXCarteHelperConmmanNoTValid(boolean value){
        this.fxCarte.getHelper().setCommanNotvalid(value);
    }

    public void deactiveFXCarteRangePointer() {
        fxCarte.deactiveRangePointer();
    }


    
    
    
    public  void visualizeFXCarteDisplayRange(double mousex,double mousey) {
            System.out.println("---------visualizeFXCarteDisplayRange----------------------_->"+mousex);
            System.out.println("------------visualizeFXCarteDisplayRange-------------------_->"+mousey);        
        fxCarte.visualizeRangePointer(mousex,mousey);
    }

    public Canvas getFXCarteActiveCanvas() {
        return fxCarte.activeCanvas();
    }

    public synchronized boolean scrollFXCarteCanvas(double x, double y) {
        return fxCarte.scrollCanvas(x, y);
    }

 


    public void initFXCarteHelperInstance(FXSoldat s) {
        fxCarte.initHelperInstance(s);
    }

    public void setFXCarteHelperArrivalCarteCoord(int i1, int j1) {
        fxCarte.setFXHelperArrivalCarteCoord(i1, j1);
    }

    
    
    
    public void closeFXCarteMenuItems() {
        this.fxCarte.devisualizeMenuItems();

    }

    public synchronized void buildFXCarteMenuItems() {
        this.fxCarte.buildMenuItems();

    }
    
    public synchronized void openFXCarteMenuItems() {
        this.fxCarte.openMenuItems();

    }    


    void buildBar() throws IOException {
        canvasBar = new Canvas(FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);

        GraphicsContext gc = canvasBar.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);        
        rootBarGroup = new Group();
        rootBarGroup.getChildren().add(canvasBar);
        message = new Label("");
        message.setFont(font);
//      Reflection reflection = new Reflection();
//      reflection.setFraction(0.7);    
//      label.setEffect(reflection);
        message.relocate(120, 6);
        message.setTextFill(Color.WHITE);
        message.setStyle("");
        rootBarGroup.getChildren().add(message);
       gc.drawImage(new Image("barConsole.png"), 0, 0);
        borderPan.setBottom(rootBarGroup);
        gc.setFont(fontTitle);
        gc.setFill(Color.BISQUE);
        gc.fillText("Message:", 10, 18);
        Sprite endButton=new Sprite(120, 50, 120, 50, "endturn.png" , this);
        endButton.setX(880);
        endButton.setY(0);
        rootBarGroup.getChildren().add(endButton);
        //BorderWidths bw = new BorderWidths(3);
        //CornerRadii cr = new CornerRadii(2);

        //Border b = new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, cr, bw));

    }

    void buildTop() {
        topPan = new Canvas(FXCarte.PIXEL_SCROLL_AREA_W, FXCarte.TOP_H);
        //borderPan.setTop(topPan);      
        //borderPan.setTop(new Label("STATUS DEL SOLDAT"));      
        //topPan.getGraphicsContext2D().setFill(Color.DARKKHAKI);
        //topPan.getGraphicsContext2D().fillRect(0, 0, w, TOP_H);

    }

    void buildDroitBar() {

        rootDroitBarGroup = new Group();
        droitCanvasBar = new Canvas(FXCarte.DROIT_BAR_W, FXCarte.PIXEL_SCROLL_AREA_H);
        GraphicsContext gc=  droitCanvasBar.getGraphicsContext2D();        
        Image img = new Image("backCamo2.png");
        //gc.setFill(Color.rgb(64, 128, 0, 1));
        gc.setFill(Color.TRANSPARENT);
        
        Text t=new Text();
        
        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        gc.drawImage(img, 0, 0);
        gc.setFill(Color.BISQUE);
        gc.setFont(fontTitle);
        
        gc.fillText("SOLDAT RESUME",0, 38);
        
        gc.fillText("EQUIPMENT", 0, 270);
        borderPan.setRight(rootDroitBarGroup);
        profile = new Text(10, 80, "");
      
        profile.setFill(Color.color(1,1,1));
        profile.setFont(font);
        rootDroitBarGroup.getChildren().add(droitCanvasBar);
        rootDroitBarGroup.getChildren().add(profile);

        BorderWidths bw = new BorderWidths(3);
        CornerRadii cr = new CornerRadii(2);

        Border b = new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, cr, bw));
        borderPan.  setBorder(b);
        //droitPan.getGraphicsContext2D().setFill(Color.DARKKHAKI);

        //droitPan.getGraphicsContext2D().fillRect(w, 0, w+DROIT_BAR_W, h);
    }

    public void sendMessageToPlayer(String text) {
        message.setText(text);

    }
public void visualizeActionBarActual(){
    int type=fxCarte.getHelper().getAct().getType();
    int k=fxCarte.getHelper().getSeletctionee().actionSize()-1;
    visualizeActionBar(type,k);
    
}
    public void visualizeActionBar(int actionType,int k) {
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

    public void imprimerProfile() {
        Soldat s = null;
        if (this.fxCarte.getHelper() != null) {
            s = this.fxCarte.getHelper().getSeletctionee();
        }
        profile.setText(s.toString());
    }

    public void suprimerActionVisualization() {

        for (int k = 0; k < fxActionsPoolSelectionee.size(); k++) {
            rootBarGroup.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }
    
    
    public void openSoldatMenuItems(FXSoldat s){
            defaceMenuItems();
            initFXCarteHelperInstance(s);
            imprimerProfile();
            buildFXCarteMenuItems();
            sendMessageToPlayer(s.getSoldat().getNomDeFamilie()+" "+s.getSoldat().getNom());    
    
    }
    
    public void clickOnButtonItems(MenuItem item){
           
        
           item.setFrame(1);
           BaseAction act=item.buildMenuItemAction();
          
           if(act.getType()==BaseAction.MARCHE) {
                addHelperInstance(act);
                setFXCarteOnMouseClicked(new ActionConfirmMarcheEventHandler((WalkItem)item, this));               
                setFXCarteCursor(Cursor.HAND);
                setFXCarteOnMouseMoved(new ActionRangeDisplayHandler(this));
                sendMessageToPlayer("Scegli posizione");
                closeFXCarteMenuItems();                

           }else if(act.getType()==BaseAction.FEU){
              setFXCarteCursor(Cursor.CROSSHAIR);  
           }
                 
    
    
    }
    public void displayMarcheRangeAction(double mousex,double mousey){




            visualizeFXCarteDisplayRange(mousex,mousey);
            
            int mapLastActI = getFXCarteActionHelper().mapLastI();
            int mapLastActJ = getFXCarteActionHelper().mapLastJ();
            double mapLastActy = mapLastActI * FXCarte.TILE_SIZE;
            double mapLastActx = mapLastActJ * FXCarte.TILE_SIZE;
            System.out.println(" (mapLastActI,mapLastActJ)=" + mapLastActI + "," + mapLastActJ);
            int posi = getFXCartePosI();
            int posj = getFXCartePosJ();
            System.out.println(" (posi,posj)=" +getFXCartePosI() + "," + getFXCartePosJ());
           
            
            //TODO usare scrollmousej, and scrollMouei per verificare la validita del percorso
            int scrollMousej = (int) (mousex / FXCarte.TILE_SIZE);
            int scrollMousei = (int) (mousey / FXCarte.TILE_SIZE);
             
            setFXCarteHelperArrivalCarteCoord(scrollMousei, scrollMousej);

            
            
            double mouseMapx = (scrollMousej + posj) * FXCarte.TILE_SIZE;
            double mouseMapy = (scrollMousei + posi) * FXCarte.TILE_SIZE;

            double circleX = Math.pow(mouseMapx - mapLastActx, 2);
            double circleY = Math.pow(mouseMapy - mapLastActy, 2);
            double r = Math.sqrt(circleX + circleY);


            double relativex = relativeJ(mapLastActJ) * FXCarte.TILE_SIZE+25;
            double relativey = relativeI(mapLastActI) * FXCarte.TILE_SIZE+25;
            System.out.println("relativex,relativey " + relativex + "," + relativey);

            getFXCarteActiveCanvas().getGraphicsContext2D().strokeLine(relativex, relativey, 
                    (scrollMousej * FXCarte.TILE_SIZE) + 25,(scrollMousei * FXCarte.TILE_SIZE) + 25);


            if (  getFXCarteActionHelper().rangeMarcheSoldat(r) &&
                  getFXCarteActionHelper().carteValiderRoute()) 
            {

                setFXCarteHelperConmmanNoTValid(false);
                setFXCarteCursor(Cursor.HAND);
              
                System.out.println("raggio---->" + r);
            } else {
                deactiveFXCarteRangePointer();
                setFXCarteHelperConmmanNoTValid(true);
                ImageCursor imgcr=new ImageCursor(new Image("forbiddenCursor.png"));
                setFXCarteCursor(imgcr);
                          
                System.out.println("fuori raggio---->" + r);
            }
        }    
    
    
   private int relativeI(int reali){
        int relativei=-1;
        int posi=getFXCartePosI();
        if(reali<posi){
                    relativei=-(posi-reali);
                }
        else if(reali>=(posi)){
            relativei=(reali-posi);
        } 
        return relativei;
    
    }
   private int relativeJ(int realj){

        int posj=getFXCartePosJ();
        int relativej=-1;

        if(realj<posj){
                    relativej=-(posj-realj);
                }
        else if(realj>=(posj)){
            relativej=(realj-posj);
        } 
        return relativej;
    
    }
   


}
