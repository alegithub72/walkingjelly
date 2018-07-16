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
public class ISOCoverter {
    public static double SQ_WD=100;
    public static double SQ_HG=100;
    public static double  covertToISOX(double x,double y){
        double sx=x-y; 
    return sx;
    }
        
    public static double  covertToISOY(double x,double y){
        double sy=(x+y)/2;
    return sy;
    }
/**
 * 
 * @param x=sx/2+sy
 * @param y=sy-sx/2
 * @return 
 */
  public static double isoConvertoScreenX(double x,double y){
      return (x/2)+y;
  }  
  public static double isoConverterScreenY(double x ,double y){
    return y-(x/2);
  }
  
 public static Point2DAndMap isoToIJMAPXY(double x,double y){
        double x1=isoConvertoScreenX(x, y);
        double y1=isoConverterScreenY(x, y);
        int  i=(int)((x1/SQ_WD));
        int j=(int)((y1/SQ_HG));
        double x2=i*SQ_WD;
        double y2=j*SQ_HG;
        if(i<0 || i>7) return null;
        if(j<0 || j>7) return null;
      //  System.out.println(" i="+i+" j="+j);
      //  System.out.println("x2="+x2+" y2="+y2);
        return new Point2DAndMap(covertToISOX( x2, y2),covertToISOY(x2, y2),i,j);
 
 
 }
  
  
  
}
