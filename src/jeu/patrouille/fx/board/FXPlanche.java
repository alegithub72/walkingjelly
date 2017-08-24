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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.menu.FeuItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.EndTurnButtonEventHandler;
import jeu.patrouille.fx.menu.eventhandler.EndTurnEventHandler;
import jeu.patrouille.fx.menu.eventhandler.MiniActionClickEventHandler;
import jeu.patrouille.fx.menu.eventhandler.MiniActionExitEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.armes.FXSoldatEquipement;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXPlanche extends Application {


    Path p;
    BorderPane borderPan;
    Canvas topPan;
    Canvas canvasBar;
    Group root;
    Canvas droitCanvasBar; 
    boolean scroolEnd = true;
    FXCarte fxCarte;
    Label message;
    List<Sprite> fxActionsPoolSelectionee;
    FXInfoPanel infPl;
    Font fontTitle;
    FXSoldatEquipement fxequip;
    @Override
    public void start(Stage primaryStage) throws Exception {

        borderPan = new BorderPane();
        InputStream i = ClassLoader.getSystemResourceAsStream("Lintsec Regular.ttf");
        fontTitle =Font.loadFont(i, 18);
        fxActionsPoolSelectionee = new ArrayList<>();
       
        fxCarte = new FXCarte(this);
        fxCarte.initFXCarte();
        borderPan.setCenter(fxCarte);
               

        //ImageView test = new ImageView(new Image("menuItem.png"));
        root=new Group();
        primaryStage.setResizable(false);
                buildBar();
        buildDroitBar();
        //Scene sc = new Scene(borderPan);
        root.getChildren().add(fxCarte);
        
        Scene sc = new Scene(root,(FXCarte.PIXEL_SCROLL_AREA_W+FXCarte.DROIT_BAR_W),
       (FXCarte.PIXEL_SCROLL_AREA_H+FXCarte.BAR_H));
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        ///SnapshotParameters params = new SnapshotParameters();
        //params.setFill(Color.TRANSPARENT);

    }

    public Font getFontTitle() {
        return fontTitle;
    }


    public void imprimerFXPLInfo(Soldat s) {
        infPl.imprimerInfo(s);
    }

    public void buildFXSoldatEquipement(Soldat s){
        root.getChildren().remove(fxequip);
       // fxequip=new FXSoldatEquipement(this);
        fxequip=new FXSoldatEquipement(this);        
        root.getChildren().add(fxequip);
        fxequip.setTranslateX(FXCarte.PIXEL_SCROLL_AREA_W+16);
        fxequip.setTranslateY(325);
        fxequip.toFront();
        fxequip.buildFXEquipment(s);
       // rootDroitBarGroup.getChildren().add(fxequip);

    }
    void buildBar() throws IOException {
        
        canvasBar = new Canvas( FXCarte.PIXEL_SCROLL_AREA_W+FXCarte.DROIT_BAR_W, FXCarte.BAR_H);
        //rootBarGroup = new Group();
        //rootBarGroup.getChildren().add(canvasBar);
        root.getChildren().add(canvasBar);
        canvasBar.setTranslateX(0 );
        canvasBar.setTranslateY(FXCarte.PIXEL_SCROLL_AREA_H);
        GraphicsContext gc = canvasBar.getGraphicsContext2D();
        //gc.setFill(Color.TRANSPARENT);
        //gc.fillRect(FXCarte.PIXEL_SCROLL_AREA_W , FXCarte.PIXEL_SCROLL_AREA_H,FXCarte.PIXEL_SCROLL_AREA_W+ FXCarte.DROIT_BAR_W,FXCarte.PIXEL_SCROLL_AREA_H+ FXCarte.BAR_H);
        gc.drawImage(new Image("barConsole.png"), 0, 0);
        
        
        //gc.setFont(fontTitle);
        //gc.setFill(Color.BISQUE);
        //gc.fillText("Message:", 10, 18);
        
        message = new Label("");
        message.setTextFill(Color.WHITE);
        message.setFont(fontTitle);
        message.relocate(10, 5);
        message.setStyle("");


        //borderPan.setBottom(rootBarGroup);

        FXPatrouilleSprite endButton = new FXPatrouilleSprite(120, 50, null, null);
        endButton.buildFrameImages(new Image( "endturn.png"));
        //endButton.setLayoutX(880);
        //endButton.setLayoutY(0);
        EventHandler e=new EndTurnButtonEventHandler(fxCarte, endButton);
        endButton.setOnMousePressed(e);
        endButton.setOnMouseReleased(e);
        endButton.setOnMouseClicked(new EndTurnEventHandler(this));
        endButton.setTranslateX(FXCarte.PIXEL_SCROLL_AREA_W);
        endButton.setTranslateY(FXCarte.PIXEL_SCROLL_AREA_H);
        endButton.toFront();
        root.getChildren().add(endButton);
        root.getChildren().add(message);
    }
    public void endTurn(){
        suprimerActionVisualization(); 
        fxCarte.playTurn();
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
        //rootDroitBarGroup = new Group();
                   
        droitCanvasBar = new Canvas(FXCarte.DROIT_BAR_W, FXCarte.PIXEL_SCROLL_AREA_H);
        GraphicsContext gc = droitCanvasBar.getGraphicsContext2D();
        droitCanvasBar.setTranslateX(FXCarte.PIXEL_SCROLL_AREA_W);
        droitCanvasBar.setTranslateY(0);
        Image img = new Image("rightBar.png");
        //gc.setFill(Color.rgb(64, 128, 0, 1));
        gc.setFill(Color.TRANSPARENT);

      

        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        gc.drawImage(img, 0, 0);
        //gc.setFill(Color.rgb(128,0,255)); grape
        gc.setFill(Color.FLORALWHITE); 
        gc.setFont(fontTitle);
        gc.setEffect(dropShadow);
       // borderPan.setRight(rootDroitBarGroup);
        
        gc.fillText("Nom:", 2, 26);
        gc.fillText("Classment:", 2, 78+10);
        gc.fillText("TEMP:", 2, 130+60);
        gc.fillText("EQUIPMENT:", 11, 315);
        gc.fillText("Sante:",2,130+30);
        gc.setEffect(null);
        infPl=new FXInfoPanel(this, FXCarte.PIXEL_SCROLL_AREA_W+10, 40);
        infPl.buildInfoPanel();

        root.getChildren().add(droitCanvasBar);
        root.getChildren().add(infPl);

      
        //BorderWidths bw = new BorderWidths(3);

    }
    public void addDoritBarGroup(Node n){
        root.getChildren().add(n);
    } 
    void sendMessageToPlayer(String text) {
        message.setText(text);

    }

    void visualizeActionBarActual() {

        int k = fxCarte.getHelper().getSeletctionee().actionSize() - 1;
        visualizeActionBar(fxCarte.getHelper().getAct(), k);

    }

    void visualizeActionBar(BaseAction act, int k) {
        //TODO compliche trought scroll panel !!!!!!!
        Sprite spAct=null;
        if (act.getType() == ActionType.MARCHE) {
            spAct = new WalkItem(null);

        }if(act.getType()==ActionType.FEU){
            spAct=new FeuItem(null);
        }
        boolean add = root.getChildren().add(spAct);
        spAct.setScaleX(0.5);
        spAct.setScaleY(0.5);
        spAct.setFrame(0);
        spAct.toFront();
        spAct.setOnMouseClicked(new MiniActionClickEventHandler(act, fxCarte));
        spAct.setOnMouseExited(new MiniActionExitEventHandler(act, fxCarte));
        spAct.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(spAct));
        spAct.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(spAct));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        spAct.setEffect(dropShadow);
        spAct.setTranslateX(500 + (k * (spAct.getW() / 2)));
        if (k < 10) {
            spAct.setTranslateY(FXCarte.PIXEL_SCROLL_AREA_H -23);
        } else {
            spAct.setTranslateY(FXCarte.PIXEL_SCROLL_AREA_H +(spAct.getH() / 2));
        }
        this.fxActionsPoolSelectionee.add(spAct);        

    }



    void suprimerActionVisualization() {

        for (int k = 0; k < fxActionsPoolSelectionee.size(); k++) {
            root.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }






    
    
    


 




    

    
    public static void main(String[] args) {

        launch(args);
        
    }    
}


  