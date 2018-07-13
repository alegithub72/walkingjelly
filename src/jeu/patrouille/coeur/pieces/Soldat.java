/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;




import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.pieces.parts.CorpPart;
import jeu.patrouille.coeur.pieces.parts.Corp;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.GeneriqueBlindageEquipment;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.exceptions.KilledSoldatException;

import jeu.patrouille.coeur.pieces.exceptions.ImmobilzedSodlatException;
import jeu.patrouille.coeur.pieces.exceptions.NotSautOrCourseSoldatException;
import jeu.patrouille.coeur.pieces.exceptions.UnActionSoldatException;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme.*;
import jeu.patrouille.coeur.equipments.armes.Magazine;
import jeu.patrouille.coeur.equipments.armes.exceptions.ImpossibleRechargeArmeException;
import jeu.patrouille.coeur.equipments.armes.exceptions.IncompatibleMagazineException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import jeu.patrouille.coeur.pieces.exceptions.IncoscientSoldatException;
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;

/**
 *
 * @author Alessio Sardaro
 */
public class Soldat extends Piece {
    

    public enum Classment{SERGENT,TENENT,SERGENT_MAJOR,SOLDAT};

    public static final int FULL_SANTE=6;
    public enum Statut{NORMAL("Saine"),MANQUE("Manque"),LEGER_BLESSE("Legger blesse"),GRAVE("Grave"),
        GRAVE_BRASE_DROITE("Grave brase droite"),GRAVE_BRASE_GAUCHE("Grave base gauche"),GRAVE_TETE("Grave alla tete"),CRITIQUE("Critique"),INCONSCENT("Incoscient"),MORT("Mort");
        public String mes;
        Statut(String mes){
        this.mes=mes;
    }}    
    ActionType actionActuel=ActionType.PA_ACTION;
    Corp corp;
    Lesion lesion[];
    int lesionN;
    Direction face;
    String nom=null;
    String nomDeFamilie=null;
    int competenceArme=-1;
    int connaissanceArme=-1;
    int combatRapproche=-1;
    int force=-1;
    int courage=-1;
    int sante=-1;
    int moral=-1;
    int commandControler=-1;
    Classment classement;
    GeneriqueArme armeUtilise;
    Pose pose=Pose.DROIT;
    GeneriqueEquipment[] equipmentGen;
    boolean equipePorte;
    boolean incoscient;
    boolean objective;
    boolean active;
    boolean choc;
    boolean immobilize;
    boolean stepScared;
    Statut st;


 
    public boolean isBleeding(){
        boolean bleeding=false;
        for (Lesion l1 : lesion) {
           if(l1!=null && l1.getGravite()==Lesion.Degre.CRITIQUE && !l1.isBandage()) 
               return true; 
           if(l1!=null  && l1.getGravite()==Lesion.Degre.GRAVE && !l1.isBandage()) 
               return true;
        }
    return bleeding;
    }
    
    public Soldat(
            String nom,
            String nomDeFamilie,
            int competenceArme,
            int connaissanceArme,
            int combatRapproche,
            int force,
            int courage,
            int sante,
            Corp corps,
            int moral,
            int commandControler,
            Direction d,
            GeneriqueJoeurs boss)
    {
        super(ActeurType.SOLDAT,boss);
        face=d;
        this.nom=nom;
        this.nomDeFamilie=nomDeFamilie;
        this.competenceArme=competenceArme;
        this.connaissanceArme=connaissanceArme;
        this.combatRapproche=combatRapproche;
        this.force=force;
        this.courage=courage;
        this.sante=sante;
        this.moral=moral;
        this.commandControler=commandControler;
        this.equipePorte=false;
        st=Statut.NORMAL;
        incoscient=false;
        objective=false;
        immobilize=false;
        active=false;
        choc=false;
        lesion=new Lesion[10];
        lesionN=0;
        this.corp=corps;
        stepScared=false;

        
    
    }
    public void bandage(){
        for(Lesion l:lesion){
            if(l!=null)l.setBandage(true);
        }
    }
    public void setSpreadDone(boolean spreadDone) {
        this.spreadDone = spreadDone;
    }

    public void setActionsPool(BaseAction[] actionsPool) {
        this.actionsPool = actionsPool;
    }
    
    
 

    public boolean isSpreadDone() {
        return spreadDone;
    }
    
    public boolean isStepScared() {
        return stepScared;
    }

    public boolean isDoubled() {
        return st==Statut.GRAVE||
               st==Statut.GRAVE_BRASE_DROITE||
               st==Statut.GRAVE_BRASE_GAUCHE;
    }



