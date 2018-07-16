/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.util;

import javafx.geometry.Point2D;

/**
 *
 * @author AlessioADM
 */
public class Point2DAndMap extends Point2D{
    int mapx,mapy;

    public Point2DAndMap(double x, double y,int mapx, int mapy) {
        super(x, y);
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public int getMapx() {
        return mapx;
    }

    public void setMapx(int mapx) {
        this.mapx = mapx;
    }

    public int getMapy() {
        return mapy;
    }

    public void setMapy(int mapy) {
        this.mapy = mapy;
    }

    @Override
    public String toString() {
        return "Point2DAndMap{" + "mapx=" + mapx + ", mapy=" + mapy  +" x="+this.getX()+", y="+this.getY() +"}";
    }
    
}
