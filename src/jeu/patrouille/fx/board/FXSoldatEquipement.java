/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.board;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;
import jeu.patrouille.fx.pieces.armes.FXEquipment;
import jeu.patrouille.fx.pieces.armes.FXMagazine;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public class FXSoldatEquipement extends Parent{
    FXPlanche fxpl;
    FXPatrouilleSprite fxarmes[];
    Label numShot;
  public   FXSoldatEquipement(FXPlanche fxpl){
      super();      
      this.fxpl=fxpl;
      numShot=new Label();
      numShot.setTextFill(Color.WHITE);
      numShot.setFont(Font.font(15));

  }
  public void buildFXEquipment(Soldat s){
      try{
      GeneriqueEquipment[] list = s.getEquipment();

      fxarmes = new FXPatrouilleSprite[s.getEquipmentChiffre()];
      //DropShadow ds=new DropShadow(5, Color.DARKOLIVEGREEN);
      //ds.setOffsetX(5);
      //ds.setOffsetY(5);  
      int k=0,n=0;
      for (int i = 0; i < list.length; i++) {
              GeneriqueEquipment arm = list[i];
              fxarmes[n] = FXEquipment.createIstance(arm.getModel());
              double y0=createFXEquipment(fxarmes[n], n);
              
              GeneriqueArme a = ((GeneriqueArme) arm);
              if(s.getArmeUtilise() !=null && a==s.getArmeUtilise()) {
                  ((FXEquipment)fxarmes[n]).addUsed(fxarmes[n].getW());
                  //numShot.setLabelFor(fxarmes[n]);
                  numShot.setTranslateX(fxarmes[n].getW()-30);
                  numShot.setTranslateY(y0+fxarmes[n].getH()-30);
                  numShot.setText(a.shotRemain()+"");
 
                  getChildren().add(numShot);  
                  Label l=new Label(a.getArmeFeuMode().toString());
                  l.setTextFill(Color.WHITE);
                  //l.setLabelFor(fxarmes[n]);
                  l.setTranslateX(fxarmes[n].getW()/2);
                  l.setTranslateY(y0+11);
                  getChildren().add(l);
              }              
              if(n>0){
                  y0=y0-15;
              }else {

              }
              n++;
              n =n+ visualizeMagazin(a, n,y0);

      }
      }catch(TomberArmeException ex){
          throw new RuntimeException(ex);
      }
  }
  
  int visualizeMagazin(GeneriqueArme a,int i,double y0){
      int k=0;
      Point2D p=null;
      for (; k < a.getNumMagazine(); k++) {
                  fxarmes[i + k ] = FXMagazine.createInstance(a.getModel());
                 p= createFXMagazine(fxarmes[i + k ], i + k,y0 );
                  //System.out.println("arme----------------->" + arm);
              }

  return k;
  }
   Point2D createFXMagazine(FXPatrouilleSprite sp,int i,double y0){
      Point2D p=null;
      if (sp != null) {
          sp.create();
          sp.setEffect(null);
          this.getChildren().add(sp);
          int max = 4;
          double k = Math.nextDown((i / max) - 0.1d);
          double y = (k * 10)+y0+30;
          double x = sp.getW() * ((i - (k * max))-1);
          if(k>0) x=sp.getW()*((i-(k*max)));
          sp.setTranslateY(y);
          sp.setTranslateX(x);
          p=new Point2D(x, y);
          sp.toFront();
          sp.setVisible(true);
          
      }
      return p;
  }
  
  double createFXEquipment(FXPatrouilleSprite sp,double i){
            DropShadow dropShadow=new DropShadow();
            dropShadow.setRadius(30.0);
            dropShadow.setOffsetX(12.0);
            dropShadow.setOffsetY(-12.0);
            dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
            dropShadow.setColor(Color.BLACK);  
              sp.create();
              sp.setEffect(dropShadow);
              this.getChildren().add(sp);
              double k=Math.nextDown((i/4)-0.1d);
              double y=(k)*25;
              double x=10;
           
              sp.setTranslateY(y);
              sp.setTranslateX(x);
              sp.toFront();
              sp.setVisible(true);
              

            return y;
          }
  
    public void updateMagazine(int n){
        numShot.setText(n+"");
    }
}
