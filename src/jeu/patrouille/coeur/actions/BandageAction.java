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
    GeneriquePiece item;    public BandageAction(GeneriquePiece protagoniste,GeneriquePiece antagoniste) {
        super(ActionType.LACHER_OBJ, -1, -1, -1, -1,protagoniste , antagoniste);
        tempActivite=0;
    }
    
}
