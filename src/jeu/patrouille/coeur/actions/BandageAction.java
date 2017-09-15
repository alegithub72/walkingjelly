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
public class BandageAction extends BaseAction{
  
    public BandageAction(GeneriquePiece protagoniste,GeneriquePiece antagoniste) {
        super(ActionType.BANDAGE, -1, -1, -1, -1,protagoniste , antagoniste);

    }

    @Override
    public void calculeActionPointDesAction() throws Exception {
        tempActivite=(int) type.TN();
    }

}
