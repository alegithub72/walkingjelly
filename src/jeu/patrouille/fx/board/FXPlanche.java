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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.EndTurnEventHandler;
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
    Group rootBarGroup;
    Group rootDroitBarGroup;
    Canvas droitCanvasBar;
    boolean scroolEnd = true;
    FXCarte fxCarte;
    Label message;
    List<Sprite> fxActionsPoolSelectionee;
    FXInfoPanel infPl;
    Font fontTitle;
    @Override
    public void start(Stage primaryStage) throws Exception {

        borderPan = new BorderPane();
        InputStream i = ClassLoader.getSystemResourceAsStream("Lintsec Regular.ttf");
        fontTitle =Font.loadFont(i, 18);
        fxActionsPoolSelectionee = new ArrayList<>();
        //Image img = new Image("bc.jpeg");
        //    BackgroundImage bckImg = new BackgroundImage(img, BackgroundRepeat.REPEAT,
        //            BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //    Background bc = new Background(bckImg);
        //borderPan.setBackground(  bc);
        fxCarte = new FXCarte(this);
        fxCarte.initFXCarte();
        borderPan.setCenter(fxCarte);
        buildBar();
        buildDroitBar();
        //ImageView test = new ImageView(new Image("menuItem.png"));

        primaryStage.setResizable(false);
        Scene sc = new Scene(borderPan);
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

        Sprite endButton = new Sprite(120, 50, 120, 50,null, this);
        endButton.buildFrameImages(new Image( "endturn.png"));
        endButton.setLayoutX(880);
        endButton.setLayoutY(0);
        EventHandler e=new EndTurnEventHandler(fxCarte, endButton);
        endButton.setOnMousePressed(e);
        endButton.setOnMouseReleased(e);
        endButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            System.out.println(event);                
            if(event.getButton()==MouseButton.PRIMARY 
                    && event.getEventType()==MouseEvent.MOUSE_CLICKED){
                
               // Platform.runLater(fxCarte.getMj());  
                fxCarte.playTurn();
                
            }
                   event.consume();
            }
        
        });
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
            rootBarGroup.getChildren().add(spAct);
            spAct.setScaleX(0.5);
            spAct.setScaleY(0.5);
            spAct.setFrame(1);
            spAct.toFront();

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            spAct.setEffect(dropShadow);
            spAct.setLayoutX(500 + (k * (spAct.getW() / 2)));
            if (k < 10) {
                spAct.setLayoutY(-23);
            } else {
                spAct.setLayoutY((spAct.getH() / 2));
            }
            this.fxActionsPoolSelectionee.add(spAct);
        }

    }



    void suprimerActionVisualization() {

        for (int k = 0; k < fxActionsPoolSelectionee.size(); k++) {
            rootBarGroup.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }






    
    
    


 




    

    
    public static void main(String[] args) {

        launch(args);
        
    }    
}


  