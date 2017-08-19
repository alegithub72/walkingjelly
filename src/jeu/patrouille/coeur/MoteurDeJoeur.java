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
import javafx.application.Platform;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.armes.GeneriqueArme;
import jeu.patrouille.coeur.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.LesionEstimation;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.coeur.pieces.Lesion;
/**
 *
 * @author Alessio Sardaro
 */
public class MoteurDeJoeur implements Runnable{
    
    
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
    Thread threadTurn=null;
    LesionEstimation lesionEsti;
    
    
    public MoteurDeJoeur(GeneriqueJoeurs jUS,GeneriqueJoeurs jHOST,Carte carte) throws IOException{
        this.c=carte;
        this.jUS=jUS;
        this.jHOST=jHOST;
        patrouille=jUS.getEquip();
        hostile=jHOST.getEquip();
        listgrafic=new ArrayList();
        lesionEsti=new LesionEstimation();
        
     
       
    }
    private GeneriqueJoeurs getJeur(int type){
    if(type==JEUR_HOSTILE)
        return jHOST;
    return jUS;
    
    
    }
    public GeneriqueJoeurs getActiveJeur() {
        if(activeJeur==JEUR_US) return jUS;
        else return jHOST;
 
    }
    public void add(GraficCarteInterface g){
        listgrafic.add(g);
    }




