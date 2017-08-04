/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.coeur.actions.*;
import jeu.patrouille.fx.sprite.Sprite;
/**
 *
 * @author appleale
 */
public class ActionRangeDisplayHandler implements EventHandler<MouseEvent>{
    FXCarte fxcarte;
    boolean outOfScroll=false;
    public ActionRangeDisplayHandler(FXCarte fxcarte){
        this.fxcarte=fxcarte;
    }
    @Override
    public void handle(MouseEvent event) {
         if (!fxcarte.scrollCanvas(event.getSceneX(), event.getSceneY())) {;
            Soldat s=fxcarte.getFXSoldatSelectionee().getSoldat();
            fxcarte.getRootGroup().getChildren().remove(fxcarte.getDisplayRange());
            int mapLastActI = mapLastI();
            int mapLastActJ = mapLastJ();
            double mapLastActy = mapLastActI * FXCarte.TILE_SIZE;
            double mapLastActx = mapLastActJ * FXCarte.TILE_SIZE;
            System.out.println(" (mapLastActI,mapLastActJ)=" + mapLastActI + "," + mapLastActJ);
            int posi = fxcarte.getPosI();
            int posj = fxcarte.getPosJ();
            System.out.println(" (posi,posj)=" + fxcarte.getPosI() + "," + fxcarte.getPosJ());
            //TODO usare scrollmousej, and scrollMouei per verificare la validita del percorso
            int scrollMousej = (int) (event.getSceneX() / FXCarte.TILE_SIZE);
            int scrollMousei = (int) (event.getSceneY() / FXCarte.TILE_SIZE);
            
            
            double mouseMapx = (scrollMousej + posj) * FXCarte.TILE_SIZE;
            double mouseMapy = (scrollMousei + posi) * FXCarte.TILE_SIZE;

            double circleX = Math.pow(mouseMapx - mapLastActx, 2);
            double circleY = Math.pow(mouseMapy - mapLastActy, 2);
            double r = Math.sqrt(circleX + circleY);


            double relativex = relativeJ(mapLastActJ) * FXCarte.TILE_SIZE+25;
            double relativey = relativeI(mapLastActI) * FXCarte.TILE_SIZE+25;
            System.out.println("relativex,relativey " + relativex + "," + relativey);
            fxcarte.activeCanvas().getGraphicsContext2D().setFill(Color.BLACK);
             fxcarte.activeCanvas().getGraphicsContext2D().beginPath();
             //LineTo line  =new LineTo((scrollMousej * FXCarte.TILE_SIZE) + 25,(scrollMousei * FXCarte.TILE_SIZE) + 25);
             //Path path=new Path(new MoveTo(relativex, relativey),line);
             // path.setOpacity(0.4);
            //fxcarte.activeCanvas().getGraphicsContext2D().lineTo(100, 100);

            //fxcarte.getRootGroup().getChildren().add(path);
            //fxcarte.setDisplayRange(path);
            fxcarte.activeCanvas().getGraphicsContext2D().strokeLine(relativex, relativey, 
                    (scrollMousej * FXCarte.TILE_SIZE) + 25,(scrollMousei * FXCarte.TILE_SIZE) + 25);
            //fxcarte.refreshCarte();
            Sprite sp = new Sprite(50, 50, 50,50, "rangeArrow.png", null);
           // sp.setRotate(90);
            //sp.setScaleY(0.5);
            //sp.setScaleX(0.5);
            double arrowPosx=(scrollMousej * FXCarte.TILE_SIZE);
            double arrowPosY=(scrollMousei * FXCarte.TILE_SIZE) ;
            if(arrowPosx>0 && arrowPosx<FXCarte.PIXEL_SCROLL_AREA_W) 
                sp.setX( arrowPosx);
            if (arrowPosY >0 && arrowPosY<FXCarte.PIXEL_SCROLL_AREA_H)
                sp.setY(arrowPosY);
                    
            fxcarte.setDisplayRange(sp);
            
            
            //TODO dovresti verificare tutte le action move che non superano 10..e
            //diminuire il range
            if (r <=  (s.getActionPoint()*50) && 
                    r>0 && s.getActionPoint()>0
                    //TODO verificare la route delle tile
                    ) {

                fxcarte.setCommanNotvalid(false);
                fxcarte.getRootGroup().getChildren().add(sp);
                fxcarte.setCursor(Cursor.HAND);
   
                System.out.println("raggio---->" + r);
            } else {
                fxcarte.setCommanNotvalid(true);
                ImageCursor imgcr=new ImageCursor(new Image("forbiddenCursor.png"));
                fxcarte.setCursor(imgcr);
                System.out.println("fuori raggio---->" + r);
            }
        }
    }
   int  mapLastJ(){
        Soldat s = fxcarte.getFXSoldatSelectionee().getSoldat();
        int actionsize = s.actionSize();

        BaseAction lastAct = null;
        int j = -1;
        if (actionsize >0) {
            lastAct = s.lastAction();
            System.out.println("last action =" + lastAct);
            if (lastAct.getJ1() >= 0) {
                j = lastAct.getJ1();//TODO le azione che non muovono
            } else {
                j = lastAct.getJ0();
            }
        } else {
            j = fxcarte.getFXSoldatSelectionee().getSoldat().getJ();
        }
   


        return j;
    }

    public int mapLastI() {
        Soldat s = fxcarte.getFXSoldatSelectionee().getSoldat();
        int actionsize = s.actionSize();

        BaseAction lastAct = null;
        int i = -1;
        if (actionsize > 0) {
            lastAct = s.nextAction(actionsize - 1);
            if (lastAct.getI1() >= 0) {
                i = lastAct.getI1();//TODO le azione che non muovono
            } else {
                i = s.getI();
            }
        } else {
            i = fxcarte.getFXSoldatSelectionee().getSoldat().getI();
        }

       

        return i;
   }   
    
    int relativeI(int reali){
        int relativei=-1;
        int posi=fxcarte.getPosI();
        if(reali<posi){
                    relativei=-(posi-reali);
                }
        else if(reali>=(posi)){
            relativei=(reali-posi);
        } 
        return relativei;
    
    }
    int relativeJ(int realj){

        int posj=fxcarte.getPosJ();
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
