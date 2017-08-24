/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;




import jeu.patrouille.coeur.pieces.parts.Lesion;
import jeu.patrouille.coeur.pieces.parts.CorpPart;
import jeu.patrouille.coeur.pieces.parts.Corp;
import java.util.ArrayList;
import jeu.patrouille.coeur.Carte;
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
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author Alessio Sardaro
 */
public class Soldat extends Piece {
    

    public enum Classment{SERGENT,TENENT,SERGENT_MAJOR,SOLDAT};

    public static final int FULL_SANTE=6;
    public enum Statut{CRITIQUE,GRAVE,GRAVE_TETE,LEGER_BLESSE
    ,MANQUE,NORMAL,GRAVE_BRASE_DROITE,GRAVE_BRASE_GAUCHE}    
    ActionType actionActuel=ActionType.PA_ACTION;
    Corp blindage;
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
    Statut st;


 
   
    
    public Soldat(
            String nom,
            String nomDeFamilie,
            int competenceArme,
            int connaissanceArme,
            int combatRapproche,
            int force,
            int courage,
            int sante,
            Corp blindage,
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
        this.blindage=blindage;
        
    
    }

    public boolean isDoubled() {
        return st==Statut.GRAVE;
    }

    public int isWounded(){
        return 6-sante;
        
    }

    public boolean isEquipePorte() {
        return equipePorte;
    }

    public void setEquipePorte(boolean equipePorte) {
        this.equipePorte = equipePorte;
    }


    public void blessure(Lesion l){
        if(l.getStatu()==Statut.CRITIQUE 
                && l.getLocation()==Corp.CorpParts.Tete) {
            sante=-10;
            st=Statut.CRITIQUE;
            resetAction();
        }
        else { 
            this.sante=sante+l.getBlessure();
            switch (l.getStatu()) {
                case CRITIQUE:
                    this.moral=moral-2;
                    int tdActionRemove=boss.dice(10);
                    removeActionUpTo(tdActionRemove);
                    this.pose=Pose.PRONE;
                    this.immobilize=true;
                    break;
                case GRAVE_TETE:
                    moral=moral-1;
                    incoscient=true;
                    immobilize=true;
                     tdActionRemove=boss.dice(6);//TODO levere se action fro the pool 
                     removeActionUpTo(tdActionRemove);
                    break;
                case GRAVE:
                    moral=moral-1;
                     tdActionRemove=boss.dice(6);
                     removeActionUpTo(tdActionRemove);
                    
                    //TODO one action per turn
                    break;
                case GRAVE_BRASE_DROITE:
                    moral=moral-1;
                    tdActionRemove=boss.dice(6);
                    removeActionUpTo(tdActionRemove);
                    armeUtilise=null;//todo drop items
                    
                    //TODO one action per turn
                    break;     
                case GRAVE_BRASE_GAUCHE:
                    moral=moral-1;
                    tdActionRemove=boss.dice(6);
                    removeActionUpTo(tdActionRemove);
                    armeUtilise.setDegat(true);
                    //TODO one action per turn
                    break;                    
                case LEGER_BLESSE:
                    tdActionRemove=4;
                    removeActionUpTo(tdActionRemove);
                    break;
                case MANQUE:
                    tdActionRemove=2;
                    removeActionUpTo(tdActionRemove);
                default:
                    break;
            }
            if(sante<=0) incoscient=true;//TODO uncoinscious 
            
           
        }
        
        objective=true;

        
    }