    public void debutJeours(){
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
    public void changeJoueur() {


        if(activeJeur==JEUR_US) activeJeur=JEUR_HOSTILE;
        else activeJeur=JEUR_US;

       

    }
    
    public void commandLivrasionActiveJoueur() {
        BaseAction ac = null;
        do {
            getActiveJeur().getCommand();
            Piece p = jUS.getPieceSelectionee();
            Soldat ps = null;
            if (p.getPieceType() == Piece.ActeurType.SOLDAT) {
                ps = (Soldat) p;
            }
            try{
            p.addAction(ac);
            }catch(Exception e){};
           
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
    
    public void debutRond(){
        System.out.println("start thraed mj");
        if(threadTurn==null ){
         threadTurn=new Thread(this);
        threadTurn.setDaemon(true);
        threadTurn.start();
        }else if(!threadTurn.isAlive()){
        threadTurn=new Thread(this);
        threadTurn.setDaemon(true);
        threadTurn.start();
        }
     
        //    run();
      //  Platform.runLater(this);
   //run()
    }

    @Override
    public void run() {
        System.out.println("-------------------RUN THREAD<----------------------"+Thread.currentThread().getId());
        debutJeours();   
        System.out.println("--------------------------->start tread turn :"+turn);
        resolveAllRondeActions();

    }


    
   public  void resolveAllRondeActions(){        
        turn++;
        List<BaseAction> listAllActionUS=new ArrayList<>();
        List<BaseAction> listAllActionHost=new ArrayList<>();
        System.out.println("----------------------------- RESOLVE TURN START-----------"+turn+"---------------------------------");
        for(int td=1;td<=10;td++){
         System.out.println("--------------------------->TD--CONSIDERED--START-->"+td+"<------------");
            for(int k=0;k<patrouille.length;k++){
                List<BaseAction> l=patrouille[k].getBaseActionSum(td);
               // System.out.println("---Action sum--->"+l.size());
                listAllActionUS.addAll(l);
            }
            
            for(int k=0;k<hostile.length;k++){
                List<BaseAction> l=hostile[k].getBaseActionSum(td);
                //System.out.println("--Action sum---->"+l.size());
                listAllActionHost.addAll(l);
            }            
           // System.out.println("-----------TD CONSIDEREDED "+td+"---------------------");
            //System.out.println("-listAllHost-->"+listAllActionHost.size());
           // System.out.println("-listAllUS-->"+listAllActionUS.size()); 
            
            resolveTempPas(td,listAllActionUS,listAllActionHost );  

            listAllActionUS=new ArrayList<>();
            listAllActionHost=new ArrayList<>();   
            System.out.println("--------------------------->TD--CONSIDERED--END-->"+td+"<------------");
           //break;
        }
        System.out.println("reset action pool");
        resetAllSoldatActionPool();
        System.out.println("-----------------------------RESOLVE TURN  END-----------"+turn+"---------------------------------");
        reMountMenuItemsAndScroll();
    }   

  
   void resetAllSoldatActionPool(){
   for(int k=0;k<this.patrouille.length;k++)
       patrouille[k].resetActionPoool();
   for(int k=0;k<hostile.length;k++)
       hostile[k].resetActionPoool();
   
   
   }
   void resolveTempPas(int td,List<BaseAction> listUSAll,List<BaseAction> listHostAll){
        BaseAction[] arrayOrderd=null;
       if(iniativeWinner==JEUR_US) 
       {
           listUSAll.addAll(listHostAll);
           arrayOrderd=new BaseAction[listUSAll.size()];
           Arrays.sort(listUSAll.toArray( arrayOrderd), BaseAction.baseActionCompratorImpl);
           System.out.println("-----------JEUR US WIN INITIATIVE---SIZE=--->"+arrayOrderd.length+"<--------SORTED--------------------------");
       }
       else{
           listHostAll.addAll(listUSAll);
           arrayOrderd=new BaseAction[listHostAll.size()];
            Arrays.sort(listHostAll.toArray(arrayOrderd),BaseAction.baseActionCompratorImpl);
            System.out.println("-----------JEUR HOSTILE WIN INITIATIVE-SIZE=--->"+arrayOrderd.length+"<--------SORTED--------------------------");            
       }    
        
        
 
       // for(BaseAction b:arrayOrderd){
       //     System.out.println(b);
       // }
        
        
        if(arrayOrderd!=null && arrayOrderd.length>0){
        int k=0;
       
            while(k<arrayOrderd.length) {


                BaseAction b=arrayOrderd[k];
                BaseAction clone=b.clone();
                Soldat s=(Soldat)b.getProtagoniste();
                //s.resetAction();

                System.out.println("--MJ PLAY STEP--->"+b+"<----->"+b.getProtagoniste().toStringSimple()+"<----------");
                //cosi sono valide le posizioni di tutti.....
                playAllGraficInterface(b);
             
//                try {
//                Thread.currentThread().wait(0);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }catch(java.lang.IllegalMonitorStateException i){
//                    i.printStackTrace();
//                }
                System.out.println("--MJ PLAY STEP------------------------------END----><-----");
                int zz=0;
                System.out.println("------------Zzzzzzzzzzzzz="+allAnimOn());
                while(allAnimOn());
                System.out.println("------------Svegliaaaaaaaa="+allAnimOn());
                
                makeAction((Soldat)b.getProtagoniste(), b);   
                refreshAllGraficInterface();
                 //TODO vedere per aggiorantre la mappa quando!!!!
                 k++;
                
                
                //break;
            };
            System.out.println("fine TD pas "+td);
            
        }
        
    
    }
   private void makeAction(Soldat s,BaseAction a){
       if(a.getType()==BaseAction.MARCHE){
           makeMarcheAction(s, a);
       }else if(a.getType()==BaseAction.FEU){
       
       }
    
   }
    private boolean allAnimOn(){
        boolean b=false;
        for(GraficCarteInterface c:listgrafic){
            b=b || c.isAnimOn();
        }
        return b;
    }
    
    public void playGame(){
    debutJeours();
    do{
         
         commandLivrasionActiveJoueur();
         changeJoueur();
         commandLivrasionActiveJoueur();
         resolveAllRondeActions();
         changeJoueur();
    
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
            g.setAnimOn(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    g.play(b);
                }
            } );
            return;
        }
    }

     void   refreshAllGraficInterface(){
         
        for (GraficCarteInterface g : listgrafic) {
            g.setAnimOn(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    g.refreshGraficCarte();
                }
            } );
            return;
        }
    }
    void reMountMenuItemsAndScroll(){
       for(GraficCarteInterface g:listgrafic){
           Platform.runLater(new Runnable() {
               @Override
               public void run() {
                   g.reMountFXCarteMenuItemsAndScroll();
               }
           });
       }
    }
    
    /**
     * @todo un sacco de lavoro
     * @param s
     * @param a 
     */
    public void makeMarcheAction(Soldat s, BaseAction a) {
        //TODO rendere effettive le modifiche .....
        
        int i0=a.getI0(),j0=a.getJ0();
        int i1=a.getI1(),j1=a.getJ1();
        if(c.terrain[i0][j0].getPiece()==s)
            c.terrain[i0][j0] .setPiece(null); 
        if(c.terrain[i0][j0].isInExtra(s))
            c.terrain[i0][j0].remvoeExtraPiece(s);
        
        System.out.println("updated terrain --null-->"+a.getI0()+"--->"+a.getJ0());
        
        if(c.terrain[i1][j1].getPiece()==null)  
            c.terrain[i1][j1].setPiece(s);
        else c.terrain[i1][j1].addExtraPiece(s);
        //TODO if enemy do a close fight...!!!!
        
        s.setAction(BaseAction.MARCHE);
        System.out.println("updated terrain --soldat-"+s.toStringSimple()+"---->"+a.getI1()+"--->"+a.getJ1());
        s.setI(i1);
        s.setJ(j1);
        System.out.println("updated position -soldat--->"+s.toStringSimple()+"--->"+a.getI1()+","+a.getJ1());
    }    


        public void makeFeuAction(BaseAction act) throws ModeDeFeuException,LoadMagazineFiniException{
            
            Soldat s=(Soldat)act.getProtagoniste();
            Soldat target=(Soldat)act.getAntagoniste();
            GeneriqueArme arme=s.getArmeUtilise();
            double dist=c.distance(act.getI0(), act.getJ0(), act.getI1(), act.getJ1(), FXCarte.TILE_SIZE);
            int shotN=s.fire(dist);
            int dCM=0;
            if(arme.getMF()==GeneriqueArme.MODE_FEU_BU) 
                dCM=dCM-1;
            else if(arme.getMF()==GeneriqueArme.MODE_FEU_FA)
                dCM=dCM-3;

            if(s.getAction()==BaseAction.MARCHE)dCM=dCM-2;//TODO questo non so se si puo fare  se marcia non fa fuoco ...pensare ad una soluzione...
            else if(s.getAction()==BaseAction.COURS)dCM=dCM-4;
            else if(act.getType()==BaseAction.VISER_FEU)dCM=dCM+1;



            if(s.getPose()==Piece.Pose.PRONE) dCM=dCM+1;
            if(target.getAction()==BaseAction.COURS) dCM=dCM-2;
            else if(target.getAction()==BaseAction.MARCHE)dCM=dCM-1;
            if(target.getPose()==Piece.Pose.PRONE) dCM=dCM-1; 


            int score=0;
            for(int hits=0;hits<shotN;hits++){
                int dice=s.getBoss().dice(10);
                int tg=s.getArmeUtilise().porteModifier(dist)+s.getCA()+dCM;
                if(dice<=tg) score++;
            }
            //TODO choose target.........
            for (int sc=0;sc<score;sc++){
                int location=s.getBoss().dice(10);
                int blessure=s.getBoss().dice(10)-arme.getEDP();
                Lesion l= lesionEsti.getLesion(location, blessure);
                target.blessure(l);
            }
    }
     
}
