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
public class Fenetre extends Terrain{
    
    public Fenetre(int i,int j,int rot){
        super(i,j,rot,"fenetreLight.png");
        this.i=i;
        this.j=j;
        this.type=PointCarte.FENETRE;
        this.v=0.5;
        this.c=Consistance.LEGER;        

    }
    public Fenetre(int i,int j){
        super(i,j,"fenetreLight.png");
        this.i=i;
        this.j=j;
        this.type=PointCarte.FENETRE;
        this.v=0.5;
        this.c=Consistance.LEGER;        

    }    
    
}
