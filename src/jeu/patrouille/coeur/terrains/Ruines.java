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
public class Ruines extends Terrain{
    
    public Ruines(int i,int j,int rot){
        super(i,j,rot,null);
        this.type=PointCarte.RUINES;
        this.v=6;
    }
}
