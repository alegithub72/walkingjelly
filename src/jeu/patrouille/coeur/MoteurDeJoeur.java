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
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.actions.exceptions.MakeActionFailException;
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
import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.fx.board.FXCarte;
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
    LesionEstimation lesionEsti;
    public int td;
    
    
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
public void rondStartTest(){
   for(Piece p1: patrouille){
       Soldat s1=(Soldat) p1;
       if(s1.isChoc() && !s1.isKIA()) s1.shellShockTest();
   }
   for(Piece p1:hostile){
       Soldat h1=(Soldat) p1;
       if(h1.isChoc() && !h1.isKIA()) h1.shellShockTest();
   }

}

    
   public  void resolveAllRondeActions()throws Exception{        
        turn++;
        rondStartTest();
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
             sum=sum+sold.nextAction(h).getTempActivite();
             if(sum<=td) {
                 if(!sold.nextAction(h).isUsed()){
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
            if(type1==ActionType.MARCHE){
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
           Arrays.sort(listUSAll.toArray(arrayOrderd), BaseAction.baseActionCompratorImpl);
           System.out.println("-----------JEUR US WIN INITIATIVE---SIZE=--->" + arrayOrderd.length + "<--------SORTED--------------------------");
       } else {
           listHostAll.addAll(listUSAll);
           arrayOrderd = new BaseAction[listHostAll.size()];
           Arrays.sort(listHostAll.toArray(arrayOrderd), BaseAction.baseActionCompratorImpl);
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
             
       if(! s.isImmobilize() && a.getType()==ActionType.MARCHE ){
           BaseAction act1=a.clone(); 
           makeMarcheAction(s, a);
           BaseAction act2=a.clone();
           playAllGraficInterface(act1,act2);
       }else if (   a.getType() == ActionType.FEU) {
           BaseAction act1=a.clone(); 
           makeFeuAction(td,(FeuAction) a);
           BaseAction act2=a.clone();
           playAllGraficInterface(act1,act2);           
       }else if(a.getType()==ActionType.BANDAGE || 
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


        public void makeFeuAction(int td,FeuAction act)throws MakeActionFailException {
            try{
            System.out.println("*************************FEUUUU MAKE*****************************");
            Soldat s=(Soldat)act.getProtagoniste();
            s.setAction(act.getType());
            if(s.getArmeUtilise()!=null)
                s.getArmeUtilise().changeModeFeu(act.getMode());
               Soldat target=null;
            if(act.getAntagoniste()!=null && 
                    act.getAntagoniste().getPieceType()==Piece.ActeurType.SOLDAT)
                target=(Soldat)act.getAntagoniste();
            
            int i1=act.getI1(),j1=act.getJ1();
            if(target!=null ){
                i1=target.getI();
                j1=target.getJ();                
            }            
            System.out.println(s.toStringSimple()+" soldat make feu---> "+((target!=null)?target.toStringSimple():""));
            //TODO generalizzare in FXcarte....

            int score=scoreSoldatFeu(s, target, i1,j1,act.getType());
            System.out.println("score ="+score);
            Lesion[] llist=new Lesion[score];

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
                boolean armorPassed=true;
                if(bli>0) armorPassed=(armorRoll<=armorCheck)|| (armorRoll==1) ;
                
                System.out.println("Terrain cover is passed="+coverBool +" armorROLL<=armorCheck "
                        +armorRoll+"<="+armorCheck+" boolean:"+armorPassed );
                if( armorPassed && coverBool) {
                    if(target!=null)System.out.println(target.toStringSimple()+":-RECIVE--->"+l);
                    llist[sc]=l;
                }else System.out.println("---->NOT PASSED");
                

            }   
            
            for (Lesion ln : llist) {
                if(target!=null && !target.isKIA() && ln!=null){
                    target.addLesion(ln);
                    target.blessure(ln);
                    if(ln.isNecessaireDropItem()) tomberArmeUtilise(target);
                    if(!target.isKIA() && target.isObjective() && !target.isChoc()){
                        target.shellShockTest();
                    if(target.isChoc()) {
                        target.setStepScared(true);                       
                        if(!target.isImmobilize() &&
                           !target.isIncoscient() &&
                           !target.isKIA()     ) addFuirLontain(target,s.getFace(), td);
                        System.out.println(s+"  -->schoked ");
                    }                       
                   }
                       

                   System.out.println(target.toStringSimple()+":--add--->"+ln);
                }
                
            }
            

            }catch(LoadMagazineFiniException|ModeDeFeuException|TomberArmeException e){
                throw new MakeActionFailException(e.getClass().getName());
            
            }
            //TODO choose other target.........if full automatic in the line of sight

        System.out.println("**************************FEUUUU MAKE----FINE*****************************");            
    }
    int scoreSoldatFeu(Soldat s,Soldat target,int i1,int j1,ActionType type) throws TomberArmeException,ModeDeFeuException,LoadMagazineFiniException{

        int score = 0;
        if(target!=null) {
            i1=target.getI();
            j1=target.getJ();
        }
        double dist = Carte.distance(s.getI(), s.getJ(), i1,j1, FXCarte.TILE_SIZE);
        int shotN = s.feu(dist);
        System.out.println("hits " + shotN);
        GeneriqueJoeurs boss=s.getBoss();
        
        int cDM = s.combatFeuModifier(dist);
        if(target!=null) cDM=cDM+target.combatModifierTarget();
        int tg=s.getCA()+cDM;
        System.out.println("-->combat modifier " + cDM+ "competence d'arme :"+s.getCA());
            for(int hits=0;hits<shotN;hits++){
                int dice=boss.dice(10);
                System.out.println("dice "+dice+"<=target number "+tg);
                if(dice<=tg) score++;
            }    
    
    return score;
    
    }
    void tomberArmeUtilise(Soldat s1){
        //TODO da aggiungere all'ultimo usati.....
        c.getPointCarte(s1.getI(), s1.getJ()).addExtraPiece(s1.getArmeUtilise());
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

    public int getTurn() {
        return turn;
    }
     
     
     
}
