/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.board;

import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    Font font;    
    double x,y;
    Border b;
    FXInfoPanel(FXPlanche fxpl,double x,double y){
        b=getBorder();
        nom=new Label();
        tdLabel=new Label();
        classment=new Label();
        this.x=x;
        this.y=y;
        font=Font.font(fxpl.getFontTitle().getName(), 12);
        buildInfoPanel();
    
    }
    void buildInfoPanel(){
        
        nom.setTextFill(Color.WHITE);
        nom.setFont(font);
        nom.relocate(x, y);
        nom.setBorder(b);
        
        classment.setTextFill(Color.WHITE);
        classment.setFont(font);
        classment.relocate(x, y+62);
        classment.setBorder(b);
        
        tdLabel.setTextFill(Color.WHITE);
        tdLabel.setFont(font);
        tdLabel.relocate(x+40, y+105);
        tdLabel.setBorder(b);

        this.getChildren().add(nom);
        this.getChildren().add(tdLabel);
        this.getChildren().add(classment);
    
    }
    
    
     Border getBorder(){
         
         CornerRadii cr = new CornerRadii(20);
         BorderStroke bs = new BorderStroke(Color.WHITE,
                 BorderStrokeStyle.SOLID,
                 cr,
                 BorderWidths.DEFAULT);
         Border b=new Border(bs);
         return b;
    }
    
    public void imprimerInfo(Soldat  s){
        nom.setText(s.toStringSimple());
        tdLabel.setText (s.getActionPoint()+"");
        String clText="Soldat";
        switch (s.getClassement()) {
            case Soldat.CLASS_SGT:
                clText="Sergent";
                break;
            case Soldat.CLASS_SGT_MJR:
                clText="Sergent-major";
                break;
            case Soldat.CLASS_TEN:
                clText="Liutenant";
                break;
            default:
                break;
        }
             
        classment.setText(clText);
    
    }
    
}