    public Statut getStatu() {
        return st;
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
    public GeneriqueArme getArmeUtilise() {
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
public int getNumLesion(){
    int n=0;
    for (int k = 0; k < lesion.length; k++) {
        if(lesion[k]!=null && lesion[k].getGravite()!=Lesion.Degre.MANQUE &&
                lesion[k].getGravite()!=Lesion.Degre.NODEGRE)n++;
    }
    return n;
}
   public int getBlindage(Corp.CorpParts part){
       if(blindage!=null){
        CorpPart p=  blindage.getCorpPart(part);
        System.out.println("part "+p);
        GeneriqueBlindageEquipment bli=p.getBlindage();
        System.out.println("part bli"+p.getBlindage());
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
                this.blindage, this.moral,
                this.commandControler, this.face,boss);
        s.setTempDesponible(this.tempDesponible);
        s.setI(this.getI());
        s.setJ(this.getJ());
        s.arrayN = this.arrayN;
        GeneriqueArme armeClone[] = null;
        if (equipmentGen != null) {
            armeClone = new GeneriqueArme[equipmentGen.length];
            for (int a = 0; a < equipmentGen.length; a++) {
                armeClone[a] = equipmentGen[a].cloneEquipmentGenerique();
            }
            s.setArmeEquip(armeClone);
            s.setArmeUtilise(this.armeUtilise);
        }

        return s;
    }
    public int fire(BaseAction act) throws ModeDeFeuException,LoadMagazineFiniException{
        int score = 0;int i1=act.getI1(),j1=act.getJ1();
        Soldat target=(Soldat)act.getAntagoniste();
        if(target!=null) {
            i1=target.getI();
            j1=target.getJ();
        }
        double dist = Carte.distance(i, j, i1,j1, FXCarte.TILE_SIZE);
        int shotN = armeUtilise.hitsNumMF(dist);
        System.out.println("hits " + shotN);
        int cDM = combatDistanceModifier(target, act.getType());
        System.out.println("combat modifier " + cDM);
            for(int hits=0;hits<shotN;hits++){
                int dice=boss.dice(10);
                int porteMod=armeUtilise.porteModifier(dist);
                System.out.println(" porte modifie="+porteMod);
                int tg=porteMod+competenceArme+cDM;
                System.out.println("dice "+dice+"<=target number "+tg);
                if(dice<=tg) score++;
            }
        
      this.tempDesponible=tempDesponible-armeUtilise.feuArme(dist);
      return score;
    
    }
    int combatDistanceModifier(Soldat target,ActionType t){
            int cDM=-isWounded();

            if(armeUtilise.getMF()==FeuMode.RA ) 
                cDM=cDM-1;
            else if(armeUtilise.getMF()==FeuMode.PA)
                cDM=cDM-3;
            
            if(actionActuel==ActionType.MARCHE)cDM=cDM-2;//TODO questo non so se si puo fare  se marcia non fa fuoco ...pensare ad una soluzione...
            else if(actionActuel==ActionType.COURS)cDM=cDM-4;
            
            if(t==ActionType.FEU_VISER)cDM=cDM+1;



            if(pose==Piece.Pose.PRONE) cDM=cDM+1;
            
            
            if(target!=null && target.getAction()==ActionType.COURS) cDM=cDM-2;
            else if(target!=null && target.getAction()==ActionType.MARCHE)cDM=cDM-1;
            if(target!=null && target.getPose()==Piece.Pose.PRONE) cDM=cDM-1;    
            System.out.println("combat distance modifier "+cDM);
            return cDM;
    
    }
    @Override
    public String toString() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"\n"
                +"Temp Desponible:"+tempDesponible+"\n"
                + "" +((armeUtilise!=null)? armeUtilise.toString():"") + ",face:"+face+",(i="+i+",j="+j+")";
                
    }
    



    public boolean isPossileDesplacer(){
        return tempDesponible>0 &&
                !isChoc() &&
                !isImmobilize() &&
                !isIncoscient() &&
                !isKIA();
        
    }
    public boolean isPossibleCourse(){
        return st!=Statut.GRAVE;
       
    }

    @Override
    public String toStringSimple() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"";
                
    }
    public boolean isTempDisponiblePour(ActionType actiontype)throws Exception{
        int baseTempDisponible=tempNecessarieDesActionBase(actiontype);
        return (tempDesponible>=baseTempDisponible);
        
    }
    public void resetAction(){
    this.actionsPool=new ArrayList<>();
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
           throw new ImmobilzedSodlatException();
        }else if(st==Statut.GRAVE_TETE  ){
            throw new ImmobilzedSodlatException();
        }else if(st==Statut.GRAVE){
            if(act.getType()==ActionType.COURS ||
                    act.getType()==ActionType.SAUT)
                throw new NotSautOrCourseSoldatException();
            super.addAction(act);
        }else if(st==Statut.LEGER_BLESSE){
            super.addAction(act);
        }else if(st==Statut.GRAVE_BRASE_GAUCHE){
            if (this.actionsPool.size() > 1) 
                throw new UnActionSoldatException();
            super.addAction(act);
            
        }else if(st==Statut.GRAVE_BRASE_GAUCHE){
            super.addAction(act);
        }
        
        
        
    }
   public boolean isObjective(){
       return this.objective;
   }
   //TODO riguardare le regole
   public boolean shellShockTest(){
       boolean b=false;
       if(isObjective()) 
           b= boss.dice(10)<=moral;//TODO vedere i modificatori di morale
       if(b) choc=true ;//TODO add an action run away nel pool di action
       return choc;
   }
   public void notBandage(){
      
      boolean b= boss.dice(10)<=10+sante;
      if(!b) sante=-10;//TODO at ending activqtion
   }
   public boolean isKIA(){
   return sante<=-10;
   
   }
    public boolean isActive() {
        return active;
    }

    public boolean isChoc() {
        return choc;
    }

@Override
    public int tempNecessarieDesActionBase(ActionType actionType)throws Exception{
        int apbase=-1;
        if(actionType==ActionType.FEU  ||
                actionType==ActionType.OCCASION_DE_FEU ) 
            apbase=this.armeUtilise.fireTempNecessarie(); 
        else apbase=actionType.TN();
        if(isDoubled())apbase=apbase*2;
        return apbase;
    }


    //TODO ????????? boohhh
   public void resetRondCheck(){
       this.objective=false;
       this.active=false;
       

   }

    public boolean isImmobilize() {
        return immobilize;
    }
   
   public boolean isDistanceLessMarcheMax(double dist){
      try{
            int apbase=this.tempNecessarieDesActionBase(ActionType.MARCHE);
            double value=dist*apbase;
            return value<=tempDesponible ;
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return false;
   }
   public boolean isFeuArmePaPorte(double d){
       double dlonge=armeUtilise.getDistancePorte(Porte.LONGE);
       System.out.println("distance "+d+" >="+dlonge+"");
       return d>=(dlonge*2);
       
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
       lesion[lesionN]=l;
   
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
    int tdMax=td,tdAction=0;
    for (BaseAction baseAction : actionsPool) {
        tdAction=tdAction+baseAction.getTempActivite();
        if(tdAction<=tdMax) {
            actionsPool.remove(baseAction);
            System.out.println("removed:"+baseAction);
        }
        
    }

}   
   
   
}
