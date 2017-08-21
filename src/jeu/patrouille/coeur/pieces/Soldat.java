/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import com.sun.jmx.remote.util.OrderClassLoaders;
import java.util.ArrayList;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.enums.OrdreAction;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.exceptions.KilledSoldatException;
import jeu.patrouille.coeur.pieces.Lesion.*;
import jeu.patrouille.coeur.pieces.exceptions.ImmobilzedSodlatException;
import jeu.patrouille.coeur.pieces.exceptions.NotSautOrCourseSoldatException;
import jeu.patrouille.coeur.pieces.exceptions.UnActionSoldatException;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme.*;

/**
 *
 * @author Alessio Sardaro
 */
public class Soldat extends Piece {
    


    public static final int CLASS_SGT=8,CLASS_TEN=20,CLASS_SGT_MJR=15,CLASS_SOLDAT=7;
    public static final int FULL_SANTE=6;
    OrdreAction actionActuel=OrdreAction.PA_ACTION;
    Direction face;
    String nom=null;
    String nomDeFamilie=null;
    int competenceArme=-1;
    int connaissanceArme=-1;
    int combatRapproche=-1;
    int force=-1;
    int courage=-1;
    int sante=-1;
    int blindage=-1;
    int moral=-1;
    int commandControler=-1;
    int classement=-1;
    GeneriqueArme armeUtilise;
    Pose pose=Pose.DROIT;
    GeneriqueEquipment[] equipmentGen;
    boolean equipePorte;
    boolean incoscient;
    boolean objective;
    boolean active;
    boolean choc;
    boolean immobilize;
    Statu st;


 
   
    
    public Soldat(
            String nom,
            String nomDeFamilie,
            int competenceArme,
            int connaissanceArme,
            int combatRapproche,
            int force,
            int courage,
            int sante,
            int blindage,
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
        st=Statu.NORMAL;
        incoscient=false;
        objective=false;
        immobilize=false;
        active=false;
        choc=false;
       
        
    
    }

    public boolean isDoubled() {
        return st==Statu.GRAVE;
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
        if(l.statu==Lesion.Statu.CRITIQUE 
                && l.location==Lesion.TETE) {
            sante=-10;
            st=Statu.CRITIQUE;
            
        }
        else {
            this.sante=sante-l.blessure;
            if(null!=l.statu) switch (l.statu) {
                case CRITIQUE:
                    this.moral=moral-2;
                    tempDesponible=tempDesponible-boss.dice(10);
                    this.pose=Pose.PRONE;
                    this.immobilize=true;
                    break;
                case GRAVE_TETE:
                    moral=moral-1;
                    incoscient=true;
                    immobilize=true;
                    tempDesponible=tempDesponible-boss.dice(6);                    
                    break;
                case GRAVE:
                    moral=moral-1;
                    tempDesponible=tempDesponible-boss.dice(6);
                    if(l.location==Lesion.BRAS_DROITE ||
                            l.location==Lesion.BRAS_GAUCHE)
                        armeUtilise=null;
                    
                    //TODO one action per turn
                    break;
                case LEGER_BLESSE:
                    tempDesponible=tempDesponible-4;
                    break;
                case MANQUE:
                    tempDesponible=tempDesponible-2;
                    
                default:
                    break;
            }
           
        }
        
        objective=true;

        
    }

    public Statu getStatu() {
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

 

    
    
    public void setClassement(int classement) {
        this.classement = classement;
    }

    public int getClassement() {
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

    public int getB() {
        return blindage;
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
   public void setAction(OrdreAction a){
    actionActuel=a;
   }
   public OrdreAction getAction(){
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
    public int fire(double dist) throws ModeDeFeuException,LoadMagazineFiniException{
      int  n=armeUtilise.hitsNumMF(dist);
      this.tempDesponible=tempDesponible-armeUtilise.feuArme();
      return n;
    
    }
    @Override
    public String toString() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"\n"
                +"Temp Desponible:"+tempDesponible+"\n"
                + "" +((armeUtilise!=null)? armeUtilise.toString():"") + ",face:"+face+",(i="+i+",j="+j+")";
                
    }
    



    public boolean isPossileDesplacer(){
        return tempDesponible>0 && st!=Statu.GRAVE_TETE &&
                   st!=Statu.CRITIQUE && sante>=-10 && !choc;
        
    }
    public boolean isPossibleCourse(){
        return st!=Statu.GRAVE;
       
    }

    @Override
    public String toStringSimple() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"";
                
    }
    public boolean isTempDisponiblePour(OrdreAction actiontype)throws Exception{
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
        else if(st==Statu.CRITIQUE && sante<=-10){
            throw new KilledSoldatException();
        }
        else if(st==Statu.CRITIQUE ){
           throw new ImmobilzedSodlatException();
        }else if(st==Statu.GRAVE_TETE  ){
            throw new ImmobilzedSodlatException();
        }else if(st==Statu.GRAVE){
            if(act.getType()==OrdreAction.COURS ||
                    act.getType()==OrdreAction.SAUT)
                throw new NotSautOrCourseSoldatException();
            super.addAction(act);
        }else if(st==Statu.LEGER_BLESSE){
            super.addAction(act);
        }else if(st==Statu.GRAVE_BRASE){
            if (this.actionsPool.size() > 1) 
                throw new UnActionSoldatException();
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
    public int tempNecessarieDesActionBase(OrdreAction actionType)throws Exception{
        int apbase=-1;
        if(actionType==OrdreAction.FEU  ||
                actionType==OrdreAction.OCCASION_DE_FEU ) 
            apbase=this.armeUtilise.fireTempNecessarie(); 
        else apbase=actionType.TN();
        if(isDoubled())apbase=apbase*2;
        return apbase;
    }



   public void resetRondCheck(){
       this.objective=false;
       this.active=false;
       

   }

    public boolean isImmobilize() {
        return immobilize;
    }
   
   public boolean isDistanceLessMarcheMax(double dist){
      try{
            int apbase=this.tempNecessarieDesActionBase(OrdreAction.MARCHE);
            double value=dist*apbase;
            return tempDesponible<=value ;
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
}
