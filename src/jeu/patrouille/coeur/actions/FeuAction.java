/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.OrdreAction;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.pieces.Lesion;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class FeuAction extends BaseAction{
    double dist;
    public FeuAction(int i1,int j1,Piece protagoniste,Piece antagonieste){
        super(OrdreAction.FEU, -1, -1, i1, j1, protagoniste, antagonieste);
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }

    @Override
    public void calculeActionPointDesAction() throws Exception{
        Soldat s=(Soldat)protagoniste;
        tempActivite=s.tempNecessarieDesActionBase(OrdreAction.FEU);

       // super.calculeActionPointDesActions(); //To change body of generated methods, choose Tools | Templates.
    }
     
    
}
