/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

import javafx.scene.image.ImageView;
import  javafx.scene.image.Image;
/**
 *
 * @author appleale
 */
public class Arbre4 extends Terrain {
    
    public Arbre4(int i,int j){
        super(i, j,"treeBig4bis.png");
        this.type=PointCarte.ARBRE;
        this.v=0.6;
       
        
    }


}
