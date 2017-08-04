/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;



import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.armes.ArmeGenerique;

/**
 *
 * @author appleale
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
    ArmeGenerique armeUtilise;

    ArmeGenerique[] armeEquip;

    public void setArmeEquip(ArmeGenerique[] armeEquip) {
        this.armeEquip = armeEquip;
        if(armeEquip.length>0) armeUtilise=armeEquip[0];
    }

    public ArmeGenerique getArmeUtilise() {
        return armeUtilise;
    }

    public void setArmeUtilise(ArmeGenerique armeUtilise) {
        this.armeUtilise = armeUtilise;
    }

    
    
    public void setClassement(int classement) {
        this.classement = classement;
    }

    public int getClassement() {
        return classement;
    }
    
    


    public Soldat(String nom,String nomDeFamilie,
    int competenceArme,
    int connaissanceArme,
    int combatRapproche,
    int force,
    int courage,
    int sante,
    int blindage,
    int moral,
    int commandControler,Direction d){
        super(ActeurType.SOLDAT);
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
                this.commandControler, this.face);
        s.setActionPoint(this.tempDesponible);
        s.setI(this.getI());
        s.setJ(this.getJ());
        s.arrayN = this.arrayN;
        ArmeGenerique armeClone[] = null;
        if (armeEquip != null) {
            armeClone = new ArmeGenerique[armeEquip.length];
            for (int a = 0; a < armeEquip.length; a++) {
                armeClone[a] = armeEquip[a].cloneArmeGenerique();
            }
            s.setArmeEquip(armeClone);
            s.setArmeUtilise(this.armeUtilise);
        }

        return s;
    }

    @Override
    public String toString() {
        return ""
                + "" + nom +" "+ nomDeFamilie +"\n"
                +"Temp Desponible:"+tempDesponible+"\n"
                + "" +((armeUtilise!=null)? armeUtilise.toString():"") + "";
                
    }



    public boolean isPossileDesplacer(){
        return tempDesponible>0;
        
    }
    public int getActionPoint(){
        return tempDesponible;
    }

   
    
   
}
