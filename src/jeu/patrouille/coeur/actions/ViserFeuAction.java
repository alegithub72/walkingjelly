/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class ViserFeuAction extends FeuAction{

    public ViserFeuAction(Piece protagoniste,Piece antagonieste,GeneriqueArme.FeuMode mode){
        super(ActionType.FEU_VISER,protagoniste, mode);
        this.mode=mode;
        
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }

     ViserFeuAction(ViserFeuAction act) {
        super(act);
    }

    @Override
    public void calculeActionPointDesAction() throws Exception{
        Soldat s=(Soldat)protagoniste;
        tempActivite=(int)s.tempNecessarieDesActionBase(ActionType.FEU_VISER);
    }

    @Override
    public BaseAction clone() {
        return new ViserFeuAction(this);
    }

    
     
    
}
