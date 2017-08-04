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
public class MurBas extends PointCarte {
    public MurBas(int i,int j){
        this.i=i;
        this.j=j;
        this.type=PointCarte.MURBAS;
        this.v=5;
    }
   
    
    
}
