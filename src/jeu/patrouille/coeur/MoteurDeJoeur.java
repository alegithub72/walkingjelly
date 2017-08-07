/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class MoteurDeJoeur {
    
    
    List<GraficCarteInterface> listgrafic;
    public static final int JEUR_HOSTILE=10;
        public static final int JEUR_US=20;
    Carte c;
    GeneriqueJoeurs jUS, jHOST;
    Piece[] patrouille ;
    Piece[] hostile ;
    int turn;
    int activeJeur;
    public MoteurDeJoeur(GeneriqueJoeurs jUS,GeneriqueJoeurs jHOST,Carte carte) throws IOException{
        this.c=carte;
        this.jUS=jUS;
        this.jHOST=jHOST;
        patrouille=jUS.getEquip();
        hostile=jHOST.getEquip();
        listgrafic=new ArrayList();
        initGame();
    }

    public GeneriqueJoeurs getActiveJeur() {
        if(activeJeur==JEUR_US) return jUS;
        else return jHOST;
 
    }
    public void add(GraficCarteInterface g){
        listgrafic.add(g);
    }
    void initGame() throws IOException {



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


    public void startTurn() {
        Soldat leaderUS = jUS.findSquadLeader();
        Soldat leaderHOST = jHOST.findSquadLeader();
        int tnUS = jUS.dice(10) - leaderUS.getCC();
        int tnHoSt = jHOST.dice(10) - leaderHOST.getCC();
        if (tnUS < tnHoSt) {
            activeJeur=JEUR_US;

        } else {
            activeJeur=JEUR_HOSTILE;

        }
        commandDeliveryJaeurActiveTurn();

    }
    
    public void commandDeliveryJaeurActiveTurn() {
        BaseAction ac = null;
        do {
            getActiveJeur().getCommand();
            Piece p = jUS.getPieceSelectionee();
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
        List<BaseAction> listAllUS=new ArrayList<>();
        List<BaseAction> listAllHost=new ArrayList<>();
        for(int tdInc=1;tdInc<=10;tdInc++){
         
            for(int k=0;k<patrouille.length;k++){
                listAllUS.addAll(patrouille[k].getBaseActionSum(tdInc));
            }
            
            for(int k=0;k<hostile.length;k++){
                listAllHost.addAll(hostile[k].getBaseActionSum(tdInc));
            }            
            System.out.println("-----------STEP  "+tdInc+"---------------------");
            System.out.println("-listAllHost-->"+listAllHost.size());
            System.out.println("-listAllUS-->"+listAllUS.size());            
            playStep(tdInc,listAllUS,listAllHost );  
            listAllUS=new ArrayList<>();
            listAllHost=new ArrayList<>();            
        }

    }
 
    public  void playStep(int td,List<BaseAction> listUSAll,List<BaseAction> listHostAll){
        
        listUSAll.addAll(listHostAll);
        BaseAction[] arrayOrderd=new BaseAction[listUSAll.size()];
        for(BaseAction b:listUSAll){
            System.out.println(b);
        }
        System.out.println("-----------size all----"+listUSAll.size()+"--------SORTED--------------------------");
        
        Arrays.sort(listUSAll.toArray( arrayOrderd), BaseAction.baseActionCompratorImpl);
 
        for (BaseAction b : arrayOrderd) {
            Soldat s=(Soldat)b.getProtagoniste();
            //s.resetAction();
            BaseAction clone=b.clone();
            System.out.println("--clone--->"+clone+"<-----");
            c.makeAction((Soldat)b.getProtagoniste(), b);
            
            playAllGraficInterface(clone);
            
        }

        
        
    
    }
    public void endTurn(){
        
        if(conditionVictoire()==GeneriqueJoeurs.JOEUR_US){
            
        }else if(conditionVictoire()==GeneriqueJoeurs.JOEUR_HOST){
            
        }else playTurn();
    }
    public int conditionVictoire(){
    if(hostile.length==0)  return GeneriqueJoeurs.JOEUR_US;
    else if(patrouille.length==0) return GeneriqueJoeurs.JOEUR_HOST;
    return -1;
    }
    
    public static void main(String[] args) {

    
    }    

    public void setActiveJeur(int activeJeur) {
        this.activeJeur = activeJeur;
    }
    
    void playAllGraficInterface(BaseAction b){
        for (GraficCarteInterface g : listgrafic) {
            g.play(b);
        }
    }
}
