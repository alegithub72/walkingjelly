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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXPlanche extends Application {

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

    public void addFxActionPoolSelectionee(Sprite sp) {
        fxActionsPoolSelectionee.add(sp);
    }

    public Group getRootBarGroup() {
        return rootBarGroup;
    }

    public FXCarte getFXCarte() {
        return fxCarte;
    }
   
    Font font ;
    Font fontTitle ;
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
        borderPan.setBackground(  bc);
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

    public void closeMenuItems() {
        this.fxCarte.closeMenuItems();

    }

    public synchronized void buildMenuItem(FXSoldat s) {
        this.fxCarte.buildMenuItem(s);

    }

    public static void main(String[] args) {

        launch(args);
    }

    void buildBar() throws IOException {
        canvasBar = new Canvas(FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);

        GraphicsContext gc = canvasBar.getGraphicsContext2D();
        //gc.setFill(Color.TRANSPARENT);
        //gc.fillRect(0, 0, FXCarte.PIXEL_SCROLL_AREA_W + FXCarte.DROIT_BAR_W, FXCarte.BAR_H);        
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
       // gc.drawImage(new Image("barConsole2.png"), 0, 0);
        borderPan.setBottom(rootBarGroup);
        gc.setFont(fontTitle);
        gc.setFill(Color.BISQUE);
        gc.fillText("Message:", 10, 18);

        BorderWidths bw = new BorderWidths(3);
        CornerRadii cr = new CornerRadii(2);

        Border b = new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, cr, bw));

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
        Image img = new Image("backCamo4.png");
        //gc.setFill(Color.rgb(64, 128, 0, 1));
        gc.setFill(Color.TRANSPARENT);
        
        Text t=new Text();
        
        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        //gc.drawImage(img, 0, 0);
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

    public void visualizeActionBar(int actionType, int k) {
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
        if (this.fxCarte.getFXSoldatSelectionee() != null) {
            s = this.fxCarte.getFXSoldatSelectionee().getSoldat();
        }
        profile.setText(s.toString());
    }

    public void suprimerActionVisualization() {

        for (int k = 0; k < fxActionsPoolSelectionee.size(); k++) {
            rootBarGroup.getChildren().remove(fxActionsPoolSelectionee.get(k));
        }
    }
}
