/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author appleale
 */
public  class BaseAction extends AbstractAction {
    public static  BaseAction QUIT=new BaseAction(-1, -1, -1, -1,-1, null, null);
    public static  Comparator baseActionCompratorImpl=new BaseActionComparatorImpl();
    Piece protagoniste;
    Piece[] antagonistes;
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
    
    
//    @Override
//    public int compareTo(BaseAction o) {
//      return (this.getTempActivite()-o.getTempActivite());
//    }

    public Piece getProtagoniste() {
        return protagoniste;
    }
    
    public BaseAction(int type, int i0, int j0, int i1, int j1, Piece protagoniste, Piece[] antagonistes) {
        this.type = type;
        this.j0 = j0;
        this.i0 = i0;
        this.j1 = j1;
        this.i1 = i1;
        this.protagoniste = protagoniste;
        this.antagonistes = antagonistes;
        used=false;             
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }
    
    

    @Override
    public List<BaseAction> spreadAction() {
        List<BaseAction> list=new ArrayList<>();
        list.add(this);
        return list;
    }


    
    public void calculeActionPointDesActions(){
        tempActivite= BaseAction.ACTIONPOINTVALOR[type];
    
    }
    

    
    @Override
    public String toString() {
        char c=' ';
        if(type==BaseAction.FEU) c='f';
        else if(type==BaseAction.MARCHE) c='m';
        
        
        String txt=c+""+i0+","+j0+";"+i1+","+j1;
        return txt;
    }    

    @Override
    public BaseAction clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    



     
    
}
