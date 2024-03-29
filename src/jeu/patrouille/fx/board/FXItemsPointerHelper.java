/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;


import javafx.event.EventType;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.AbstractAction;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.FeuAction;
import jeu.patrouille.coeur.actions.ViserFeuAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.pieces.FXSoldat;
import jeu.patrouille.fx.sprite.CursorHelper;
import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author appleale
 */
public class FXItemsPointerHelper {
    private BaseAction act;
    private FXSoldat seletctionee;
    private Carte carte;
    private FXCarte fxcarte;
    private boolean commanNotvalid;
    private boolean actionSeletione;
    private int rangeCursorHelper;
    private int lastVisualizationRond;
            
   
    public FXItemsPointerHelper(FXSoldat sfx,FXCarte fxcarte){
        this.seletctionee=sfx;
        this.carte=fxcarte.getCarte();
        this.fxcarte=fxcarte;
        this.commanNotvalid=false;
        this.act=null;
        this.lastVisualizationRond=-1;
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
        if(act.getType().isMovementAction()
                &&  this.seletctionee.getSoldat().
                        getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST) 
        this.rangeCursorHelper=ImageChargeur.CURSOR_HOST_RANGE;
        else if(act.getType().isMovementAction()
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

    public void setFXSeletctionee(FXSoldat seletctionee) {
        this.seletctionee = seletctionee;
        this.lastVisualizationRond=seletctionee.getSoldat().getBoss().getJeur();
    }
    

    public void setArrivalCarteCoord(int i1,int j1){
        act.setI1(i1);
        act.setJ1(j1);
    
    }
    public void addActionToSoldat()throws Exception{
        Soldat s=seletctionee.getSoldat();
        s.addAction(act);
    
    }

    @Override
    public String toString() {
        return   "act=" + act.toString() ;
    }
    

    
   public PointCarte carteValiderRoute(){
       return carte.validerLeRoute(act);
   }     
   
   public int  mapLastJ(){
     Soldat s=seletctionee.getSoldat();
     int size=s.actionSize();
     BaseAction last=null;
      for (int h=0;h<size;h++){
          BaseAction act1=s.nextAction(h);
          if(act1.getType().isMovementAction()) last=act1;
      }
          
      if(last!=null) return last.getJ1();
      else return s.getJ();
    }

    public int mapLastI() {
     Soldat s=seletctionee.getSoldat();
     int size=s.actionSize();
     BaseAction last=null;
      for (int h=0;h<size;h++){
          BaseAction act1=s.nextAction(h);
          //System.out.println(s.toStringSimple()+":the action action of -->"+ act1.toString());
          if(act1.getType().isMovementAction()) last=act1;
      }
      //System.out.println("last action -->"+ ((last!=null)?last.toString():"null"));
      if(last!=null ) return last.getI1();
      else return s.getI();

 
   }      
    
public FXSoldat getFXSoldatSelectionee(){
    return seletctionee;
}    

    public boolean isCommanNotvalid() {
        return commanNotvalid;
    }
 
    public void setCommanNotvalid(boolean commanNotvalid) {
        removeDisplayRange();
        if(commanNotvalid==true) 
            this.rangeCursorHelper=ImageChargeur.CURSOR_FORBIDDEN;
        this.commanNotvalid = commanNotvalid;
    }

    public boolean isLastUSVisualizationTurn(Soldat s){
        Soldat slast=seletctionee.getSoldat();
               
        return  lastVisualizationRond!=slast.getBoss().getJeur();
    }
    public void buildFeuAction(BaseAction act,int i1,int j1,double angle){
        Terrain t= carte.getPointCarte(i1, j1);
        GeneriquePiece p=t.getPiece();
        Soldat s=null;
        if(t.getPiece()!=null && 
                t.getPiece().getPieceType()==Piece.ActeurType.SOLDAT)
            s=(Soldat)p;


        act.setI1(i1);
        act.setJ1(j1);
        act.setAntagoniste(s);
        Soldat s0=(Soldat)act.getProtagoniste();
        if(act.getType()==ActionType.FEU)  ((FeuAction)act).setAngle(angle);
        else if(act.getType()==ActionType.FEU_VISER) ((ViserFeuAction)act).setAngle(angle);
                
        act.setI0(s0.getI());
        act.setJ0(s0.getJ());
        this.act=act;
    }
    public void builtAction(ActionType type){
    
        Terrain t=carte.getPointCarte(act.getI1(), act.getJ1());
        GeneriquePiece p=t.getPiece();
        Soldat s=null;
        if(t.getPiece()!=null && 
                t.getPiece().getPieceType()==Piece.ActeurType.SOLDAT)
            s=(Soldat)p;
        act.setAntagoniste(s);
        if(s==act.getProtagoniste())
            act.setVersus(AbstractAction.SubjectType.MYSELF);
        else act.setVersus(AbstractAction.SubjectType.OTHER);
    
    }
    
    public void removeDisplayRange(){
       
        if(fxcarte.rootGroup.getChildren().contains(getDisplayRange()))
            fxcarte.rootGroup.getChildren().remove(getDisplayRange());
            
    }
}
