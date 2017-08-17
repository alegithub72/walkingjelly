/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;
/**
 *
 * @author Alessio Sardaro
 */
public class Arbre extends Terrain {
    
    public Arbre(int i,int j){
        super(i, j,"treeBig1bis.png");
        this.type=PointCarte.ARBRE;
        this.v=0.6;
         this.c=Consistance.DUR;
       
        
    }


}
