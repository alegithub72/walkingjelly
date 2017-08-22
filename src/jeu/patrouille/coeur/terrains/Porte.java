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
public class Porte extends Terrain  {
    public Porte(int i,int j,int rot){
        super(i,j,rot,"portLight2.png");
        this.i=i;
        this.j=j;
        this.type=PointCarte.PORTE;
        this.v=0.6;
        this.c=Consistance.LEGER;        
       
    }
    public Porte(int i,int j){
        super(i,j,"portLight2.png");
        this.i=i;
        this.j=j;
        this.type=PointCarte.PORTE;
        this.v=0.6;
        this.c=Consistance.LEGER;
       
    }    
}