    public boolean isEquipePorte() {
        return equipePorte;
    }

    public void setEquipePorte(boolean equipePorte) {
        this.equipePorte = equipePorte;
    }

    public void rechargeArme() throws PaDeMagazineException,IncompatibleMagazineException,ImpossibleRechargeArmeException{
        if(armeUtilise!=null  ){
            armeUtilise.loadMagazine();
           
        }
    
    }
    public void giveMagazine(Soldat s){
        if(armeUtilise!=null){
            Magazine m=armeUtilise.giveMagazine();
            s.takeMagazine(m);
                    
        }
            
    }
    
    public boolean isFireWeaponUtilize(){
        return armeUtilise.getEquipmentType()==GeneriqueEquipment.EquipmentType.FIRE_WEAPON;
        
    }
    public void takeMagazine(Magazine m){
        armeUtilise.addMagazine(m);
    }
    public void blessure(Lesion l){
        Statut statutNow=l.getStatu();
        corp.reciveBlessure(l); 
        if(statutNow==Statut.CRITIQUE 
                && l.getLocation()==Corp.CorpParts.Tete) {
            sante=-10;
            st=Statut.MORT;
            immobilize=true;
            resetAction();
        }
        else { 
            this.sante=sante+l.getBlessure();
            
            switch (statutNow) { 
                case CRITIQUE:
                    objective=true;
                    this.moral=moral-2;
                    int tdActionRemove=boss.dice(10);
                    removeActionUpTo(tdActionRemove);
                    this.pose=Pose.PRONE;
                    this.immobilize=true;
                    setStatut(statutNow);
                           
                    break;
                case GRAVE_TETE:
                    objective=true;
                    moral=moral-1;
                    incoscient=true;
                    immobilize=true;
                    tdActionRemove=boss.dice(6);//TODO levere se action fro the pool 
                    removeActionUpTo(tdActionRemove);
                    setStatut(statutNow);                       
                    this.pose=Pose.PRONE;
                                
                    break;
                case GRAVE:
                    objective=true;
                    moral=moral-1;
                     tdActionRemove=boss.dice(6);
                     removeActionUpTo(tdActionRemove);
                                   
                    //TODO not jumping and runnnig but all the others...
                    //TODO remove all action except one
                    setStatut(statutNow);
                   
                    break;
                case GRAVE_BRASE_DROITE:
                    objective=true;
                    moral=moral-1;
                    tdActionRemove=boss.dice(6);
                    removeActionUpTo(tdActionRemove);
                    setStatut(statutNow);
                   
                    //todo drop items
                    //TODO remove all action except one
                    //TODO one action per turn
                
                
                    break;     
                case GRAVE_BRASE_GAUCHE:
                    objective=true;
                    moral=moral-1;
                    tdActionRemove=boss.dice(6);
                    removeActionUpTo(tdActionRemove);
                    if(armeUtilise!=null)armeUtilise.setDegat(true);
                    setStatut(statutNow);                       
              
                    //TODO one action per turn

                    break;                    
                case LEGER_BLESSE:
                    objective=true;
                    tdActionRemove=4;
                    removeActionUpTo(tdActionRemove);
                    setStatut(statutNow);      
             

                    break;
                case MANQUE:
                    objective=false;//is happy
                    tdActionRemove=2;
                    removeActionUpTo(tdActionRemove);
                default:
                    break;
            }
            if(sante<=0) {
                incoscient=true;
                st=Statut.INCONSCENT;
                pose=Pose.PRONE;
            }//TODO uncoinscious 
            if(sante<=-10) st=Statut.MORT;
           
        }
        
        

        
    }

    public Statut getStatu() {
        return st;
    }
    public boolean isEnVie(){
        return st==Statut.NORMAL || st==Statut.MANQUE  || st==Statut.LEGER_BLESSE;
    }

    public void setStatut(Statut statut) {
        if(statut.ordinal()>st.ordinal() )st=statut;
        
    }

 
    
    
    public void setArmeEquip(GeneriqueEquipment[] equipmentGen) {
        this.equipmentGen = equipmentGen;
        if(equipmentGen.length>0) armeUtilise=(GeneriqueArme)equipmentGen[0];
    }
    public int getEquipmentChiffre(){
        int n=equipmentGen.length;
        for (GeneriqueEquipment equipe : equipmentGen) {
            if (equipe.getEquipmentType() == GeneriqueArme.EquipmentType.FIRE_WEAPON) {
                n = n + ((GeneriqueArme)equipe).getNumMagazine();
            }
        }
        return n;
    }
    public GeneriqueArme getArmeUtilise()throws TomberArmeException {
        if(armeUtilise==null) throw  new TomberArmeException();  
        return armeUtilise;
    }

