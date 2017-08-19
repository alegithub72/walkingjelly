/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import java.util.ArrayList;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.armes.GeneriqueArme;
import jeu.patrouille.coeur.armes.GeneriqueEquipment;
import jeu.patrouille.coeur.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.exceptions.KilledSoldatException;
import jeu.patrouille.coeur.pieces.Lesion.*;
import jeu.patrouille.coeur.pieces.exceptions.ImmobilzedSodlatException;
import jeu.patrouille.coeur.pieces.exceptions.IncoscientSoldatException;
import jeu.patrouille.coeur.pieces.exceptions.MainArmeSoldatException;
import jeu.patrouille.coeur.pieces.exceptions.UnActionSoldatException;

/**
 *
 * @author Alessio Sardaro
 */
public class Soldat extends Piece {
    


    public static final int CLASS_SGT=8,CLASS_TEN=20,CLASS_SGT_MJR=15,CLASS_SOLDAT=7;

    int actionActuel=NOACTION;
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
    boolean actived;
    Statu st;

    public Soldat(String nom,String nomDeFamilie,
    int competenceArme,
    int connaissanceArme,
    int combatRapproche,
    int force,
    int courage,
    int sante,
    int blindage,
    int moral,
    int commandControler,Direction d,GeneriqueJoeurs boss){
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
        this.actived=false;
        st=Statu.NORMAL;
       
        
    
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }
    
    public void blessure(Lesion l){
        if(l.statu==Lesion.Statu.MORT) sante=-10;
        else this.sante=sante-l.blessure;
        if(sante<=0 && sante>-10) this.st=Statu.INCOSCIENT;
        else if(sante<=-10) this.st=Statu.MORT;
        
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

    public int getS() {
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
   public void setAction(int a){
    actionActuel=a;
   }
   public int getAction(){
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
        s.setActionPoint(this.tempDesponible);
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
      this.tempDesponible=tempDesponible-armeUtilise.fireWeapon();
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
        return tempDesponible>0 || st!=Statu.MORT ||
                   st!=Statu.IMMOBILSER
                    || st!=Statu.INCOSCIENT;
        
    }
    @Override
    public int getActionPoint(){
        return tempDesponible;
    }
    @Override
    public String toStringSimple() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"";
                
    }
   
    public void resetAction(){
    this.actionsPool=new ArrayList<>();
    }
    
   public  GeneriqueEquipment[] getEquipment(){
        return equipmentGen;
    }

   
   //TODO inserire vairiabile status UNCOSCIOUS,IMMOBILIZE,NORMAL,ETC....vedere bene health doc..
    @Override
    public void addAction(BaseAction act) throws Exception{
        if(sante==5) super.addAction(act);
        else if(st==Statu.MORT){
            throw new KilledSoldatException();
        }
        else if(st==Statu.IMMOBILSER ){
           throw new ImmobilzedSodlatException();
        }else if(st==Statu.INCOSCIENT){
            throw new IncoscientSoldatException();
        }else if(st==Statu.LEGER_BLESSE){
            super.addAction(act);
        }else if(st==Statu.UN_ACTION){
            if(this.actionsPool.size()>1) 
                throw new UnActionSoldatException();
        }else if(st==Statu.MAIN_ARME_SERIOUX){
            if(armeUtilise.getArmeFeuModel()!=GeneriqueArme.TEMP_PISTOL 
                    && (act.getType()==BaseAction.FEU || act.getType()==BaseAction.OP_FEU ))
                throw new MainArmeSoldatException();//TODO migliorare su tutte le azioni di fuoco
            super.addAction(act);
            
        }
        
        
        
    }
   
}
