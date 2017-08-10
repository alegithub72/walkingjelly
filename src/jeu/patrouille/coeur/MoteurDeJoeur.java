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
public class MoteurDeJoeur implements  Runnable{
    
    
    List<GraficCarteInterface> listgrafic;
    public static final int JEUR_HOSTILE=10;
        public static final int JEUR_US=20;
    Carte c;
    GeneriqueJoeurs jUS, jHOST;
    Piece[] patrouille ;
    Piece[] hostile ;
    int turn;
    int activeJeur;
    int iniativeWinner;
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

    public void initJeours(){
        Soldat leaderUS = jUS.findSquadLeader();
        Soldat leaderHOST = jHOST.findSquadLeader();
        int tnUS = jUS.dice(10) - leaderUS.getCC();
        int tnHoSt = jHOST.dice(10) - leaderHOST.getCC();
        if (tnUS < tnHoSt) {
            activeJeur=JEUR_US;
            iniativeWinner=JEUR_US;

        } else {
            activeJeur=JEUR_HOSTILE;
            iniativeWinner=JEUR_HOSTILE;
        }
    
    }
    public void changeJeur() {


        if(activeJeur==JEUR_US) activeJeur=JEUR_HOSTILE;
        else activeJeur=JEUR_US;

       

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
    
    public void startTurn(){
        
    Thread t=new Thread(this);
    initJeours();    
    t.start();
    
    
    }
    public void resolveTurnActions(){        
        turn++;
        List<BaseAction> listAllActionUS=new ArrayList<>();
        List<BaseAction> listAllActionHost=new ArrayList<>();
        for(int tdInc=1;tdInc<=10;tdInc++){
         
            for(int k=0;k<patrouille.length;k++){
                listAllActionUS.addAll(patrouille[k].getBaseActionSum(tdInc));
            }
            
            for(int k=0;k<hostile.length;k++){
                listAllActionHost.addAll(hostile[k].getBaseActionSum(tdInc));
            }            
            System.out.println("-----------STEP  "+tdInc+"---------------------");
            System.out.println("-listAllHost-->"+listAllActionHost.size());
            System.out.println("-listAllUS-->"+listAllActionUS.size());            
            playStep(tdInc,listAllActionUS,listAllActionHost );  
          
            listAllActionUS=new ArrayList<>();
            listAllActionHost=new ArrayList<>();      
           //break;
        }

    }   

    @Override
    public void run() {
        
        resolveTurnActions();
    }
 
      void playStep(int td,List<BaseAction> listUSAll,List<BaseAction> listHostAll){
        
       if(iniativeWinner==JEUR_US) 
        listUSAll.addAll(listHostAll);
        else 
        listHostAll.addAll(listUSAll);
        
        BaseAction[] arrayOrderd=new BaseAction[listUSAll.size()];
        for(BaseAction b:listUSAll){
            System.out.println(b);
        }
        System.out.println("-----------size all----"+listUSAll.size()+"--------SORTED--------------------------");
        
        Arrays.sort(listUSAll.toArray( arrayOrderd), BaseAction.baseActionCompratorImpl);
        int k=0;
        do {
          
            if(allAnimFinished() && k<arrayOrderd.length){
            BaseAction b=arrayOrderd[k];
            Soldat s=(Soldat)b.getProtagoniste();
            //s.resetAction();
            BaseAction clone=b.clone();
            System.out.println("--clone--->"+clone+"<-----");
            //cosi sono valide le posizioni di tutti.....
            playAllGraficInterface(clone);
              k++;
           
            //c.makeAction((Soldat)b.getProtagoniste(), b);
            }     
            //break;
        }while(k<arrayOrderd.length);
        System.out.println("fine turno");
        
        
    
    }
    private boolean allAnimFinished(){
        boolean b=true;
        for(GraficCarteInterface c:listgrafic){
            b=b && c.isAnimFinished();
        }
        return b;
    }
    public void playGame(){
    initJeours();
    do{
         
         commandDeliveryJaeurActiveTurn();
         changeJeur();
         commandDeliveryJaeurActiveTurn();
         resolveTurnActions();
         changeJeur();
    
        }while(conditionVictoire()!=-1);
    
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
    
      void   playAllGraficInterface(BaseAction b){
        for (GraficCarteInterface g : listgrafic) {
            g.play(b);
        }
    }
     
     
}
