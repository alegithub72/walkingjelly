/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.pieces.Lesion;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class FeuAction extends BaseAction{
    public FeuAction(int i1,int j1,Piece protagoniste,Piece antagonieste){
        super(BaseAction.FEU, -1, -1, i1, j1, protagoniste, antagonieste);
    }

    @Override
    public void calculeActionPointDesActions() {
        int apbase= BaseAction.ACTIONPOINTVALOR[type];
        Soldat s=(Soldat)protagoniste;
        if(s.getStatu()==Lesion.Statu.GRAVE || s.getStatu()==Lesion.Statu.GRAVE_BRASE)
        apbase=apbase*2;
        tempActivite=apbase;
       // super.calculeActionPointDesActions(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
