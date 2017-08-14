/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

import javafx.scene.image.Image;

/**
 *
 * @author appleale
 */
public class Interior extends Terrain {
    
    public Interior(int i,int j){
        super(i,j,"inside.png");
        this.type=PointCarte.INTERIOR;
        this.v=0.999;
      
    }
    
}
