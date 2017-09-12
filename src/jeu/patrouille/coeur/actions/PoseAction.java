/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class PoseAction extends BaseAction{

    public PoseAction(Soldat protagoniste,int i,int j) {
        super(ActionType.COUCHER, -1, -1, i, j, protagoniste, null);
    }
    
}
