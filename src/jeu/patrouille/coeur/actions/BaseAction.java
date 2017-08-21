/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.BaseActionComparatorImpl;
import jeu.patrouille.coeur.actions.enums.OrdreAction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author Alessio Sardaro
 */
public   class BaseAction extends AbstractAction {
    
    
    public static  BaseAction QUIT=new BaseAction(OrdreAction.PA_ACTION, -1, -1, -1,-1, null, null);
    public static  Comparator baseActionCompratorImpl=new BaseActionComparatorImpl();
    Piece protagoniste;
    Piece antagoniste;
    boolean used;
    int ordreInitiative;

    public void setOrdreInitiative(int ordreInitiative) {
        this.ordreInitiative = ordreInitiative;
    }
@Override
public int compareTo(BaseAction b) {

		//ascending order
		if(this.tempActivite<b.getTempActivite()) 
                    return -1;
                if(this.tempActivite>b.getTempActivite())return 1;
                if(this.tempActivite==b.getTempActivite()) return 0;
                return 0;
		//descending order
		//return compareQuantity - this.quantity;
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

    public Piece getProtagoniste() {
        return protagoniste;
    }
    
    public BaseAction(OrdreAction type, int i0, int j0, int i1, int j1, Piece protagoniste, Piece antagoniste) {
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
    public List<BaseAction> spreadAction() throws Exception{
        List<BaseAction> list=new ArrayList<>();
        list.add(this);
        return list;
    }


    

    

    
    @Override
    public String toString() {
        char c=' ';
        if(type==OrdreAction.FEU) c='f';
        else if(type==OrdreAction.MARCHE) c='m';
        
        
        String txt=c+""+i0+","+j0+";"+i1+","+j1;
        return txt;
    }    

    @Override
    public BaseAction clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Piece getAntagoniste() {
        return antagoniste;
    }

    public void setAntagoniste(Piece antagoniste) {
        this.antagoniste = antagoniste;
    }

    @Override
    public void calculeActionPointDesAction()throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    



     
    
}
