/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author appleale
 */
public class Haie extends Terrain{
    public Haie(int i,int j,int type){     
        super(i,j,"arbuste"+type+".png");
        this.type=PointCarte.HAIE;
        this.v=4;
       
    }


 
}
