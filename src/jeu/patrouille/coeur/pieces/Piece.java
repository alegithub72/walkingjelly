/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;


/**
 *
 * @author Alessio Sardaro
 */
public abstract class Piece extends GeneriquePiece  {
    public enum ActeurType{SOLDAT,JEEP,HELICOPTER,ARMOR }
    public enum Direction{S(1,0),N(-1,0),E(0,-1),W(0,1),NW(-1,1),NE(-1,-1),SE(1,-1),SW(1,1);
        public int i,j;
        Direction(int i,int j){
          this.i=i;
          this.j=j;
       }
       
    }
    public enum Pose{PRONE,GENOUCS,DROIT}
    
    

    int tempDesponible;
    BaseAction[] actionsPool;   
    boolean spreadDone=false;
    int equipeArrayPlace;
    int actionArrayN;
    public Piece(ActeurType type,GeneriqueJoeurs boss){
        super(boss);
        this.type=type;
        this.i=-1;
        this.j=-1;
        this.tempDesponible=10;
        actionsPool=new BaseAction[10];
        actionArrayN=0;
        spreadDone=false;
    }
    public boolean isHostile(){
        return boss.getJeur()==GeneriqueJoeurs.JOEUR_HOST;
    
    }
    public boolean isUS(){
    return boss.getJeur()==GeneriqueJoeurs.JOEUR_US;
    
    }


    @Override
    public abstract String toStringSimple() ;
    
    public int getTempDisponible() {
        return tempDesponible;
    }
    public void setTempDesponible(int n) {
         tempDesponible=n;
    }    
    public void resetTempDispoleNotUse(){
        int l=0;
        
        while(l<actionArrayN)
        {   
            System.out.println(" actioPool size "+actionArrayN);
            BaseAction base=actionsPool[l];
            if(!base.isUsed()) tempDesponible=tempDesponible+base.getTempActivite();
            l++;
            
        }
        System.out.println(" TD reset to :"+tempDesponible);
        actionsPool=new BaseAction[10];
        actionArrayN=0;
    }
    public boolean isZeroActionPoint(){
    return tempDesponible==0;
    }
    public void decActionPoint(int menus) {
        this.tempDesponible = tempDesponible-menus;
    }

    public abstract Piece[] losView();


    
    
    public void setEquipeArrayPlace(int n){
        equipeArrayPlace=n;
    }
    public int getEquipeArrayPlace(){
        return equipeArrayPlace;
    }    
    
   
    
    public void  addAction(BaseAction act)throws Exception{
        if(act==null) throw new NullPointerException("Action null");
        act.calculeActionPointDesAction();
        actionsPool[actionArrayN]=act;
        tempDesponible=tempDesponible-act.getTempActivite();
        actionArrayN++;
        System.out.println("--TD RIMANENTE----->"+tempDesponible);
    }    
     
    
    public abstract int tempNecessarieDesActionBase(ActionType actionType)throws Exception;
    
    public BaseAction nextAction(int i){        
        return this.actionsPool[i];    
    }
    public BaseAction lastAction(){
        return this.actionsPool[actionArrayN-1];
    }    
    public int actionSize(){
        return actionArrayN;
        
    }   

    public void setActionArrayPlace(int n){
        this.actionArrayN=n;
    }
    public void resetActionPoool(){
        actionsPool=new BaseAction[10];
        this.tempDesponible=10;
        spreadDone=false;
        actionArrayN=0;
        
    }
    
}
