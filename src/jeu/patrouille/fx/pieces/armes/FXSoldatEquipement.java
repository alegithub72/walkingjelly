/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces.armes;

import javafx.scene.Parent;
import jeu.patrouille.coeur.armes.GeneriqueArme;
import jeu.patrouille.coeur.armes.GeneriqueEquipment;
import jeu.patrouille.coeur.armes.GeneriqueEquipment.*;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public class FXSoldatEquipement extends Parent{
    FXPlanche fxpl;
    FXPatrouilleSprite armes[];
  public   FXSoldatEquipement(FXPlanche fxpl){
      super();      
      this.fxpl=fxpl;

      
     
      
  }
  public void buildFXEquipment(Soldat s){
      GeneriqueEquipment[]  list=s.getEquipment();
       
      armes=new FXPatrouilleSprite[s.getEquipmentChiffre()];
      //DropShadow ds=new DropShadow(5, Color.DARKOLIVEGREEN);
      //ds.setOffsetX(5);
      //ds.setOffsetY(5);   
      for (int i = 0; i < list.length; i++) {
          GeneriqueEquipment arm = list[i]; 
          
          if(Model.BENELLI_M3==arm.getModel()) {
              armes[i]=new FXBenelliM3();
              GeneriqueArme a= ((GeneriqueArme)arm);
               createFXEquipment(armes[i],i);
              for(int k=0;k<a.getNumMagazine() ;k++){
              armes[i+k+1]=FXMagazine.createInstance(arm.getModel());
               createFXEquipment(armes[i+k+1],i+k+1);
               
              System.out.println("arme----------------->"+arm);
              }
              i=i+a.getNumMagazine();

          }
          if(Model.AK74==arm.getModel()){
              GeneriqueArme a= ((GeneriqueArme)arm);
             armes[i]=new FXAK74();
              createFXEquipment(armes[i],i);
            System.out.println("arme----------------->"+arm);
            for(int k=0;k<a.getNumMagazine() ;k++){
              armes[i+k+1]=FXMagazine.createInstance(arm.getModel());
              createFXEquipment(armes[i+k+1],i+k+1);
            }
               i=i+a.getNumMagazine();
          
            
          }

          

         

      }
  
  }
  void createFXEquipment(FXPatrouilleSprite sp,int i){
            if(sp!=null){
              sp.create();
              sp.setEffect(null);
              this.getChildren().add(sp);

              int max=4;
              
              if(i>0){
                  double k=Math.nextDown((i/max)-0.1d);
                  double y=(k+2)*sp.getH();
                  double x=sp.getW()*((i-(k*max))-1);
                  if(k>0) x=sp.getW()*((i-(k*max)));
                      sp.setTranslateY(y);
                  sp.setTranslateX(x);
                  
              } 

              
              
           /// equipment.setTranslateX((315+(28 * i)) );
            // equipment.setTranslateY(11);
           //   equipment.relocate((315+(28 * i)) , 11);
              sp.toFront();
              sp.setVisible(true);
          }
  }
    
}