    public void setArmeUtilise(GeneriqueArme armeUtilise) {
              
        this.armeUtilise = armeUtilise;
    }

    public Pose getPose() {
        return pose;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public boolean isUtilizeAutomaticArme() {
        return armeUtilise.isAutomaticArme();
    }

 

    
    
    public void setClassement(Classment classement) {
        this.classement = classement;
    }

    public Classment getClassement() {
        return classement;
    }
    
    
    public String getNom() {
        return nom;
    }

    public String getNomDeFamilie() {
        return nomDeFamilie;
    }

    public int getCA() {
        return competenceArme;
    }

    public int getKA() {
        return connaissanceArme;
    }

    public int getCP() {
        return combatRapproche;
    }

    public int getF() {
        return force;
    }

    public int getC() {
        return courage;
    }

    public int getSante() {
        return sante;
    }
public int isLesion(){
    int n=0;
    for (int k = 0; k < lesion.length; k++) {
        Lesion l = lesion[k];
        if(l!=null && l.getGravite().ordinal()>1) n++;
    }   
    return n;
}
   public int getBlindage(Corp.CorpParts part){
       if(corp!=null){
        CorpPart p=  corp.getCorpPart(part);
        System.out.println("part "+p);
        GeneriqueBlindageEquipment bli=p.getBlindage();
        System.out.println("part blinde "+p.getBlindage());
        if(bli!=null)return bli.getAr();
       } 
       return 0;
    }
    public int getM() {
        return moral;
    }

    public int getCC() {
        return commandControler;
    }    
   public ActeurType getType() {
        return type;
    }
   public void setAction(ActionType a){
    actionActuel=a;
   }
   public ActionType getAction(){
    return actionActuel;
   }

    public void setFace(Direction face) {
        this.face = face;
    }

    public Direction getFace() {
        return face;
    }

    @Override
    public Piece[] losView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

     
     
    @Override
    public Soldat clonerPiece(){

        Soldat s = new Soldat(this.nom,
                this.nomDeFamilie, this.competenceArme,
                this.connaissanceArme, this.combatRapproche,
                this.force, this.courage, this.sante,
                this.corp, this.moral,
                this.commandControler, this.face,boss);
        s.setTempDesponible(this.tempDesponible);
        s.setI(this.getI());
        s.setJ(this.getJ());
        s.equipeArrayPlace = this.equipeArrayPlace;
        s.setStatut(st);
        GeneriqueEquipment armeClone[] = null;
        if (equipmentGen != null) {
            armeClone = new GeneriqueArme[equipmentGen.length];
            for (int a = 0; a < equipmentGen.length; a++) {
                armeClone[a] = (GeneriqueEquipment)equipmentGen[a].clonerPiece();
                if(armeUtilise!=null &&
                   armeUtilise.getModel()==equipmentGen[a].getModel()) 
                    s.setArmeUtilise((GeneriqueArme)equipmentGen[a]);
            }
            s.setArmeEquip(armeClone);
          
        }
        s.lesion=new Lesion[10];
        int k=0;
        s.lesionN=0;
        for (Lesion l : lesion) {
            if(l!=null)s.addLesion(l.clone());
            
            k++;
        }
        return s;
    }
    
   
    
    public int feu() throws ModeDeFeuException,LoadMagazineFiniException,TomberArmeException{
        if(armeUtilise==null) throw  new TomberArmeException();
        int shotN = armeUtilise.feuArme();
        this.tempDesponible=tempDesponible-armeUtilise.fireTempNecessarie(actionActuel);
        return shotN;
    
    }
    

   public int combatFeuModifier(double dist){
            int cDM=-(6-sante);
            System.out.println("1)combat  modifier lesion:"+cDM);
            if(armeUtilise.getArmeFeuMode()==FeuMode.RA ) 
                cDM=cDM-1;
            else if(armeUtilise.getArmeFeuMode()==FeuMode.PA)
                cDM=cDM-3;
            System.out.println("2)combat  modifier arme utlise:"+cDM);
            
            if(actionActuel==ActionType.MARCHE)cDM=cDM-2;//TODO questo non so se si puo fare  se marcia non fa fuoco ...pensare ad una soluzione...
            else if(actionActuel==ActionType.COURS)cDM=cDM-4;
            System.out.println("3)combat  modifier soldat move action:"+cDM);
            if(actionActuel==ActionType.FEU_VISER)cDM=cDM+1;
            System.out.println("4)combat  modifier soldat action viser fire:"+cDM);


            if(pose==Piece.Pose.PRONE) cDM=cDM+1;
             System.out.println("5)combat  modifier soldat prone:"+cDM);
            cDM=cDM+armeUtilise.porteModifier(dist);
            System.out.println("5bis)combat porte modifie:"+cDM);
            
         
            return cDM;
    
    }
    
    
    public int combatModifierTarget(){
            int cDM=0;
            
            if(actionActuel==ActionType.COURS) cDM=cDM-2;
            else if(actionActuel==ActionType.MARCHE)cDM=cDM-1;
            System.out.println("6)target combat  modifier move action:"+cDM);
            
            if(pose==Piece.Pose.PRONE) cDM=cDM-1;   
            System.out.println("7)target combat modifier prone:"+cDM);
            
    return cDM;
    
    }
    @Override
    public String toString() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"\n"
                +"Temp Desponible:"+tempDesponible+"\n"
                + "" +((armeUtilise!=null)? armeUtilise.toString():"") + ",face:"+face+",(i="+i+",j="+j+")";
                
    }
    



