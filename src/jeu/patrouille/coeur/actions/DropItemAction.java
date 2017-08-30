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
public class DropItemAction extends BaseAction{
    GeneriquePiece item;    public DropItemAction(int i,int j,GeneriquePiece owner,GeneriquePiece item) {
        super(ActionType.LACHER_OBJ, -1, -1, i, j,owner , item);
        tempActivite=0;
    }
    
}
