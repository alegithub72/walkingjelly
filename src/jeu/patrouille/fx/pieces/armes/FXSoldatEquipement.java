/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces.armes;

import javafx.scene.Parent;
import jeu.patrouille.coeur.armes.ArmeGenerique;
 import jeu.patrouille.coeur.armes.ArmeGenerique.Model;
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
      ArmeGenerique[]  list=s.getEquipment();
       
      armes=new FXPatrouilleSprite[s.getEquipment().length];
      //DropShadow ds=new DropShadow(5, Color.DARKOLIVEGREEN);
      //ds.setOffsetX(5);
      //ds.setOffsetY(5);   
      for (int i = 0; i < list.length; i++) {
          ArmeGenerique arm = list[i]; 
          
          if(Model.BENELLI_M3==arm.getModel()) {
              armes[i]=new FXBenelliM3();
              
              System.out.println("arme----------------->"+arm);
           

          }
          if(Model.AK74==arm.getModel()){
             armes[i]=new FXAK74();
            System.out.println("arme----------------->"+arm);

            
          }
          if(armes[i]!=null){
              armes[i].create();
              armes[i].setEffect(null);
              this.getChildren().add(armes[i]);
           /// equipment.setTranslateX((315+(28 * i)) );
            // equipment.setTranslateY(11);
           //   equipment.relocate((315+(28 * i)) , 11);
              armes[i].toFront();
              armes[i].setVisible(true);
          }
         

      }
  
  }
  
    
}