    public boolean isPossibleAction(){
        return tempDesponible>0 &&
                !isIncoscient() &&
                !isKIA() &&
                !isChoc();
        
    }

    @Override
    public String toStringSimple() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"";
                
    }
    public boolean isTempDisponiblePour(ActionType actiontype)throws ModeDeFeuException{
        double baseTempDisponible=tempNecessarieDesActionBase(actiontype);
        return (tempDesponible>=baseTempDisponible);
        
    }
    public void resetAction(){
    this.actionsPool=new BaseAction[10];
    }
    
   public  GeneriqueEquipment[] getEquipment(){
        return equipmentGen;
    }

 public boolean isIncoscient(){
        return incoscient || sante<=0;
 
 }
   //TODO inserire vairiabile status UNCOSCIOUS,IMMOBILIZE,NORMAL,ETC....vedere bene health doc..
    @Override
    public void addAction(BaseAction act) throws Exception{
        if(sante==FULL_SANTE) super.addAction(act);
        else if(st==Statut.CRITIQUE && sante<=-10){
            throw new KilledSoldatException();
        }
        else if(st==Statut.CRITIQUE ){
            //Immobilized he cannot move he can fire ..bandage and ..boohhh
            if(act.getType()==ActionType.MARCHE ||
                act.getType()==ActionType.COURS ||
                    act.getType()==ActionType.LEVER||
                    act.getType()==ActionType.MONTER
                    )throw new ImmobilzedSodlatException();
            else super.addAction(act);
        }else if(st==Statut.GRAVE_TETE  ){
            if(incoscient) throw new IncoscientSoldatException();
            else if(act.getType()==ActionType.MARCHE ||
                act.getType()==ActionType.COURS ||
                    act.getType()==ActionType.LEVER||
                    act.getType()==ActionType.MONTER
                    )
                throw new ImmobilzedSodlatException();
            else super.addAction(act);
        }else if(st==Statut.GRAVE){
            if(act.getType()==ActionType.COURS ||
                    act.getType()==ActionType.SAUT)
                throw new NotSautOrCourseSoldatException();
            super.addAction(act);
        }else if(st==Statut.LEGER_BLESSE){
            super.addAction(act);
        }else if(st==Statut.GRAVE_BRASE_DROITE){
            if (actionArrayN > 1) 
                throw new UnActionSoldatException();
            super.addAction(act);
            
        }else if(st==Statut.GRAVE_BRASE_GAUCHE){
            if (actionArrayN > 1) 
                throw new UnActionSoldatException();            
            super.addAction(act);
        }else super.addAction(act);
        
        
        
    }

    public void setStepScared(boolean stepScared) {
        this.stepScared = stepScared;
    }
    
  
   public boolean isObjective(){
       return this.objective;
   }
   //TODO riguardare le regole
   public void shellShockTest(){
       choc= !(boss.dice(10)<=moral);//TODO vedere i modificatori di morale
       if(!choc) System.out.println(this.toStringSimple()+" CHOC  passed");
       else System.out.println(this.toStringSimple()+" CHOC not passed");
   }

   public boolean isKIA(){
       
   return sante<=-10|| st==Statut.MORT;
   
   }
    public boolean isActive() {
        return active;
    }

    public boolean isChoc() {
        return choc;
    }

