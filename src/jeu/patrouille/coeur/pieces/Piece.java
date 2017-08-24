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
public abstract class Piece  {
    public enum ActeurType{SOLDAT,JEEP,HELICOPTER,ARMOR }
    public enum Direction{S,N,E,W,NW,NE,SE,SW}
    public enum Pose{PRONE,GENOUCS,DROIT}
    public abstract String toStringSimple() ;    
    public  static final int NOACTION=-1;
    ActeurType type;
    int tempDesponible;
    List<BaseAction> actionsPool;   
    boolean spreadDone=false;
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
        spreadDone=false;
    }
    public boolean isHostile(){
        return boss.getJeur()==GeneriqueJoeurs.JOEUR_HOST;
    
    }
    public boolean isUS(){
    return boss.getJeur()==GeneriqueJoeurs.JOEUR_US;
    
    }
    private void transformActionPool() throws Exception {
        List<BaseAction> newActionPool=new ArrayList<>();
        System.out.println("*******SRPEAD START*******>");
        
        for(BaseAction b:actionsPool){
            ActionType type=b.getType();
            System.out.println("----TD COST--------->"+b.getTempActivite());
            if(type==ActionType.MARCHE){
                List<BaseAction> l=b.spreadAction(); 
                System.out.println(" list spread ->"+l.size());
                newActionPool.addAll(l);
            }
        }
        
        spreadDone=true;
        if(newActionPool.size()>0) this.actionsPool=newActionPool;
        System.out.println("******SRPEAD END*****>");
     
    }
    public List<BaseAction> getBaseActionSum(int td) throws Exception{
        //System.out.println("----GET ACTION SUM START--------->"+spreadDone);
        if(!spreadDone) {

            transformActionPool();
        }
        int sum=0;
        List<BaseAction> list =new ArrayList<>();
        for (BaseAction b : actionsPool) {
             sum=sum+b.getTempActivite();
             if(sum<=td) {
                 if(!b.isUsed()){
                        list.add(b);
                        Soldat s=(Soldat)b.getProtagoniste();
                        int rollDice=boss.dice(10);
                        b.setOrdreInitiative( rollDice -s.getCC());
                        b.setUsed(true);                     
                 }

                 
             }
                 
        }
       // System.out.println("----GET ACTION SUM END--------->");
        return list;
    }
    
    
    public int getTempDisponible() {
        return tempDesponible;
    }
    public void setTempDesponible(int n) {
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


    public GeneriqueJoeurs getBoss() {
        return boss;
    }
    public void resetActionPoool(){
        actionsPool=new ArrayList<>();
        this.tempDesponible=10;
        spreadDone=false;
        
    }
    
}
