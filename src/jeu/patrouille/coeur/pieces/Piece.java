/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.actions.BaseAction;


/**
 *
 * @author appleale
 */
public abstract class Piece  {
    public  static final int NOACTION=-1;
    public enum ActeurType{SOLDAT,JEEP,HELICOPTER,ARMOR } ;
    public enum Direction{S,N,E,W,NW,NE,SE,SW};
    ActeurType type;
    int tempDesponible;
    List<BaseAction> actionsPool;     

    int i,j;
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
    public Piece(ActeurType type){
        this.type=type;
        this.i=-1;
        this.j=-1;
        this.tempDesponible=10;
        actionsPool=new ArrayList();        
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
    int arrayN;
    
    
    public void setArraN(int n){
        arrayN=n;
    }
    public int getarrayN(){
        return arrayN;
    }    
    
    public abstract Piece clonerPiece();
   
    
    public void  addAction(BaseAction act){
        
        actionsPool.add(act);
        tempDesponible=tempDesponible-act.valorActionPointDesActions();
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
     
}
