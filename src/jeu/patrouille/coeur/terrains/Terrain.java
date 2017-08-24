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
public class Terrain extends PointCarte {
    public enum Consistance {LEGER,DUR,NO}
    int orientation;
    int rot;
    String imgFile;
    Consistance c;
    public Terrain(int i,int j,int rot,String f){
        super(i,j);
        this.type=PointCarte.TERRAIN;
        this.imgFile=f;
        this.rot=rot;
        this.c=Consistance.NO;
       
    }
    public Terrain(int i,int j){
        super(i,j);
        this.type=PointCarte.TERRAIN;
        imgFile="vert2.png";
        rot=0;
         this.c=Consistance.NO;


    }
    public Terrain(int i,int j,String f){
        super(i,j);
        this.type=PointCarte.TERRAIN;
        this.imgFile=f;
        rot=0;
        this.c=Terrain.Consistance.NO;
        
    
    }

    public Consistance getConsistance() {
        return c;
    }

    
    @Override
    public char rapresentation() {
        return '\'';
    }
   Image img;

    public Image getImg() {
        if(img==null) loadImage();
        return img;
    }

     void loadImage(){
        Image png=new Image(imgFile);
        Canvas l=new Canvas(50,50);
        l.getGraphicsContext2D().drawImage(img, i, i);
      
        ImageView v=new ImageView(png);
        if(rot!=0){
            SnapshotParameters params = new SnapshotParameters();
        
        params.setFill(Color.TRANSPARENT);
        v.setRotate(rot);         
        this.img = v.snapshot(params, null);     
        }else this.img=png;
        
    
    }

}
