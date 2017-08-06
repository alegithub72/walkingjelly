/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;


import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class FXMouseJeurHelper {
    private BaseAction act;
    private FXSoldat seletctionee;
    private Carte carte;
    private boolean commanNotvalid;
    
    public FXMouseJeurHelper(BaseAction act,Carte carte){
        this.act=act;
        this.carte=carte;

    }
    public FXMouseJeurHelper(FXSoldat s,Carte carte){
        this.seletctionee=s;
        this.carte=carte;
    }

    public BaseAction getAct() {
        return act;
    }

    public void setAct(BaseAction act) {
        this.act = act;
    }

    public Soldat getSeletctionee() {
        return seletctionee.getSoldat();
    }

    public void setFXSeletctionee(FXSoldat seletctionee) {
        this.seletctionee = seletctionee;
    }
    
    public void setSeletctionee(Soldat s) {
        
        this.seletctionee.setS(s);
    }
    public void setArrivalCarteCoord(int i1,int j1){
        act.setI1(i1);
        act.setJ1(j1);
    
    }
    public void addSoldataSelectioneeAction(){
        Soldat s=seletctionee.getSoldat();
        s.addAction(act);
    
    }

    @Override
    public String toString() {
        return   "act=" + act.toString() ;
    }
    
    public boolean rangeMarcheSoldat(double range){
             Soldat s=seletctionee.getSoldat();
      return ( range>0 
              && s.getActionPoint()>0 
              && range<=(s.getActionPoint()*FXCarte.TILE_SIZE));
    
    }
    
   public boolean carteValiderRoute(){
       return carte.validerLeRoute(act);
   }     
   
   public int  mapLastJ(){
        Soldat s=seletctionee.getSoldat();
        int actionsize = s.actionSize();

        BaseAction lastAct = null;
        int j = -1;
        if (actionsize >0) {
            lastAct = s.lastAction();
            System.out.println("last action =" + lastAct);
            if (lastAct.getJ1() >= 0) {
                j = lastAct.getJ1();//TODO le azione che non muovono
            } else {
                j = lastAct.getJ0();
            }
        } else {
            j = s.getJ();
        }
   


        return j;
    }

    public int mapLastI() {
     Soldat s=seletctionee.getSoldat();
        int actionsize = s.actionSize();

        BaseAction lastAct = null;
        int i = -1;
        if (actionsize > 0) {
            lastAct = s.nextAction(actionsize - 1);
            if (lastAct.getI1() >= 0) {
                i = lastAct.getI1();//TODO le azione che non muovono
            } else {
                i = s.getI();
            }
        } else {
            i = s.getI();
        }

       

        return i;
   }      
    
public FXSoldat getFXSoldatSelectionee(){
    return seletctionee;
}    

    public boolean isCommanNotvalid() {
        return commanNotvalid;
    }
 
    public void setCommanNotvalid(boolean commanNotvalid) {
        this.commanNotvalid = commanNotvalid;
    }
}
