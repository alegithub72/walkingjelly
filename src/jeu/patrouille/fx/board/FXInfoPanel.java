/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.board;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    
    FXInfoPanel(FXPlanche fxpl,double x,double y){

        nom=new Label();
        tdLabel=new Label();
        classment=new Label();
        sante=new Label();
        this.x=x;
        this.y=y;
        font=Font.font(fxpl.getFontTitle().getName(), 12);
       
    
    }
    void buildInfoPanel(){
        
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
        nom.setText(s.toStringSimple());
        tdLabel.setText (s.getTempDisponible()+"");
        String clText=s.getClassement().name();
        classment.setText(clText);
        sante.setText(s.getSante()+" "+s.getStatu().mes+"");
    
    }
    public void resetInfo(){
        nom.setText("");
        tdLabel.setText("");
        classment.setText("");
        sante.setText("");
    
    }
}
