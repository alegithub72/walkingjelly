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
public class AngleGrosMur extends Terrain {
    public AngleGrosMur(int i,int j,int rot){
        super(i,j,rot,"wallAngleLight.png");
        this.type=PointCarte.GROSMUR;
        this.v=0;

    }
    public AngleGrosMur(int i,int j){
        super(i,j,"wallAngleLight.png");
        this.type=PointCarte.GROSMUR;
        this.v=0;

    }    
}
