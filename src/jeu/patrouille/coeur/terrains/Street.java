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
public class Street extends Terrain {
    
    
        public Street(int i,int j,int rot){
        super(i,j,rot,"street.png");
        this.type=PointCarte.STREET;



    }
        public Street(int i,int j){
        super(i,j,"street.png");
        this.type=PointCarte.STREET;
     


    }        
}
