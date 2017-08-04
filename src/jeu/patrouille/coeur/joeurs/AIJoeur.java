/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class AIJoeur extends GeneriqueJoeurs{

    public AIJoeur(Piece[] equipe) {
        super(equipe);
    }
    
    @Override
    public BaseAction getCommand(){
        BaseAction best=null;
        for(int k=0;k<equip.length;k++){
            AISoldat s=(AISoldat)equip[k];
            BaseAction a=s.preferredAction();
            if(best==null || a.valorActionPointDesActions()>best.valorActionPointDesActions()) best=a;
        }
        return best;
    }

}
