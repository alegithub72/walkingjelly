/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class FeuAction extends BaseAction{
    double angle;
    public FeuAction(Piece protagoniste,Piece antagonieste){
        super(ActionType.FEU, -1, -1,-1, -1, protagoniste, antagonieste);
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }

     FeuAction(FeuAction act) {
        super(act.type, act.i0, act.j0, act.i1, act.j1,null , null);
        if(act.antagoniste!=null ) antagoniste=act.antagoniste.clonerPiece();
        if(act.protagoniste!=null) protagoniste=act.protagoniste.clonerPiece();
         this.angle=act.angle;
     
    }

    @Override
    public void calculeActionPointDesAction() throws Exception{
        Soldat s=(Soldat)protagoniste;
        tempActivite=s.tempNecessarieDesActionBase(ActionType.FEU);

       // super.calculeActionPointDesActions(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseAction clone() {
        return new FeuAction(this);
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
    
     
    
}