@Override
    public int tempNecessarieDesActionBase(ActionType actionType)throws ModeDeFeuException{
        int apbase=-1;
        if(armeUtilise!=null && actionType.isFeuAction() ) 
            apbase=this.armeUtilise.fireTempNecessarie(actionType); 
        else  apbase=actionType.TN();
        if(isDoubled() && actionType.isMovementAction() ) apbase=apbase*2;
        return apbase;
    }


    //TODO ????????? boohhh
   public void resetRondCheck(){
       this.objective=false;
       this.active=false;
       this.actionActuel=ActionType.PA_ACTION;
       
       

   }
   public void inconscentTetst(){
    int roll=boss.dice(10);
    if(roll<=sante) {
        incoscient=false;
        System.out.println(this.toStringSimple() +"->INCOSCINET PASSED IS ALIVE");
        return ;
    }else System.out.println(toStringSimple()+"->Incosciente not passed");
   }
    public void incoscientBleedingTest(){
          boolean surv=survriveTest();
          if(!surv) sante=sante-1;
  
   }
   
   public boolean isBandage(){
       boolean bandage=true;
       for(int k=0;k<lesion.length;k++)
          if(lesion[k]!=null) bandage=bandage && lesion[k].isBandage();
       return bandage;
   }
   public void bleedingTest(){
        boolean surv=survriveTest();
        if(!surv){
           sante=-10;
           st=Statut.MORT;
           System.out.println(toStringSimple()+" MORTO PER TEST DI SOPRAVVIVENZA");
       }else{
           sante--;
           if(sante<=0) st=Statut.INCONSCENT;
           System.out.println(toStringSimple()+" bleding test passed, not die but sante -1");
       }
   }
   public boolean survriveTest(){
       int roll=boss.dice(10);
       int targetN=10+sante;
       return roll<=targetN;
    
   }
    public boolean isImmobilize() {
        return immobilize;
    }
   
   public boolean isPossibleAchive(ActionType type,double dist){
      try{
            double apbase=this.tempNecessarieDesActionBase(type);
            if(type==ActionType.COURS) apbase=apbase/5;
            double value=Math.round(dist*apbase);
             System.out.println(" temp consumed :"+value);
            return value<=tempDesponible ;
           
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return false;
   }
   public boolean isFeuArmePaPorte(double d){
       double dlonge=armeUtilise.getDistancePorte(Porte.LONGE);
       System.out.println("distance "+d+" >="+dlonge+"");
       return d>(dlonge);
       
   }
   
   public void addLesion(Lesion l){
       lesionN++;
       if(lesionN==lesion.length) {
       Lesion listNew[]=new Lesion[lesion.length*2];
       for (int k = 0; k < lesion.length; k++) {
               listNew[k]=lesion[k];
          }
           lesion=listNew;
           
       }
       this.lesion[lesionN]=l;
   
   }

public void setObjective(boolean objective) {
        this.objective = objective;
    }
   
   public Lesion getLastLesion(){
        return lesion[lesionN];
   }
   public Lesion[] getAllLesion(){
       return lesion;
   }
   
  void removeActionUpTo(int td){
    int tdMax=0;
    
    if(tempDesponible>td) {
        tempDesponible=tempDesponible-td;
        tdMax=0;
    }else tdMax=td-tempDesponible;
    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    System.out.println("richiesta originale :"+td+",levare tdMax="+tdMax+",temp disponible rimasto "+tempDesponible);
    while(tdMax<0 && actionArrayN>0){
            BaseAction act=  actionsPool[actionArrayN-1];
            if(!act.isUsed())
            {
                tdMax=tdMax-act.getTempActivite();            
                actionsPool[actionArrayN-1]=null;
                actionArrayN--;
                System.out.println("removed "+act);
            }else {
                tdMax=0;
                System.out.println(" fine remove action ");
            }

        }
    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");    
    
    

}   

    public boolean isBrasDroiteBlesse() {
        return corp.isBrasDroiteBlesse();
    }

    public boolean isBraceGaucheBlesse() {
        return corp.isBraceGaucheBlesse();
    }

public boolean isFriend(GeneriquePiece p){
    return p.getBoss().getJeur()==boss.getJeur();

}
  

public BaseAction lastMovement(){
    BaseAction last=null;
    for (int k = 0; k < actionArrayN; k++) {
        BaseAction act = actionsPool[k];
        if(act!=null && act.getType().isMovementAction()) last=act;
    }
    return last;
}
  
  
}
