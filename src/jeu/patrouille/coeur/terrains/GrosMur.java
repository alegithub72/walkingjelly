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
public class GrosMur extends Terrain {
    public GrosMur(int i,int j,int rot){
        super(i,j,rot,"wallLight.png");
        this.type=PointCarte.GROSMUR;
        this.v=0;

    }
    public GrosMur(int i,int j){
        super(i,j,"wallLight.png");
        this.type=PointCarte.GROSMUR;
        this.v=0;

    }    
}
