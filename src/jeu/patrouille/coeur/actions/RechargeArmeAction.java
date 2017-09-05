/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.GeneriquePiece;

/**
 *
 * @author appleale
 */
public class RechargeArmeAction extends BaseAction{
  
    public RechargeArmeAction(GeneriquePiece protagoniste,GeneriquePiece antagoniste) {
        super(ActionType.ARME_RECHARGE, -1, -1, -1, -1,protagoniste , antagoniste);

    }

    @Override
    public void calculeActionPointDesAction() throws Exception {
       if(protagoniste==antagoniste) tempActivite= type.TN();
       else tempActivite=2;
    }

}
