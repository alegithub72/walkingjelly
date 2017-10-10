/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.actions.helper.LigneFeuObjectifs;
import jeu.patrouille.coeur.actions.helper.Target;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class FeuAction extends BaseAction{
    double angle;
    GeneriqueArme.FeuMode mode;
    LigneFeuObjectifs targets;
    public FeuAction(Piece protagoniste,GeneriqueArme.FeuMode mode){
        super(ActionType.FEU, -1, -1,-1, -1, protagoniste, null);
        this.mode=mode;
        this.antagoniste=null;
        this.targets=new LigneFeuObjectifs();
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }
    public FeuAction(ActionType type,Piece protagoniste,GeneriqueArme.FeuMode mode){
        super(type, -1, -1,-1, -1, protagoniste, null);
        this.mode=mode;
        this.antagoniste=null;
        this.targets=new LigneFeuObjectifs();
      // dist= Carte.distance(protagoniste.getI(), this.protagoniste.getJ(), i1, j1, FXCarte.TILE_SIZE);
    }    

     FeuAction(FeuAction act) {
        super(act.type, act.i0, act.j0, act.i1, act.j1,null , null);
        if(act.antagoniste!=null ) antagoniste=act.antagoniste.clonerPiece();
        if(act.protagoniste!=null) protagoniste=act.protagoniste.clonerPiece();
        this.targets=act.targets;
         this.angle=act.angle;
     
    }

    @Override
    public void calculeActionPointDesAction() throws Exception{
        Soldat s=(Soldat)protagoniste;
        tempActivite=(int)s.tempNecessarieDesActionBase(ActionType.FEU);

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

    public GeneriqueArme.FeuMode getMode() {
        return mode;
    }

    public void addTarget(Target t) {
        targets.addTarget(t);
    }

    public int size() {
        return targets.size();
    }
    public GeneriquePiece[] getTargets(){
        int size=targets.size();
        GeneriquePiece[] targetss=new GeneriquePiece[size];
            for (int i = 0; i < targetss.length; i++) {
                targetss[i]= targets.getGeneriqueTarget(i);
                
            }
        return targetss;
    } 

    
     
    
}
