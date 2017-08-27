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
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.parts.LesionEstimation;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
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
        try {
            resolveAllRondeActions();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        

    }


    
   public  void resolveAllRondeActions()throws Exception{        
        turn++;
        List<BaseAction> listAllActionUS=new ArrayList<>();
        List<BaseAction> listAllActionHost=new ArrayList<>();
        System.out.println("----------------------------- RESOLVE TURN START-----------"+turn+"---------------------------------");
        for(int td=1;td<=10;td++){
         System.out.println("--------------------------->TD--CONSIDERED--START-->"+td+"<------------");
            for(int k=0;k<patrouille.length;k++){
                List<BaseAction> l=getBaseActionSum(td,(Soldat)patrouille[k]);

               // System.out.println("---Action sum--->"+l.size());
                listAllActionUS.addAll(l);
            }
            
            for(int k=0;k<hostile.length;k++){
                List<BaseAction> l=getBaseActionSum(td,(Soldat)hostile[k]);


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
    public List<BaseAction> getBaseActionSum(int td,Soldat sold) throws Exception{
        //System.out.println("----GET ACTION SUM START--------->"+spreadDone);
        if(!sold.isSpreadDone()) {

            transformActionPool(sold);
        }
        int sum=0;
        List<BaseAction> list =new ArrayList<>();
        for (int h=0;h<sold.actionSize();h++) {
             sum=sum+sold.nextAction(h).getTempActivite();
             if(sum<=td) {
                 if(!sold.nextAction(h).isUsed()){
                        list.add(sold.nextAction(h));
                        Soldat s=(Soldat)sold.nextAction(h).getProtagoniste();
                        int rollDice=sold.getBoss().dice(10);
                        sold.nextAction(h).setOrdreInitiative( rollDice -s.getCC());
                        sold.nextAction(h).setUsed(true);                     
                 }

                 
             }
                 
        }
       // System.out.println("----GET ACTION SUM END--------->");
        return list;
    }
      private void transformActionPool(Soldat s) throws Exception {
        List<BaseAction> newActionPool=new ArrayList<>(10);
        System.out.println("*******SRPEAD START*******>");
        
        for(int h=0;h<s.actionSize();h++){
            ActionType type1=s.nextAction(h).getType();
            System.out.println("----TD COST--------->"+s.nextAction(h).getTempActivite());
            if(type1==ActionType.MARCHE){
                List<BaseAction> l=s.nextAction(h).spreadAction(); 
                System.out.println(" list spread ->"+l.size());
                newActionPool.addAll(l);
            }else newActionPool.add(s.nextAction(h));
        }
        
        s.setSpreadDone(true);
        if(newActionPool.size()>0) s.setActionsPool(newActionPool);
        System.out.println("******SRPEAD END*****>");
     
    }
      
      
   void resetAllSoldatActionPool(){
       for (Piece patrouille1 : this.patrouille) {
           Soldat p=(Soldat)patrouille1;
           p.resetRondCheck();
           if(!p.isKIA()){
               p.resetActionPoool();
               p.bleedingTache();
               p.inconscentTache();
           }
           
       }
       for (Piece hostile1 : hostile) {
           Soldat host=(Soldat)hostile1;
          
           host.resetRondCheck();
          if(!host.isKIA()){
            host.resetActionPoool();              
            host.bleedingTache();
            host.inconscentTache(); 
          }
          
       }
   
   
   }
   void resetAllSoldatStep(){
   for(Piece p:this.patrouille)
       ((Soldat)p).setStepScared(false);
   for(Piece h:this.hostile)
       ((Soldat)h).setStepScared(false);
   
   
   }
   void resolveTempPas(int td,List<BaseAction> listUSAll,List<BaseAction> listHostAll)throws Exception{
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
        
        
        if( arrayOrderd.length>0){
        int k=0;
       
            while(k<arrayOrderd.length) {


                BaseAction act=arrayOrderd[k];
                //BaseAction clone=act.clone();
                Soldat s=(Soldat)act.getProtagoniste();
                //s.resetAction();
                BaseAction fugitivAct=null;
                //if(ch) act=fugitivAct;//TODO   fugitiv action
                if(!s.isIncoscient()&&!s.isImmobilize()&&!s.isKIA() && !s.isStepScared()){
                    
                   // System.out.println("--MJ PLAY GRAFIC--->"+act+"<----->"+((act.getProtagoniste()!=null)?act.getProtagoniste().toStringSimple():" no protagoniste")+"<----------");
                    //cosi sono valide le posizioni di tutti.....
                    if( act.getType()==ActionType.FEU) makeFeuAction(td,act);
                    playAllGraficInterface(act);
    

                    int zz=0;
                    System.out.println("------------Zzzzzzzzzzzzz="+allAnimOn());
                    while(allAnimOn());
                    System.out.println("------------Svegliaaaaaaaa="+allAnimOn());

                    if( act.getType()!=ActionType.FEU) makeAction((Soldat)act.getProtagoniste(), act);   
                    refreshAllGraficInterface();
                }else System.out.println("--------->KIA OR CHOKET NOTA ANIM");
                 //TODO vedere per aggiorantre la mappa quando!!!!
                 k++;
                
                
                //break;
            }
            System.out.println("fine TD pas "+td);
            
        }
        
        resetAllSoldatStep();
        
    
    }
   private void makeAction(Soldat s,BaseAction a)throws Exception{
       if(a.getType()==ActionType.MARCHE){
           makeMarcheAction(s, a);
       }
    
   }
    private boolean allAnimOn(){
        boolean b=false;
        for(GraficCarteInterface c:listgrafic){
            b=b || c.isAnimOn();
        }
        return b;
    }
    
    public void playGame()throws Exception{
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
        System.out.println("*************************MARCHE MAKE*************************");
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
        
        s.setAction(ActionType.MARCHE);
        System.out.println("updated terrain --soldat-"+s.toStringSimple()+"---->"+a.getI1()+"--->"+a.getJ1());
        s.setI(i1);
        s.setJ(j1);
        System.out.println("updated position -soldat--->"+s.toStringSimple()+"--->"+a.getI1()+","+a.getJ1());
        System.out.println("**************************MARCHE MAKE**************************");        
    }    


        public void makeFeuAction(int td,BaseAction act) throws ModeDeFeuException,LoadMagazineFiniException{
            System.out.println("*************************FEUUUU MAKE*****************************");
            Soldat s=(Soldat)act.getProtagoniste();
            Soldat target=(Soldat)act.getAntagoniste();
            int i1=act.getI1(),j1=act.getJ1();
           
            //TODO generalizzare in FXcarte....
            int score=s.fire(act);
            System.out.println("score ="+score);
            Lesion[] llist=new Lesion[score];
            if(target!=null ){
                i1=target.getI();j1=target.getJ();
                
            }
            PointCarte line[]= Carte.getLigne(s.getI(), s.getJ(), i1,j1);
            //Terrain cover= c.getPointCarte(line[line.length-1].getI(),line[line.length-1].getJ());
            GeneriqueArme arm=s.getArmeUtilise();
            for (int sc=0;sc<score;sc++){
                int location=s.getBoss().dice(10);
                int blessure=s.getBoss().dice(10)-arm.getEDP();
                Lesion l= lesionEsti.getLesion(location, blessure,turn);   
                //TODO is not enough the last cover
                //TODO for each cove r on the los of the target
                System.out.println(l);
                boolean coverBool=coverRoll(line, s);
                if(coverBool && target!=null) target.setObjective(true);
                int bli=0;
                if(target!=null)bli=target.getBlindage(l.getLocation());
                
                int armorCheck=bli+arm.getEMB();
                int armorRoll=s.getBoss().dice(10);
                System.out.println("cover passed="+coverBool +" armorROLL<=armorCheck "
                        +armorRoll+"<="+armorCheck+" armor check= "+(bli>0 ));
                if(((armorRoll==1 && armorRoll<=armorCheck )|| bli==0)&& coverBool
                        ) {
                    System.out.println("--PASSED--->"+l);
                    llist[sc]=l;
                }else System.out.println("---->NOT PASSED");
                

            }   
            
            for (Lesion ln : llist) {
                if(target!=null && !target.isKIA() && ln!=null){
                   target.addLesion(ln);
                   target.blessure(ln);
                   if(target.isObjective())target.shellShockTest();
                       
                   if(target.isChoc()&& !target.isImmobilize()) {
                       target.setStepScared(true);                       
                       addFuirLontain(target, td);
                       System.out.println(s+"  -->schoked ");
                   }
                   System.out.println(target.toStringSimple()+":--add--->"+ln);
                }
                
            }
            


            //TODO choose other target.........if full automatic in the line of sight

        System.out.println("**************************FEUUUU MAKE----FINE*****************************");            
    }
        
        
    //TODO rivedere perche fa un de casino
    public void addFuirLontain(Soldat s,int td){
        try{
        s.resetTempDispoleNotUse();
        BaseAction act=MarcheAction.marcheLointain(s);
        if(act!=null){
            BaseAction noact=new BaseAction(ActionType.PA_ACTION, -1, -1, -1, -1, s, null);
            noact.setTempActivite(td);
            s.addAction(noact);
            s.addAction(act);
            s.setSpreadDone(false);
        }
        else System.out.println("Non c'e' tempo.....");
        System.out.println(" fuggire lontato a----> "+act);
        }catch(Exception ex){
            System.err.println("Non e' possibile aggiungere una azione runaaway");
            ex.printStackTrace();
            
        }

    }        
     boolean coverRoll(PointCarte line[],Soldat s){
         for (PointCarte pointCarte : line) {
             Terrain t=c.getPointCarte(pointCarte.getI(), pointCarte.getJ());
             GeneriqueArme arm=s.getArmeUtilise();
             Terrain.Consistance con=t. getConsistance();
             System.out.println(t+" consitstence="+con);
             if(con!=Terrain.Consistance.NO){
                 
                 int coverNum=arm.getCoverPenetration(con)+arm.getEDP();
                 if(coverNum==GeneriqueEquipment.NOTVALUE) return false;
                 int rollCover=s.getBoss().dice(10);
                 System.out.println("rollCove "+rollCover+" coverNum="+coverNum);
                 if(rollCover>coverNum ) return false;                 
             }

             }
             
         return true;
     }
}
