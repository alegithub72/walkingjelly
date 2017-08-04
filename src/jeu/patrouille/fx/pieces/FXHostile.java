/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;

/**
 *
 * @author appleale
 */
public class FXHostile extends FXSoldat{
    
    public FXHostile(Soldat s,int pos){
        super( "frameHostile.png", s, pos);
        this.flagImg=new ImageView("hosFlag.png");
       
     
        
        
        
        
    }

    @Override
    public void buildSprite() {
        super.buildSprite(); 
        this.getChildren().remove(classmentImg);
    }
    
    
}
