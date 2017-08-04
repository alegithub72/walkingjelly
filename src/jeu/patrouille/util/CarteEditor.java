/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.util;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.board.FXCarte;
/**
 *
 * @author appleale
 */
public class CarteEditor extends Application {
    
    Carte c;
    Canvas canvas;
    Canvas canvas2;
    Canvas console;
    TextArea mapText;
    Group gcanvas;
    Group gconsole;
    int posX=0,posY=0;
    boolean vis=true;
    
    public void start(Stage primaryStage) throws Exception{
        gconsole=new Group();
        gcanvas=new Group();
        c=new Carte("src/mapDesert.txt");
 
      
        canvas=new Canvas(2560, 1600);
        canvas2=new Canvas(800, 600);
        canvas2.setVisible(false);
        console=new Canvas(200,400);

        Font f=new Font(-1);
        ScrollPane sp=new ScrollPane();
        sp.setPrefViewportHeight(500);
        sp.setPrefViewportWidth(600);

       GridPane grid=new GridPane();

 
       gcanvas.getChildren().add(canvas);
       gcanvas.getChildren().add(canvas2);
       gconsole.getChildren().add(console);

       
       grid.addRow(1,gcanvas);
       grid.addRow(2,gconsole);
       //tot.getChildren().add(gconsole);
        Button up=new Button("Up");
        Button dw=new Button("Dw");
        dw.setTranslateY(30);
        gconsole.getChildren().add(up);
        gconsole.getChildren().add(dw);
        up.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                   if(vis)  {
                       posY++;
                        buildCarte(posX, posY, posX+50, posY+50,canvas2);
                        canvas.setVisible(false);
                        canvas2.setVisible(true);
                   }
                   else{
                       posY++;
                       buildCarte(posX, posY, posX+50, posY+50,canvas);
                        canvas2.setVisible(false);  
                        canvas.setVisible(true);
                                            
                   }

                   vis=!vis;
                event.consume();
            }
        });
        
        
        sp.setContent(canvas);
       // sc.setPrefSize(500, 600);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
       
//        mapText.setPrefSize(800, 400);
        
       Scene s=new Scene(grid);
       primaryStage.setMinHeight(1000);
       primaryStage.setMinWidth(1000);
       
       primaryStage.setScene(s);
       primaryStage.sizeToScene();
        //primaryStage.setMinHeight(1024);
        //primaryStage.setMinWidth(2048);
        
        buildCarte(0,0,50,50,canvas);
        primaryStage.show();
    
    }
    
    void buildCarte(int w,int h,int w1,int h1,Canvas canv){
        String a="";
        canv.getGraphicsContext2D().fillRect(0, 0,600,
                800);
         Image img=new Image("tree.png");
         Image imgB=new Image("desertBorder.png");
        ImageView s=new ImageView("enemy.png");
        ImageView e=new ImageView("soldier.png");
        for(int n=0;n<(600/FXCarte.TILE_SIZE);n++){
            for(int m=0;m<(800/FXCarte.TILE_SIZE);m++){
                Terrain tile=c.getPointCarte(n+posY, m+posX);
                int x0=m*FXCarte.TILE_SIZE,y0=n*FXCarte.TILE_SIZE;
                canv.getGraphicsContext2D().drawImage(tile.getImg(),x0,y0);
               //else if(tile.getType()==) canv.getGraphicsContext2D().drawImage(imgB,x0,y0);
              /** Object ob = c.getPointCarte(n+posY, m+posX).getPiece();
                if(ob!=null) {
                    e.setVisible(false);
                    gcanvas.getChildren().add(e);
                    e.setX( m*PointCarte.TILE_SIZE);e.setY(n*PointCarte.TILE_SIZE);
                }*/
            }
            //a=a+"\n";
            
        }
        javafx.scene.text.Text t=new Text(30,30,"H");

        e.setX(80);
        ;
        //console.getGraphicsContext2D().setFill(Color.AQUA);
        //console.getGraphicsContext2D().fillRect(0, 0,PointCarte.TILE_SIZE*Carte.CARTE_SIZE,
         //        PointCarte.TILE_SIZE*Carte.CARTE_SIZE);
        //s.setX(30);
        //s.setY(30);
        gcanvas.getChildren().add(s);
        gcanvas.getChildren().add(e);
        //mapText.setText(a);
        //canvas.setVisible(true);
        //console.getGraphicsContext2D().fillRect(0, 0, 40, 40);
            
    }
    
    public static void main(String[] args) {

        launch(args);

    }    
}
