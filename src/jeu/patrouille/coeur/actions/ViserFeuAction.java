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
public class ViserFeuAction extends BaseAction{
    double angle;
    GeneriqueArme.FeuMode mode;
    public ViserFeuAction(Piece protagoniste,Piece antagonieste,GeneriqueArme.FeuMode mode){
        super(ActionType.FEU_VISER, -1, -1,-1, -1, protagoniste, antagonieste);
        this.mode=mode;
        
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }

     ViserFeuAction(ViserFeuAction act) {
        super(act.type, act.i0, act.j0, act.i1, act.j1,null , null);
        if(act.antagoniste!=null ) antagoniste=act.antagoniste.clonerPiece();
        if(act.protagoniste!=null) protagoniste=act.protagoniste.clonerPiece();
         this.angle=act.angle;
     
    }

    @Override
    public void calculeActionPointDesAction() throws Exception{
        Soldat s=(Soldat)protagoniste;
        tempActivite=s.tempNecessarieDesActionBase(ActionType.FEU_VISER);
    }

    @Override
    public BaseAction clone() {
        return new ViserFeuAction(this);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public GeneriqueArme.FeuMode getMode() {
        return mode;
    }
    
     
    
}
