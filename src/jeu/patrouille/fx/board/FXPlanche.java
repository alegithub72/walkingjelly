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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.menu.BandageItem;
import jeu.patrouille.fx.menu.FeuItem;
import jeu.patrouille.fx.menu.LoadMagazineItem;
import jeu.patrouille.fx.menu.MenuItemButton;
import jeu.patrouille.fx.menu.RunItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.EndTurnButtonEventHandler;
import jeu.patrouille.fx.menu.eventhandler.EndTurnEventHandler;
import jeu.patrouille.fx.menu.eventhandler.MiniActionClickEventHandler;
import jeu.patrouille.fx.menu.eventhandler.MiniActionExitEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXPlanche extends Application {


    Path p;
    Group rootSubScene;
    Group rootScene;
    Canvas droitCanvasBar; 
    SubScene subScene;
    FXCarte fxCarte;
    FXMenuItemsDossier menu;
    Label message;
    List<Sprite> fxActionsPoolSelectionee;
    FXInfoPanel infPl;
    Font fontTitle;
    FXSoldatEquipement fxequip;
    //public ProgressIndicator bar;
    FXPatrouilleSprite endButton;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        rootSubScene=new Group();
        rootScene=new Group();
       
        InputStream i = ClassLoader.getSystemResourceAsStream("Lintsec Regular.ttf");
        fontTitle =Font.loadFont(i, 18);
        fxActionsPoolSelectionee = new ArrayList<>();
        Scene sc = new Scene(rootScene);

        
               

        //ImageView test = new ImageView(new Image("menuItem.png"));

        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println(" -->"+primaryStage.getScene().getWidth()+","+primaryStage.getScene().getHeight());
            }
        });

                
        primaryStage.setFullScreen(true);
        
  
        //Scene sc = new Scene(borderPan);

        //primaryStage.setFullScreen(true);
        primaryStage.setScene(sc);
       // primaryStage.sizeToScene();
       primaryStage.show();


        fxCarte = new FXCarte(this,primaryStage.getScene().getWidth(),primaryStage.getScene().getHeight());
        menu=new FXMenuItemsDossier(this);
        fxCarte.initFXCarte();
        rootSubScene.getChildren().add(fxCarte);
        subScene=new SubScene(rootSubScene, FXCarte.PIXEL_SCROLL_AREA_W, FXCarte.PIXEL_SCROLL_AREA_H);
        rootScene.getChildren().add(subScene);                
        ///SnapshotParameters params = new SnapshotParameters();
        //params.setFill(Color.TRANSPARENT);
              buildAngleTopLeftBar();
                buildMessaggeBar();              

    }

    public Font getFontTitle() {
        return fontTitle;
    }


    public void imprimerFXPLInfo(Soldat s) {
        infPl.setVisible(true);
        infPl.imprimerInfo(s);

        
    }

    public void buildFXSoldatEquipement(Soldat s){
        rootScene.getChildren().remove(fxequip);
       // fxequip=new FXSoldatEquipement(this);
        fxequip=new FXSoldatEquipement(this);        
        rootScene.getChildren().add(fxequip);
        fxequip.setTranslateX(FXCarte.PIXEL_SCROLL_AREA_W+16);
        fxequip.setTranslateY(325);
        fxequip.toFront();
        fxequip.buildFXEquipment(s);
               // rootDroitBarGroup.getChildren().add(fxequip);

    }
    void buildMessaggeBar() throws IOException {
        
        //canvasBar = new Canvas( FXCarte.PIXEL_SCROLL_AREA_W+FXCarte.DROIT_BAR_W, FXCarte.BAR_H);
        //rootBarGroup = new Group();
        //rootBarGroup.getChildren().add(canvasBar);
        //rootScene.getChildren().add(canvasBar);
        //canvasBar.setTranslateX(0 );
        //canvasBar.setTranslateY(FXCarte.PIXEL_SCROLL_AREA_H);
        //GraphicsContext gc = canvasBar.getGraphicsContext2D();
        //gc.setFill(Color.TRANSPARENT);
        //gc.fillRect(FXCarte.PIXEL_SCROLL_AREA_W , FXCarte.PIXEL_SCROLL_AREA_H,FXCarte.PIXEL_SCROLL_AREA_W+ FXCarte.DROIT_BAR_W,FXCarte.PIXEL_SCROLL_AREA_H+ FXCarte.BAR_H);
        //gc.drawImage(new Image("barConsole.png"), 0, 0);
        
        
        //gc.setFont(fontTitle);
        //gc.setFill(Color.BISQUE);
        //gc.fillText("Message:", 10, 18);
        
        message = new Label(" CIAOOO");
        message.setTextFill(Color.WHITE);
        message.setLayoutX(0);
        message.setLayoutY(FXCarte.PIXEL_SCROLL_AREA_H-20);
        message.setStyle("");


        //borderPan.setBottom(rootBarGroup);

        endButton = new FXPatrouilleSprite(120, 50, null, null);
        endButton.buildFrameImages(new Image( "endturn.png"));
        //endButton.setLayoutX(880);
        //endButton.setLayoutY(0);
        EventHandler e=new EndTurnButtonEventHandler(fxCarte, endButton);
        endButton.setOnMousePressed(e);
        endButton.setOnMouseReleased(e);
        endButton.setOnMouseClicked(new EndTurnEventHandler(this));
        endButton.setLayoutX(FXCarte.PIXEL_SCROLL_AREA_W-endButton.getW());
        endButton.setLayoutY(FXCarte.PIXEL_SCROLL_AREA_H-endButton.getH());
        endButton.toFront();
        rootScene.getChildren().add(endButton);
        rootScene.getChildren().add(message);
    }
    public void endTurn(){
        infPl.resetInfo();
        rootScene.getChildren().remove(fxequip);   
        infPl.setVisible(false);
        suprimerActionVisualization();
        fxCarte.playTurn();
    }
    void buildTop() {


//borderPan.setTop(topPan);      
        //borderPan.setTop(new Label("STATUS DEL SOLDAT"));      
        //topPan.getGraphicsContext2D().setFill(Color.DARKKHAKI);
        //topPan.getGraphicsContext2D().fillRect(0, 0, w, TOP_H);

    }

    void buildAngleTopLeftBar() {

        infPl=new FXInfoPanel(droitCanvasBar,this.fontTitle,0, 0);
        infPl.buildInfoPanel();
        rootScene.getChildren().add(infPl);
    }
    public void addDoritBarGroup(Node n){
        rootScene.getChildren().add(n);
    } 
    public void sendMessageToPlayer(String text,Paint color) {
        message.setTextFill(color);
        if(text.length()>20) {
            String t2=text.substring(20);
            String t1=text.substring(0, 20)+"\n";
            message.setText(t1+t2);
        }else      message.setText(text);
       
   

    }
    void sendMessageToPlayer(String text) {
        message.setTextFill(Color.WHITE);
        message.setEffect(new DropShadow(30, Color.WHITE));
        message.setText(text);

    }
    void visualizeActionBarActual() {

        int k = fxCarte.getHelper().getSeletctionee().actionSize() - 1;
        visualizeActionBar(fxCarte.getHelper().getAct(), k);

    }

    void visualizeActionBar(BaseAction act, int k) {
        //TODO compliche trought scroll panel !!!!!!!
        MenuItemButton spAct=null;
        if (null != act.getType()) switch (act.getType()) {
            case MARCHE:
                spAct = new WalkItem();
                break;
            case FEU_VISER:
            case FEU:
                spAct=new FeuItem();
                break;
            case BANDAGE:
                spAct=new BandageItem();
                break;
            case ARME_RECHARGE:
                spAct=new LoadMagazineItem();
                break;
            case COURS:
                spAct=new RunItem();
            default:
                break;
        }
        boolean add = rootScene.getChildren().add(spAct);
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
            rootScene.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }






    
    
    


 




    

    
    public static void main(String[] args) {

        launch(args);
        
    }    
}


  