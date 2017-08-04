/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;

import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.sprite.Sprite;
/**
 *
 * @author appleale
 */
public class AISoldat extends Soldat {


    public AISoldat(String nom,String nomDeFamilie,
    int competenceArme,
    int connaissanceArme,
    int combatRapproché,
    int force,
    int courage,
    int sante,
    int blindage,
    int moral,
    int commandControler,Direction d){
        //super(ActeurType.SOLDAT);
        super( nom, nomDeFamilie,
     competenceArme,
     connaissanceArme,
     combatRapproché,
     force,
     courage,
     sante,
     blindage,
     moral,
     commandControler,d);

    }   
    
    ImageView aiimg;

    public ImageView getAiimg() {
        return aiimg;
    }
    
/**
 * He could have a path or limit movement area where move and fire first or fixed position and 
 * OP-Fire action
 * @param x0
 * @param y0
 * @param x1
 * @param y1 
 */
   public void setSentinelComportement(int x0,int y0,int x1,int y1){
   } 
    /**
     * He could use a preferred direction to move and he preferred to fire first...
     * each turn just some step and leave some point to fire.....
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1 
     */   
   public void setAggresiveComportement(int x0,int y0,int x1,int y1){
   } 
   /**
    * He stay close to the squad and fire as second priority ...and if he is the range of them
    * fire 
    * @param x0
    * @param y0
    * @param x1
    * @param y1 
    */
   public void setLeaderComportement(int x0,int y0,int x1,int y1){
   }  
   public BaseAction preferredAction(){
   BaseAction ac=null;
   return ac;
   }
}
