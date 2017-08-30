/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.equipments.armes.AK74;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.Soldat.Classment;

/**
 *
 * @author Alessio Sardaro
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
        selectionee=(Piece)best.getProtagoniste();
        actual=best;
    }
     void costruireHostile() {
        AISoldat s = new AISoldat("Leader", "Tribu ", 5, 5, 5, 4,
                4, Soldat.FULL_SANTE, null, 7, 8,Piece.Direction.S,this);
            GeneriqueArme a[] = {new AK74()};
            s.setArmeEquip(a);
            s.setClassement(Classment.SERGENT);
            s.setLeaderComportement(0, 0, 0, 0);
            s.setArraN(0);
            s.setArmeUtilise(a[0]);
        equipe[0] = s;
        
        s = new AISoldat("Militia", "Taleban A", 5, 5, 5, 4,
                4, Soldat.FULL_SANTE, null, 7, 6,Piece.Direction.S,this);
            GeneriqueArme a1[] = {new AK74()};
            s.setArmeEquip(a1);
            s.setClassement(Classment.SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(1);
            s.setArmeUtilise(a1[0]);
           
        equipe[1] = s;
        
        s = new AISoldat("Militia", "Taleban B", 5, 5, 5, 4,
                4, Soldat.FULL_SANTE, null, 7, 6,Piece.Direction.S,this);
            GeneriqueArme a2[] = {new AK74()};
            s.setArmeEquip(a2);
            s.setClassement(Classment.SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(2);
            s.setArmeUtilise(a2[0]);
        equipe[2] = s;
        
        s = new AISoldat("Militia", "Taleban C", 5, 5, 5, 4,
                4, Soldat.FULL_SANTE, null, 7, 6,Piece.Direction.S,this);
            GeneriqueArme a3[] = {new AK74()};
            s.setArmeEquip(a3);
            s.setClassement(Classment.SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(3);
            s.setArmeUtilise(a3[0]);            
        equipe[3] = s;
    }  
}
