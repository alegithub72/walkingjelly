/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;


/**
 *
 * @author appleale
 */
public abstract class Piece  {
    public enum ActeurType{SOLDAT,JEEP,HELICOPTER,ARMOR }
    public enum Direction{S,N,E,W,NW,NE,SE,SW}
    public abstract String toStringSimple() ;    
    public  static final int NOACTION=-1;
    ActeurType type;
    int tempDesponible;
    List<BaseAction> actionsPool;   
    boolean spreaded;
    GeneriqueJoeurs boss;
    int i,j;
    int arrayN;
    public Piece(ActeurType type,GeneriqueJoeurs boss){
        this.type=type;
        this.i=-1;
        this.j=-1;
        this.tempDesponible=10;
        actionsPool=new ArrayList();
        this.boss=boss;
        spreaded=false;
    }

    private void transformActionPool() {
        List<BaseAction> newActionPool=new ArrayList<>();
        spreaded=true;
        for(BaseAction b:actionsPool){
            int type=b.getType();
            if(type==BaseAction.MARCHE){
                List<BaseAction> l=b.spreadAction(); 
                System.out.println(" list spread ->"+l.size());
                newActionPool.addAll(l);
            }
        }
        this.actionsPool=newActionPool;
    }
    public List<BaseAction> getBaseActionSum(int tdInc){
        if(!spreaded) {
            System.out.println("transformed=====");
            transformActionPool();
        }
        int sum=0;
        List<BaseAction> list =new ArrayList<>();
        for (BaseAction b : actionsPool) {
             sum=sum+b.getTempActivite();
             if(sum<=tdInc) {
                 if(!b.isUsed()){
                        list.add(b);
                        Soldat s=(Soldat)b.getProtagoniste();
                        int rollDice=boss.dice(10);
                        b.setOrdreInitiative( rollDice -s.getCC());
                        b.setUsed(true);                     
                 }

                 
             }
                 
        }
        
        return list;
    }
    
    
    public int getActionPoint() {
        return tempDesponible;
    }
    public void setActionPoint(int n) {
         tempDesponible=n;
    }    
    public boolean isZeroActionPoint(){
    return tempDesponible==0;
    }
    public void decActionPoint(int menus) {
        this.tempDesponible = tempDesponible-menus;
    }

    public ActeurType getPieceType() {
        return type;
    }
    public abstract Piece[] losView();


    public void setI(int i){
        this.i=i;
    }
    public void setJ(int j){
     this.j=j;
    }
    
    public int getI(){
    return i;
    }
    public int getJ(){
    return j;
    }
    
    
    public void setArraN(int n){
        arrayN=n;
    }
    public int getarrayN(){
        return arrayN;
    }    
    
    public abstract Piece clonerPiece();
   
    
    public void  addAction(BaseAction act){
        act.calculeActionPointDesActions();
        actionsPool.add(act);
        tempDesponible=tempDesponible-act.getTempActivite();
    }    
     
    public BaseAction nextAction(int i){
        
        return this.actionsPool.get(i);
    
    }
    public BaseAction lastAction(){
        if(actionsPool.size()>0)
        return this.actionsPool.get(actionsPool.size()-1);
        else return null;
    }    
    public int actionSize(){
        if(actionsPool!=null) return actionsPool.size();
        else return 0;
    }   
    public static double getDirection(double x0,double y0,double x1,double y1){
        Point2D pv=new Point2D(x0, y0);
        Point2D p0=new Point2D(0, y0);
        Point2D p1=new Point2D(x1, y1);
        if((y1-y0)<0 && (x1-x0)<0) 
            return pv.angle(p0, p1)-90;
        else if((y1-y0)<0 && (x1-x0)>0)
            return pv.angle(p0, p1)-90;        
        else  if((y1-y0)>0 && (x1-x0)>0 ) return (180-pv.angle(p0, p1))+90;
        else if((y1-y0)>0 && (x1-x0)<0) return -pv.angle(p0,p1)-90;
        else if((x1-x0)==0 && (y1-y0)<0) return pv.angle(p0,p1)-90;
        else if((x1-x0)==0 && (y1-y0)>0) return -pv.angle(p0,p1)-90;
        else if((y1-y0)==0 && (x1-x0)>0) return pv.angle(p0,p1)-90;
        else if((y1-y0)==0 && (x1-x0)<0)return pv.angle(p0,p1)-90;
        return pv.angle(p0,p1);
      
            
    }
}
