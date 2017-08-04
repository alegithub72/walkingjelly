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
public class StreetBorder extends Terrain {
    
    
        public StreetBorder(int i,int j,int rot){
        super(i,j,rot,"streetBorder.png");
        this.type=PointCarte.PORTE;
        this.v=7;

    }
        public StreetBorder(int i,int j){
        super(i,j,"streetBorder.png");
        this.type=PointCarte.PORTE;
        this.v=7;

    }        
}
