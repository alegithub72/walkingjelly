/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.BaseActionComparatorImpl;
import jeu.patrouille.coeur.actions.enums.ActionType;
import java.util.Comparator;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author Alessio Sardaro
 */
public   class BaseAction extends AbstractAction {
    
   
    public static  BaseAction QUIT=new BaseAction(ActionType.PA_ACTION, -1, -1, -1,-1, null, null);
    public static  Comparator baseActionCompratorImpl=new BaseActionComparatorImpl();
    GeneriquePiece protagoniste;
    GeneriquePiece antagoniste;
    boolean used;
    int ordreInitiative;

    public void setOrdreInitiative(int ordreInitiative) {
        this.ordreInitiative = ordreInitiative;
    }






    public int getOrdreInitiative() {
        return ordreInitiative;
    }
    
    public boolean isProtagonisteTypeSoldat(){
    return protagoniste.getPieceType()==Piece.ActeurType.SOLDAT;
    }
    public boolean isProtagonisteHostile(){
        return protagoniste.getBoss().getJeur()==GeneriqueJoeurs.JOEUR_HOST;
    }
//    @Override
//    public int compareTo(BaseAction o) {
//      return (this.getTempActivite()-o.getTempActivite());
//    }

    public GeneriquePiece getProtagoniste() {
        return protagoniste;
    }
    
    public BaseAction(ActionType type, int i0, int j0, int i1, int j1, GeneriquePiece protagoniste, GeneriquePiece antagoniste) {
        this.type = type;
        this.j0 = j0;
        this.i0 = i0;
        this.j1 = j1;
        this.i1 = i1;
        this.protagoniste = protagoniste;
        this.antagoniste = antagoniste;
        used=false;             

    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }
    
    

    @Override
    public BaseAction[] spreadAction() throws Exception{
        BaseAction[] list=new BaseAction[1];
        list[0]=this;
        return list;
    }


    

    

    
    @Override
    public String toString() {
        char c=' ';
        if(type==ActionType.FEU) c='f';
        else if(type==ActionType.FEU_VISER) c='v';
        else if(type==ActionType.MARCHE) c='m';
        else if(type==ActionType.BANDAGE) c='b';
        else if(type==ActionType.ARME_RECHARGE) c='r';
        else if(type==ActionType.COURS) c='c';
        
        String txt=c+""+i0+","+j0+";"+i1+","+j1;
        return txt;
    }    

    @Override
    public BaseAction clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public GeneriquePiece getAntagoniste() {
        return antagoniste;
    }

    public void setAntagoniste(GeneriquePiece antagoniste) {
        this.antagoniste = antagoniste;
    }

    @Override
    public void calculeActionPointDesAction()throws Exception {
        tempActivite= type.TN();
    }

    



     
    
}
