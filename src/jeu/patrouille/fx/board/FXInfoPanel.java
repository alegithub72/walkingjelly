/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.board;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jeu.patrouille.coeur.pieces.Soldat;
import javafx.scene.layout.Border;

/**
 *
 * @author appleale
 */
public class FXInfoPanel extends Parent{
    Label nom;
    Label tdLabel;
    Label classment;
    Label sante;
    Font font;    
    double x,y;
    Border bo;
    Background bk;
    Canvas canv;
    boolean bkreset;
    FXInfoPanel(Canvas canv,Font font,double x,double y){

        nom=new Label();
        tdLabel=new Label();
        classment=new Label();
        sante=new Label();
        this.x=x;
        this.y=y;
        this.font=Font.font(font.getName(), 12);
        this.canv=canv;
        bkreset=true;

    
    }
    void buildInfoPanel(){
        if(bkreset) {
            drawTitle();
            bkreset=false;
        }
        Color textColor=Color.LAWNGREEN;
        
        bo=getBorder();
        bk=getBlackBackgound();
        nom.setTextFill(textColor);
        nom.setFont(font);
        nom.relocate(x, y);
        nom.setBackground(bk);
        nom.setBorder(bo);
        
        
        classment.setTextFill(textColor);
        classment.setFont(font);
        classment.relocate(x, y+62);
        classment.setBorder(bo);
        classment.setBackground(bk);
        
        tdLabel.setTextFill(textColor);
        tdLabel.setFont(font);
        tdLabel.relocate(x+80, y+155);
        tdLabel.setBorder(bo);
        tdLabel.setBackground(bk);
        
        sante.setTextFill(textColor);
        sante.setFont(font);
        sante.setBorder(bo);
        sante.setBackground(bk);
        sante.relocate(x, y+130);
        this.setVisible(false);
        this.getChildren().add(sante);
        this.getChildren().add(nom);
        this.getChildren().add(tdLabel);
        this.getChildren().add(classment);
    
    }
    
    
     Border getBorder(){
         BorderWidths w=new BorderWidths(2);
         CornerRadii cr = new CornerRadii(20);
         BorderStroke bs = new BorderStroke(Color.LAWNGREEN ,
                 BorderStrokeStyle.SOLID,
                 cr,
                 w);
         Border b=new Border(bs);
         
         return b;
    }
    Background getBlackBackgound(){
             CornerRadii cr = new CornerRadii(20);
        BackgroundFill fills=new BackgroundFill(Color.BLACK, 
                cr, Insets.EMPTY);
        
        Background bk=new Background(fills);
        return bk;
    }
    public void imprimerInfo(Soldat  s){
        if(bkreset) {
            drawTitle();
            bkreset=false;
        }
        nom.setText(s.toStringSimple());
        tdLabel.setText (s.getTempDisponible()+"");
        String clText=s.getClassement().name();
        classment.setText(clText);
        sante.setText(s.getSante()+" "+s.getStatu().mes+"");
    
    }
    void drawTitle(){
         GraphicsContext gc= canv.getGraphicsContext2D();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setSpread(1);
        dropShadow.setColor(Color.BLACK);          
        Image img = new Image("rightBar.png");
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        gc.drawImage(img, 0, 0);        
        //gc.setFill(Color.rgb(128,0,255)); grape
        gc.setFill(Color.FLORALWHITE); 
        gc.setFont(font);
        gc.setEffect(dropShadow);
       // borderPan.setRight(rootDroitBarGroup);
        
        gc.fillText("Nom:", 2, 26);
        gc.fillText("Classment:", 2, 78+10);
        gc.fillText("Temp:", 2, 130+85);
        gc.fillText("Equipment:", 11, 315);
        gc.fillText("Sante:",2,130+30);    
    
    
    }
    
    
    
    public void resetInfo(){
        GraphicsContext gc= canv.getGraphicsContext2D();
        Image img = new Image("rightBar.png");
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, FXCarte.DROIT_BAR_W,
                FXCarte.PIXEL_SCROLL_AREA_H);
        gc.drawImage(img, 0, 0);
        bkreset=true;
        nom.setText("");
        tdLabel.setText("");
        classment.setText("");
        sante.setText("");
    
    }
}
