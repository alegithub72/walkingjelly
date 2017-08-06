/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.armes.AK74;
import jeu.patrouille.coeur.armes.ArmeGenerique;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class AIJoeur extends GeneriqueJoeurs{

    public AIJoeur(int jeur,Carte c) {
        super(jeur,c);
        costruireHostile();
    
    }
    
    @Override
    public void getCommand(){
        BaseAction best=null;
        for(int k=0;k<equipe.length;k++){
            AISoldat s=(AISoldat)equipe[k];
            BaseAction a=s.preferredAction();
            if(best==null || 
                    a.getTempActivite()>best.getTempActivite())
            {
                best=a;
            }
        }
        selectionee=best.getProtagoniste();
        actual=best;
    }
     void costruireHostile() {
        AISoldat s = new AISoldat("Leader", "Tribu ", 5, 5, 5, 4,
                4, 5, 0, 7, 8,Piece.Direction.S,this);
            ArmeGenerique a[] = {new AK74()};
            s.setArmeEquip(a);
            s.setClassement(Soldat.CLASS_SGT);
            s.setLeaderComportement(0, 0, 0, 0);
            s.setArraN(0);
        equipe[0] = s;
        
        s = new AISoldat("Militia", "Taleban A", 5, 5, 5, 4,
                4, 7, 0, 7, 6,Piece.Direction.S,this);
            ArmeGenerique a1[] = {new AK74()};
            s.setArmeEquip(a1);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(1);
        equipe[1] = s;
        
        s = new AISoldat("Militia", "Taleban B", 5, 5, 5, 4,
                4, 5, 0, 7, 6,Piece.Direction.S,this);
            ArmeGenerique a2[] = {new AK74()};
            s.setArmeEquip(a2);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(2);
        equipe[2] = s;
        
        s = new AISoldat("Militia", "Taleban C", 5, 5, 5, 4,
                4, 5, 0, 7, 6,Piece.Direction.S,this);
            ArmeGenerique a3[] = {new AK74()};
            s.setArmeEquip(a3);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(3);
        equipe[3] = s;
    }  
}
