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
public class FenetreCoin extends Terrain{
    
    public FenetreCoin(int i,int j,int rot){
        super(i,j,rot,"fenetre.png");
        this.type=PointCarte.FENETRE;
        this.v=0.5;
        this.c=Consistance.DUR;        

    }
    
    public FenetreCoin(int i,int j){
        super(i,j,"fenetre.png");
        this.type=PointCarte.FENETRE;
        this.v=0.5;
        this.c=Consistance.DUR;        

    }    
    public char graficRaprestation(){
        return 'ƒ';
    }
}
