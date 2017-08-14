/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;


import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.pieces.FXUSSoldat;
import jeu.patrouille.fx.sprite.CursorHelper;
import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author appleale
 */
public class FXMouseJeurHelper {
    private BaseAction act;
    private FXUSSoldat seletctionee;
    private Carte carte;
    private boolean commanNotvalid;
    private boolean actionSeletione;
    private int rangeCursorHelper;
            


    
    
    
    
    

    
        
    public FXMouseJeurHelper(FXUSSoldat s,Carte carte){
        this.seletctionee=s;
        this.carte=carte;
        this.commanNotvalid=false;
        this.act=null;
    }
    
    
    public void setActionSeletione(boolean actionSeletione) {
        this.actionSeletione = actionSeletione;
    }

    public boolean isActionSelectione() {
        return actionSeletione;
    }

    public int getRangeCursorHelper() {
        return rangeCursorHelper;
    }

    public void setRangeCursorHelper(int rangeCursorHelper) {
      this.rangeCursorHelper = rangeCursorHelper;
    }
    
    public void resetCursorHelper(){
        if(act.getType()==BaseAction.MARCHE 
                &&  this.seletctionee.getSoldat().
                        getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST) 
        this.rangeCursorHelper=ImageChargeur.CURSOR_HOST_RANGE;
        else if(act.getType()==BaseAction.MARCHE 
                && this.seletctionee.
                        getSoldat().getBoss().getJeur()== GeneriqueJoeurs.JOEUR_US) 
            this.rangeCursorHelper=ImageChargeur.CURSOR_US_RANGE;     
    
    }
    public CursorHelper getDisplayRange(){
        return ImageChargeur.getInstance().getDisplayRange(rangeCursorHelper);
    
    }
    public BaseAction getAct() {
        return act;
    }

    public void setAct(BaseAction act) {
      this.act = act;
      this.resetCursorHelper();        
    }

    public Soldat getSeletctionee() {
        return seletctionee.getSoldat();
    }

    public void setFXSeletctionee(FXUSSoldat seletctionee) {
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
    
public FXUSSoldat getFXSoldatSelectionee(){
    return seletctionee;
}    

    public boolean isCommanNotvalid() {
        return commanNotvalid;
    }
 
    public void setCommanNotvalid(boolean commanNotvalid) {
        if(commanNotvalid==true) 
            this.rangeCursorHelper=ImageChargeur.CURSOR_FORBIDDEN;
        this.commanNotvalid = commanNotvalid;
    }
}
