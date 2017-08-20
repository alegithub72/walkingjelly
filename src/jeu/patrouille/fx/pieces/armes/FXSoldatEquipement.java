/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces.armes;

import javafx.scene.Parent;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public class FXSoldatEquipement extends Parent{
    FXPlanche fxpl;
    FXPatrouilleSprite fxarmes[];
  public   FXSoldatEquipement(FXPlanche fxpl){
      super();      
      this.fxpl=fxpl;

      
     
      
  }
  public void buildFXEquipment(Soldat s){
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
              if(a==s.getArmeUtilise()) ((FXEquipment)fxarmes[n]).addUsed(fxarmes[n].getW());              
              if(n>0) y0=y0-40;
              n++;
              n =n+ visualizeMagazin(a, n,y0);

      }

  }
  
  int visualizeMagazin(GeneriqueArme a,int i,double y0){
      int k=0;
      for (; k < a.getNumMagazine(); k++) {
                  fxarmes[i + k ] = FXMagazine.createInstance(a.getModel());
                  createFXMagazine(fxarmes[i + k ], i + k,y0 );
                  //System.out.println("arme----------------->" + arm);
              }
  return k;
  }
  void createFXMagazine(FXPatrouilleSprite sp,int i,double y0){
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

          sp.toFront();
          sp.setVisible(true);
      }
  }
  
  double createFXEquipment(FXPatrouilleSprite sp,double i){

              sp.create();
              sp.setEffect(null);
              this.getChildren().add(sp);
              double k=Math.nextDown((i/4)-0.1d);
              double y=(k)*20;
              double x=10;
           
              sp.setTranslateY(y);
              sp.setTranslateX(x);
              sp.toFront();
              sp.setVisible(true);
              

            return y;
          }
  
    
}
