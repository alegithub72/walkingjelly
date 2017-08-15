/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

/**
 *
 * @author appleale
 */
public class Haie extends Terrain{
    public Haie(int i,int j,int type){     
        super(i,j,"arbuste"+type+".png");
        this.type=PointCarte.HAIE;
        this.v=0.5;
        this.c=Consistance.NO;        
       
    }


 
}
