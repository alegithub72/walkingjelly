/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;


/**
 *
 * @author Alessio Sardaro
 */
public abstract class Piece extends GeneriquePiece  {
    public enum ActeurType{SOLDAT,JEEP,HELICOPTER,ARMOR }
    public enum Direction{S,N,E,W,NW,NE,SE,SW}
    public enum Pose{PRONE,GENOUCS,DROIT}
    public abstract String toStringSimple() ;    

    int tempDesponible;
    List<BaseAction> actionsPool;   
    boolean spreadDone=false;
    int arrayN;
    public Piece(ActeurType type,GeneriqueJoeurs boss){
        super(boss);
        this.type=type;
        this.i=-1;
        this.j=-1;
        this.tempDesponible=10;
        actionsPool=new ArrayList(10);

        spreadDone=false;
    }
    public boolean isHostile(){
        return boss.getJeur()==GeneriqueJoeurs.JOEUR_HOST;
    
    }
    public boolean isUS(){
    return boss.getJeur()==GeneriqueJoeurs.JOEUR_US;
    
    }


    
    
    public int getTempDisponible() {
        return tempDesponible;
    }
    public void setTempDesponible(int n) {
         tempDesponible=n;
    }    
    public void resetTempDispoleNotUse(){
        int l=0;
        
        while(l<actionsPool.size())
        {   
            System.out.println(" actioPool size "+actionsPool.size());
            BaseAction base=actionsPool.get(l);
            if(!base.isUsed()) tempDesponible=tempDesponible+base.getTempActivite();
            l++;
            
        }
        System.out.println(" TD reset to :"+tempDesponible);
        actionsPool=new ArrayList<>(10);
    }
    public boolean isZeroActionPoint(){
    return tempDesponible==0;
    }
    public void decActionPoint(int menus) {
        this.tempDesponible = tempDesponible-menus;
    }

    public abstract Piece[] losView();


    
    
    public void setArraN(int n){
        arrayN=n;
    }
    public int getarrayN(){
        return arrayN;
    }    
    
   
    
    public void  addAction(BaseAction act)throws Exception{
        act.calculeActionPointDesAction();
        actionsPool.add(act);
        tempDesponible=tempDesponible-act.getTempActivite();
        System.out.println("--TD RIMANENTE----->"+tempDesponible);
    }    
     
    
    public abstract int tempNecessarieDesActionBase(ActionType actionType)throws Exception;
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


    public void resetActionPoool(){
        actionsPool=new ArrayList<>(10);
        this.tempDesponible=10;
        spreadDone=false;
        
    }
    
}
