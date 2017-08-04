/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur;

import java.io.IOException;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.armes.AK74;
import jeu.patrouille.coeur.armes.ArmeGenerique;
import jeu.patrouille.coeur.armes.BenelliM3;
import jeu.patrouille.coeur.joeurs.AIJoeur;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.joeurs.KeyboardJoeur;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class MoteurDeJoeur {
    
    
    public static final int EQUIPE_SIZE_US=4;
    public static final int EQUIPE_SIZE_HOSTILE=4;
    Carte c;
    GeneriqueJoeurs jUS, jHOST;
    Piece[] patrouille = new Soldat[EQUIPE_SIZE_US];
    Piece[] hostile = new AISoldat[EQUIPE_SIZE_HOSTILE];
    int turn;
    public MoteurDeJoeur() throws IOException{
        initGame();
    }

    void initGame() throws IOException {
        c = new Carte("src/mapDesert.txt");
        costruirePatrouille();
        costruireHostile();
        jUS = new KeyboardJoeur(patrouille);
        jHOST = new AIJoeur(hostile);
        displacementEquipeHost();
        displacementEquipeUS();
    }
    public void displacementEquipeUS(){
        for(int k=0;k<patrouille.length;k++){
            c.desplacementSoldat(patrouille[k], 30, k+20);

            
        }
    }
    public void displacementEquipeHost(){
        for(int k=0;k<hostile.length;k++){
            c.desplacementSoldat(hostile[k], 2, k);

        }
            
    }
    public void costruirePatrouille() {
        Soldat s = new Soldat("Sgt.", "Smith", 7, 8, 6, 5, 6,
                5, 5, 8, 9,Piece.Direction.N);
            ArmeGenerique a[] = {new BenelliM3()};
            s.setArmeEquip(a);
            s.setClassement(Soldat.CLASS_SGT);
            s.setArraN(0);
        patrouille[0] = s;
        
        s = new Soldat("Rifleman", "Williams", 7, 8, 6, 5, 6,
                5, 5, 8, 8,Piece.Direction.N);
            ArmeGenerique a1[] = {new BenelliM3()};
            s.setArmeEquip(a1);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(1);
        patrouille[1] = s;
        
        s = new Soldat("Rifleman", "Miller", 7, 8, 6, 5, 6,
                5, 5, 8, 8,Piece.Direction.N);
            ArmeGenerique a2[] = {new BenelliM3()};
            s.setArmeEquip(a2);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(2);
        patrouille[2] = s;
        
        s = new Soldat("Rifleman", "Anderson", 7, 8, 6, 5, 5, 
                5, 5, 8, 8,Piece.Direction.N);
            ArmeGenerique a3[] = {new BenelliM3()};
            s.setArmeEquip(a3);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(3);
        patrouille[3] = s;

    }

    public void costruireHostile() {
        AISoldat s = new AISoldat("Leader", "Tribu ", 5, 5, 5, 4,
                4, 5, 0, 7, 8,Piece.Direction.S);
            ArmeGenerique a[] = {new AK74()};
            s.setArmeEquip(a);
            s.setClassement(Soldat.CLASS_SGT);
            s.setLeaderComportement(0, 0, 0, 0);
            s.setArraN(0);
        hostile[0] = s;
        
        s = new AISoldat("Militia", "Taleban A", 5, 5, 5, 4,
                4, 7, 0, 7, 6,Piece.Direction.S);
            ArmeGenerique a1[] = {new AK74()};
            s.setArmeEquip(a1);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(1);
        hostile[1] = s;
        
        s = new AISoldat("Militia", "Taleban B", 5, 5, 5, 4,
                4, 5, 0, 7, 6,Piece.Direction.S);
            ArmeGenerique a2[] = {new AK74()};
            s.setArmeEquip(a2);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(2);
        hostile[2] = s;
        
        s = new AISoldat("Militia", "Taleban C", 5, 5, 5, 4,
                4, 5, 0, 7, 6,Piece.Direction.S);
            ArmeGenerique a3[] = {new AK74()};
            s.setArmeEquip(a3);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setAggresiveComportement(0, 0, 0, 0);
            s.setArraN(3);
        hostile[3] = s;
    }

    public void startTurn() {
        Soldat leaderUS = jUS.findSquadLeader();
        Soldat leaderHOST = jHOST.findSquadLeader();
        int tnUS = jUS.dice(10) - leaderUS.getCC();
        int tnHoSt = jHOST.dice(10) - leaderHOST.getCC();
        if (tnUS < tnHoSt) {
            commandDeliveryUSTurn();//START US TEAM
        } else {
            commandDeliveryHOSTurn();//Start HOSTxILE TEAM
        }

    }

    public void commandDeliveryUSTurn() {
        BaseAction ac = null;
        do {
            ac = jUS.getCommand();
            Piece p = ac.getProtagoniste();
            Soldat ps = null;
            if (p.getPieceType() == Piece.ActeurType.SOLDAT) {
                ps = (Soldat) p;
            }
            p.addAction(ac);
           
        } while (ac == null);
    }

    public void commandDeliveryHOSTurn() {
        BaseAction ac = null;
        do {
            ac = jHOST.getCommand();
            Piece p = ac.getProtagoniste();
            Soldat ps = null;
            if (p.getPieceType() == Piece.ActeurType.SOLDAT) {
                ps = (Soldat) p;
            }
            p.addAction(ac);
        } while (ac == null);        

    }
    public Carte getCarte(){
    return c;
    }

    public Piece[] getHostile() {
        return hostile;
    }

    public Piece[] getPatrouille() {
        return patrouille;
    }
    public void playTurn(){
    turn++;
    }
    public void endTurn(){
        
        if(conditionVictoire()==GeneriqueJoeurs.JOEUR_US){
            
        }else if(conditionVictoire()==GeneriqueJoeurs.JOEUR_HOST){
            
        }else playTurn();
    }
    public int conditionVictoire(){
    return -1;
    }
    
    public static void main(String[] args) {

    
    }    
}
