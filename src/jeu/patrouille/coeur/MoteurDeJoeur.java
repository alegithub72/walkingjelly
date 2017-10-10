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
import jeu.patrouille.coeur.actions.AbstractAction;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.FeuAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.ViserFeuAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.actions.exceptions.MakeActionFailException;
import jeu.patrouille.coeur.actions.helper.LigneFeuObjectifs;
import jeu.patrouille.coeur.actions.helper.Target;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.armes.exceptions.ImpossibleRechargeArmeException;
import jeu.patrouille.coeur.equipments.armes.exceptions.IncompatibleMagazineException;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.parts.LesionEstimation;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Piece.Direction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;
import jeu.patrouille.coeur.pieces.parts.Corp;
import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.board.FXCarte;
import static jeu.patrouille.fx.board.FXCarte.TILE_SIZE;
import jeu.patrouille.util.ArrayUtil;
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
    public int td;
    
    
    public MoteurDeJoeur(GeneriqueJoeurs jUS,GeneriqueJoeurs jHOST,Carte carte) throws IOException{
        this.c=carte;
        this.jUS=jUS;
        this.jHOST=jHOST;
        patrouille=jUS.getEquip();
        hostile=jHOST.getEquip();
        listgrafic=new ArrayList();

        
     
       
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
public void rondStartTest(int turn){
   for(Piece p1: patrouille){
       Soldat s1=(Soldat) p1;
       p1.getBoss().setTurn(turn);
       if(s1.isChoc() && !s1.isKIA()) s1.shellShockTest();
   }
   for(Piece p1:hostile){
       Soldat h1=(Soldat) p1;
       p1.getBoss().setTurn(turn);
       if(h1.isChoc() && !h1.isKIA()) h1.shellShockTest();
   }

}

    
   public  void resolveAllRondeActions()throws Exception{        
        turn++;
        rondStartTest(turn);
        List<BaseAction> listAllActionUS=new ArrayList<>();
        List<BaseAction> listAllActionHost=new ArrayList<>();
        System.out.println("----------------------------- RESOLVE TURN START-----------"+turn+"---------------------------------");
        for( td=1;td<=10;td++){
         System.out.println("--------------------------->TD--CONSIDERED--START-->"+td+"<------------");
            for(int k=0;k<patrouille.length;k++){
                Soldat s1=(Soldat)patrouille[k];
                List<BaseAction> l=getBaseActionSum(td,s1);                
                listAllActionUS.addAll(l);
            }
            
            for(int k=0;k<hostile.length;k++){
                Soldat h1=(Soldat)hostile[k];
              
                List<BaseAction> l=getBaseActionSum(td,h1);
                 listAllActionHost.addAll(l);
            }            

            BaseAction[] arrayOrderd = sequenqueActionMake(listAllActionUS, listAllActionHost);
            resolveTempPas(td,arrayOrderd );  

            listAllActionUS=new ArrayList<>();
            listAllActionHost=new ArrayList<>();   
            System.out.println("--------------------------->TD--CONSIDERED--END-->"+td+"<------------");
           //break;
        }
        System.out.println("end Rond All Soldat Tache, reset action pool");
        endRondAllSoldatTache();
        refreshAllGraficInterface();
        System.out.println("-----------------------------RESOLVE TURN  END-----------"+turn+"---------------------------------");
        reMountMenuItemsAndScroll();
    }   
    public List<BaseAction> getBaseActionSum(int td,Soldat sold) throws Exception{
        //System.out.println("----GET ACTION SUM START--------->"+spreadDone);
        if(!sold.isSpreadDone()) {

            transformActionPool(sold);
        }
        int sum=0;
        int rollDice=sold.getBoss().dice(10);
        int initiativeNum=rollDice -sold.getCC();
                        
        List<BaseAction> list =new ArrayList<>();
        for (int h=0;h<sold.actionSize();h++) {
             BaseAction b=sold.nextAction(h);
             if(b!=null)sum=sum+b.getTempActivite();
             if(sum<=td) {
                 if(b!=null && !b.isUsed()){
                        list.add(sold.nextAction(h));
                        sold.nextAction(h).setOrdreInitiative( initiativeNum);                        
                        sold.nextAction(h).setUsed(true);                     
                 }

                 
             }
                 
        }
       // System.out.println("----GET ACTION SUM END--------->");
        return list;
    }
      private void transformActionPool(Soldat s) throws Exception {
        BaseAction[] newActionPool=new BaseAction[10];
        System.out.println("*******SRPEAD START*******>");
        int n=0;
        for(int h=0;h<s.actionSize();h++){
            ActionType type1=s.nextAction(h).getType();
            System.out.println("----ACTION TO SPREAD--------->"+s.nextAction(h));
            if(type1==ActionType.MARCHE || type1==ActionType.COURS){
                BaseAction[] l=s.nextAction(h).spreadAction(); 
                //System.out.println(" list spread ->"+l.length);
                n=ArrayUtil.copyArray(newActionPool, l, n);
            }else {
                newActionPool[n]=s.nextAction(h);
                n++;
              //  newActionPool.add(s.nextAction(h));
            }
        }
        
        s.setSpreadDone(true);
        s.setActionsPool(newActionPool);
        s.setActionArrayPlace(n);
        System.out.println("******SRPEAD END*****>");
     
    }
      
      
   private void endRondAllSoldatTache(){
       for (Piece patrouille1 : this.patrouille) {
           Soldat s1=(Soldat)patrouille1;
           s1.resetRondCheck();
           s1.resetActionPoool();
           if(!s1.isKIA()){

               if(s1.isBleeding()) s1.bleedingTest();
               if(s1.isIncoscient() && s1.getSante()>0 )s1.inconscentTetst();
               if(s1.getSante()<=0) s1.incoscientBleedingTest();
           }
           
       }
       for (Piece hostile1 : hostile) {
           Soldat host=(Soldat)hostile1;
           host.resetRondCheck();
           host.resetActionPoool();
          if(!host.isKIA()){
            if(host.isBleeding()) host.bleedingTest();
            if(host.isIncoscient() && host.getSante()>0 )host.inconscentTetst();
            if(host.getSante()<=0) host.incoscientBleedingTest();
          }
          
       }
   
   
   }
private   void resetAllSoldatStep(){
   for(Piece p:this.patrouille)
       ((Soldat)p).setStepScared(false);
   for(Piece h:this.hostile)
       ((Soldat)h).setStepScared(false);
   
   
   }
private BaseAction[] sequenqueActionMake(List<BaseAction> listUSAll,List<BaseAction> listHostAll){
       BaseAction[] arrayOrderd = null;
       if (iniativeWinner == JEUR_US) {
           listUSAll.addAll(listHostAll);
           arrayOrderd = new BaseAction[listUSAll.size()];
           Arrays.sort(listUSAll.toArray(arrayOrderd));
           System.out.println("-----------JEUR US WIN INITIATIVE---SIZE=--->" + arrayOrderd.length + "<--------SORTED--------------------------");
       } else {
           listHostAll.addAll(listUSAll);
           arrayOrderd = new BaseAction[listHostAll.size()];
           Arrays.sort(listHostAll.toArray(arrayOrderd));
           System.out.println("-----------JEUR HOSTILE WIN INITIATIVE-SIZE=--->" + arrayOrderd.length + "<--------SORTED--------------------------");
       }
       return arrayOrderd;

}
   private void resolveTempPas(int td,BaseAction[] arrayOrderd){


       if (arrayOrderd.length > 0) {
           int k = 0;

           while (k < arrayOrderd.length) {

               BaseAction act = arrayOrderd[k];
               //BaseAction clone=act.clone();
               Soldat s = (Soldat) act.getProtagoniste();

               if (!s.isIncoscient() &&  !s.isKIA() && !s.isStepScared() ) {
                   try {                                              
                       makeActionAndPlay(td,(Soldat) act.getProtagoniste(),act);                    
                       System.out.println("------------Zzzzzzzzzzzzz=" + allAnimOn());
                       while (allAnimOn());
                       System.out.println("------------Svegliaaaaaaaa=" + allAnimOn());                        
                       refreshAllGraficInterface();
                   } catch (MakeActionFailException e) {
                       e.printStackTrace();
                       //TODO personal reactio  to a ecczption....new ACTION INSERT
                       
                   }
               } else {
                   System.out.println("--------->ACTION IS IMPOSSIBLE FOR THE STATU OF SOLDAT");
               }
               //TODO vedere per aggiorantre la mappa quando!!!!
               k++;

               //break;
           }

           System.out.println("fine TD pas " + td);

       }

       resetAllSoldatStep();

    
    }
   private void makeActionAndPlay(int td,Soldat s,BaseAction a) throws MakeActionFailException{
             
       if(! s.isImmobilize() && (a.getType()==ActionType.MARCHE || a.getType()==ActionType.COURS) ){
           BaseAction act1=a.clone(); 
           makeMarcheAction(s,(MarcheAction) a);
           BaseAction act2=a.clone();
           playAllGraficInterface(act1,act2);
       }else if (   a.getType() == ActionType.FEU_VISER) {
           BaseAction act1=a.clone(); 
           makeFeuViserAction(td, a);
           BaseAction act2=a.clone();
           playAllGraficInterface(act1,act2);           
       }else if(a.getType()==ActionType.FEU){
           BaseAction act1=a.clone(); 
           makeFeuAction(a);
           BaseAction act2=a.clone();
           playAllGraficInterface(act1,act2); 
       
       } else if(a.getType()==ActionType.BANDAGE || 
               a.getType()==ActionType.ARME_RECHARGE){
           makeAction(a);
           playAllGraficInterface(a, a);
       
       }else {
           System.out.println("ACTION NOT CONSIDERED :"+a.toString());
           stopAllPendingAnimation();
       }

   }
   
   void makeAction(BaseAction act)throws MakeActionFailException{
       GeneriquePiece sp=act.getProtagoniste(),sa=act.getAntagoniste();
       try{

               Soldat s=(Soldat)sp;
               s.setAction(act.getType());
               switch (act.getType()){
                   case ARME_RECHARGE:
                      if(act.getVersus()==AbstractAction.SubjectType.MYSELF) 
                          s.rechargeArme();
                      else {
                         Soldat s1=(Soldat)sa;
                          s.giveMagazine(s1);
                          s1.rechargeArme();
                      }
                       break;
                   case BANDAGE:
                       if(act.getVersus()==AbstractAction.SubjectType.MYSELF)
                            s.bandage();
                       else {
                            s=(Soldat)sa;
                            s.bandage();
                      
                       }
                       break;
               
               
               }

           
           
 
       }catch(ImpossibleRechargeArmeException|PaDeMagazineException |IncompatibleMagazineException pa ){
           MakeActionFailException m= new MakeActionFailException();
           m.initCause(pa);
           throw m;
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
    
   private    void   playAllGraficInterface(BaseAction act1,BaseAction act2){
         
        for (GraficCarteInterface g : listgrafic) {
            g.setAnimOn(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    g.play(act1,act2);
                }
            } );
            return;
        }
    }

private     void   refreshAllGraficInterface(){
         
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

private void stopAllPendingAnimation(){
    for(GraficCarteInterface g:listgrafic)
        g.setAnimOn(false);

}
private    void reMountMenuItemsAndScroll(){
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
    public void makeMarcheAction(Soldat s, MarcheAction a) {
        //TODO rendere effettive le modifiche .....
        System.out.println("*************************MARCHE MAKE*************************");
        int i0=a.getI0(),j0=a.getJ0();
        int i1=a.getI1(),j1=a.getJ1();
        c.terrain[i0][j0].removePiece(s); 
        c.terrain[i1][j1].putPiece(s);
        s.setI(i1);
        s.setJ(j1);
        s.setAction(a.getType());

        System.out.println("updated terrain --soldat-"+s.toStringSimple()+"---->"+a.getI1()+"--->"+a.getJ1());
        System.out.println("updated position -soldat--->"+s.toStringSimple()+"--->"+a.getI1()+","+a.getJ1());
        System.out.println("**************************MARCHE MAKE**************************");        
    }    


        public void makeFeuViserAction(int td,BaseAction act)throws MakeActionFailException {
            try{
            System.out.println("*************************FEUUUU MAKE*****************************");
            ViserFeuAction viser=(ViserFeuAction)act;
            Soldat s=(Soldat)act.getProtagoniste();
            int shotN = s.feu();
            s.setAction(act.getType());
            
            if(s.getArmeUtilise()!=null){
                if(act.getType()==ActionType.FEU)
               s.getArmeUtilise().changeModeFeu(((FeuAction)act).getMode()); 
                else  if(act.getType()==ActionType.FEU_VISER)
                   s.getArmeUtilise().changeModeFeu(((ViserFeuAction)act).getMode());
            }
                
            
            
            Soldat t=null;
           
            if(act.getAntagoniste()!=null && 
                    act.getAntagoniste().getPieceType()==Piece.ActeurType.SOLDAT){
                t=(Soldat)act.getAntagoniste();
               
                int i1=t.getI();
                int j1=t.getJ();                
                      
            System.out.println(s.toStringSimple()+" soldat make feu---> "+t.toStringSimple()+"");
            //TODO generalizzare in FXcarte....
            double dist = Carte.distance(s.getI(), s.getJ(), i1,j1, FXCarte.TILE_SIZE);
            
            
            Target target=new Target(t, dist, shotN);
            int shotAssigned=scoreSoldatFeu(s, target, i1,j1,shotN,dist,act.getType());
            System.out.println("shotAssigned ="+shotAssigned);
            System.out.println("hit on target ="+target.getHits());
            Lesion[] llist=new Lesion[target.getHits()];

            PointCarte line[]= Carte.getLigne(s.getI(), s.getJ(), i1,j1);

            //Terrain cover= c.getPointCarte(line[line.length-1].getI(),line[line.length-1].getJ());
            GeneriqueArme arm=s.getArmeUtilise();
            for (int sc=0;sc<target.getHits();sc++)
                    resolveHit(s, t, act.getType());
             viser.addTarget(target);
            }
           
           
            }catch(LoadMagazineFiniException|ModeDeFeuException|TomberArmeException e){
                throw new MakeActionFailException(e.getClass().getName());
            
            }
            //TODO choose other target.........if full automatic in the line of sight
            
        System.out.println("**************************FEUUUU MAKE----FINE*****************************");            
    }
        
        
        
    void makeFeuAction(BaseAction act  )throws MakeActionFailException {
        try{
        GeneriqueArme.FeuMode mode=null;
        FeuAction feuact=(FeuAction)act;        
        mode=feuact.getMode();
        Soldat target=null;
        Soldat s=null;
        int num=0;
        if(act.getAntagoniste()!=null)
            target=(Soldat)act.getAntagoniste();
        if(act.getProtagoniste().getPieceType()==Piece.ActeurType.SOLDAT)
            s=(Soldat)act.getProtagoniste();
        s.setAction(act.getType());
        Target[] t=null;
        int shotN=s.feu();
        if(!(mode==GeneriqueArme.FeuMode.SA || mode==GeneriqueArme.FeuMode.SC))
          t=searchTargets(s, act.getI1(), act.getJ1()) ;
        else {
            double dist=Carte.distance(act.getI0(), act.getJ0(), act.getI1(), act.getJ1(), TILE_SIZE);
            t=new Target[]{new Target(target, dist,s.getArmeUtilise().getShotNumFeu())};
        }
       
        for (int i = 0; i < t.length; i++) {
            target = t[i].getS();
            System.out.println("score to :"+target.toStringSimple());
             int shotAssigned=scoreSoldatFeu(s, t[i], act.getI1(),act.getJ1(),shotN , t[i].getDist(), act.getType());
             if(t[i].getHits()>0) {
                 num++;
                 feuact.addTarget(t[i]);
             }  
             for(int n=0;n<t[i].getHits();n++)
             resolveHit(s, target, act.getType()) ;
           
             shotN=shotN-shotAssigned;
             if(shotN<=0) break;
                
        }

        }catch(ModeDeFeuException|TomberArmeException|LoadMagazineFiniException ex){
            throw new MakeActionFailException(ex);
        }
    }     
    void resolveHit(Soldat s,Soldat t,ActionType type)throws TomberArmeException {
        int dice=s.getBoss().dice(10);
        if(type==ActionType.FEU_VISER && dice>1) dice=dice-1;
        Corp.CorpParts location=LesionEstimation.getLesionLocation(dice);
        int blindage= t.getBlindage(location);
        boolean blindageRoll=true;
        
        if(blindage>0) {
            int tg=blindage+s.getArmeUtilise().getEMB();
            int rollBlindage=t.getBoss().dice(10);
            if(rollBlindage<=tg || rollBlindage==1) blindageRoll=false;
        }
        PointCarte line[]=Carte.getLigne(s.getI(), s.getJ(), t.getI(), t.getJ());
        boolean terrainCoverRoll=terrainCoverRoll(line, s);
        if(terrainCoverRoll ) t.setObjective(true);
        if(blindageRoll && terrainCoverRoll){
            int damage=s.getBoss().dice(10)-s.getArmeUtilise().getEDP();
            Lesion l=LesionEstimation.getLesion(dice, damage, turn);
                if(t!=null && !t.isKIA() && l!=null){
                    t.addLesion(l);
                    t.blessure(l);
                    if(l.isNecessaireDropItem()) tomberArmeUtilise(t);
                    if(!t.isKIA() && t.isObjective() && !t.isChoc()){
                        t.shellShockTest();
                    if(t.isChoc()) {
                        t.setStepScared(true);                       
                        if(!t.isImmobilize() &&
                           !t.isIncoscient() &&
                           !t.isKIA()     ) addFuirLontain(t,s.getFace(), td);
                            System.out.println(s+"  -->schoked ");
                    }                       
                   }
                       
                   System.out.println(t.toStringSimple()+":--add--->"+l);
                }
        
        }

    }    
    void makeSupressiveFeu(FeuAction act) throws LoadMagazineFiniException,ModeDeFeuException,TomberArmeException{
    
            
            Soldat s=(Soldat)act.getProtagoniste();
            s.setAction(act.getType());
            if(s.getArmeUtilise()!=null)
                s.getArmeUtilise().changeModeFeu(act.getMode());            
            Target[] targets=searchTargets(s, act.getI1(), act.getJ1());
            s.setAction(act.getType());
            int shotN=s.feu(); 
            int shutted=0;

            for (int i = 0; i < targets.length; i++) {
                Target target = targets[i];
                if(target!=null){
                    System.out.println(s.toStringSimple()+" soldat make feu---> "+((target!=null)?target.getS().toStringSimple():""));                                            
                    double dist=Carte.distance(s.getI(),s.getJ(), target.getI(), target.getJ(), FXCarte.TILE_SIZE);
                    int scoreAssigned=scoreSoldatFeu(s, target,target.getI() , target.getJ(),shotN,dist, act.getType());
                    if(target.getHits()>0) {
                        act.addTarget(target);
                        shutted++;
                    }
                    for (int sc = 0; sc < target.getHits(); sc++) 
                         resolveHit(s, target.getS(), act.getType());
                    shotN=shotN-scoreAssigned;
                   
            
                }
            if(shotN<=0) break;                    
            }

           

        
        
    }
    
    
    Target[] searchTargets(Soldat s,int i1,int j1) throws ModeDeFeuException,TomberArmeException{

        LigneFeuObjectifs objetifs=new LigneFeuObjectifs();
        int n=0;
        PointCarte line[][]=fireLine(s, i1, j1);
        for(int k=0;k<line.length;k++){
            for (int i = 1; i < line[k].length; i++) {
                PointCarte p = line[k][i];
                Terrain t=this.c.getPointCarte(p);
               
                if(t.getPiece()!=null && 
                        t.getPiece().getPieceType()==Piece.ActeurType.SOLDAT){
                    System.out.println(i+") terrain t="+t.toString());
                    Soldat target=(Soldat)t.getPiece();
                    double dist=Carte.distance(s.getI(), s.getJ(), target.getI(), target.getJ(), FXCarte.TILE_SIZE);
                    int shotN= s.getArmeUtilise().getShotNumFeu();
                    if(k==0 && shotN>=10)shotN=(int)(shotN*0.4);
                    else if(shotN>=10)shotN=(int)(shotN*0.3);
                    else shotN=s.getBoss().dice(shotN);
                    Target tt=new Target(target,dist,shotN);
                    objetifs.addTarget(tt);
                    System.out.println(tt);
                    n++;
                }

            }    
        }

        System.out.println("target trovati :"+objetifs.size());

        return objetifs.sortedDistanceTarget(); 
    }
    
    //TODO da fare solo con una linea di fuoco....
     Target[] searchSingleLineTarget(Soldat s,int i1,int j1)throws ModeDeFeuException,TomberArmeException{
        PointCarte[] ligne=Carte.getLigne(s.getI(), s.getJ(), i1, j1);
        Soldat target=null;
        for (int i = 0; i < ligne.length; i++) {
            PointCarte p = ligne[i];
             Terrain t=c.getPointCarte(p.getI(), p.getJ());
             
             if(t.getPiece()!=null &&
             t.getPiece().getPieceType()==Piece.ActeurType.SOLDAT ) 
                 target=(Soldat)t.getPiece();
                 break;
        }
        Target[] t=null;
        if(target!=null)searchTargets(s, target.getI(), target.getJ());
        return t;
    }
    PointCarte[][] fireLine(Soldat s ,int i1,int j1){
        PointCarte[][] lines=new PointCarte[3][];
        System.out.println("fire line:"+s.toStringSimple()+" -> "+i1+","+j1+" direction "+s.getFace());
    switch( s.getFace()){
            case N:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1, j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.NE.i, Direction.NE.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.NW.i, Direction.NW.j+j1);
                break;
            case NE:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1,j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.N.i, Direction.N.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.NW.i, Direction.NW.j+j1);                
                break;
            case E:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1, j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.NE.i, Direction.NE.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.SE.i, Direction.SE.j+j1);                
                break; 
            case SE:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1,j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.E.i, Direction.E.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.S.i, Direction.S.j+j1);                
                break;      
            case S:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1,j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.SE.i, Direction.SE.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.SW.i, Direction.SW.j+j1);                
                break;       
            case SW:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1,j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.S.i, Direction.S.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.W.i, Direction.W.j+j1);                
                break;      
            case W:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1, j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.SW.i, Direction.SW.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.NW.i, Direction.NW.j+j1);                
                break;         
            case NW:
                lines[0]=Carte.getLigne(s.getI(), s.getJ(), i1, j1);
                lines[1]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.N.i, Direction.N.j+j1);
                lines[2]=Carte.getLigne(s.getI(), s.getJ(), i1+Direction.W.i, Direction.W.j+j1);                
                break;                    
                
    }
        
    return lines;
    }    
    int scoreSoldatFeu(Soldat s,Target target,int i1,int j1,int shotN,double dist,ActionType type) throws TomberArmeException,ModeDeFeuException,LoadMagazineFiniException{

        int shotAssigned = 0;
        Soldat t = null;
        if (target == null) {

        } else {            
            i1 = target.getI();
            j1 = target.getJ();
            t = target.getS();
            shotAssigned = target.getScore();
            System.out.println("shotN disponible:" + shotN);
            System.out.println("shotAssigned:" + shotAssigned);
            GeneriqueJoeurs boss = s.getBoss();

            int cDM = s.combatFeuModifier(dist);
            cDM = cDM + t.combatModifierTarget();
            int tg = s.getCA() + cDM;
            System.out.println("-->combat modifier " + cDM + " competence d'arme :" + s.getCA());
            int hits = s.getArmeUtilise().hitsNumMF(dist);
            System.out.println("special shot hits:" + hits);
            if (shotAssigned > shotN) {
                shotAssigned = shotN;
            }
            for (int sc = 0; sc < shotAssigned; sc++) {
                for (int h = 0; h < hits; h++) {
                    int dice = boss.dice(10);
                    System.out.println("dice " + dice + "<=target number " + tg);
                    if (dice <= tg) {
                        target.addHits();
                    }
                }
            }
            System.out.println("hits: "+target.getHits());
            
        }
        return shotAssigned;
    
    }
    void tomberArmeUtilise(Soldat s1){
        //TODO da aggiungere all'ultimo usati.....
        //c.getPointCarte(s1.getI(), s1.getJ()).addExtraPiece(s1.getArmeUtilise());
        s1.setArmeUtilise(null);
        System.out.println(s1.toStringSimple()+" : drop arme");
        //TODO play anim senza arma nella mano.....da aggingere a blessed
    }
    //TODO rivedere in altre direzioni opposte al fuoco...:)))
    private void addFuirLontain(Soldat s,Direction d,int td){
        try{
        
        s.resetTempDispoleNotUse();
        BaseAction act=MarcheAction.marcheLointain(s,d);
        if(act!=null){
            BaseAction noact=new BaseAction(ActionType.PA_ACTION, -1, -1, -1, -1, s, null);
            noact.setTempActivite(td);
            noact.setUsed(true);
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
     boolean terrainCoverRoll(PointCarte line[],Soldat s)throws TomberArmeException{
         for (int i = 0; i < line.length; i++) {
             PointCarte pointCarte = line[i];
             Terrain t=c.getPointCarte(pointCarte.getI(), pointCarte.getJ());
             GeneriqueArme arm=s.getArmeUtilise();
             Terrain.Consistance con=t. getConsistance();
             System.out.println("(i,j)=("+t.getI()+","+t.getJ()+"):"+t);
             if(con!=Terrain.Consistance.NO  && i!=0 ){
                 
                 int coverNum=arm.getCoverPenetration(con)+arm.getEDP();
                 if(arm.getCoverPenetration(con)==GeneriqueEquipment.NOTVALUE) return false;
                 int rollCover=s.getBoss().dice(10);
                 System.out.println("rollCove "+rollCover+" coverNum="+coverNum+" (i,j)=("+t.getI()+","+t.getJ()+") terrain="+t );
                 if(rollCover>coverNum ) return false;                 
             }else if(i==0 && con==Terrain.Consistance.DUR ){
                 return false;
             }

             }
             
         return true;
     }

    public int getTurn() {
        return turn;
    }
     
     
     
}
