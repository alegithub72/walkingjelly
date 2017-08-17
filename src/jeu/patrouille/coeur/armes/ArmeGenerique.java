/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.armes;



/**
 *
 * @author Alessio Sardaro
 */
public abstract class  ArmeGenerique {

public final int NOTVALUE=-111; 
String nom;
public static final int COURT=0, MED=1,LONGE=2;
public static final int MODE_FEU_SS=0,MODE_FEU_SA=1,MODE_FEU_BU=2,MODE_FEU_FA=3;
public static final int TEMP_SHOTGUN=0,TEMP_RIFLE=1,TEMP_PISTOL=2,TEMP_MACHINE_GUN=3;
 int porte[]=new int[3];
 int shotNumMF[]=new int[4];
 int APfireWeapon[]=new int[4];
 int armeType;
 //Map shotNumMFMed;
 //Map shotNumMFLonge;
 int modefeu;
 int doux=NOTVALUE;
 int fort=NOTVALUE;
 int maxCharge;

 

int evaluateDamagePotentiel=NOTVALUE;
int evaluateModifierBlindee=NOTVALUE;
int evaluateDificulte=NOTVALUE;
int capaciteMunition=NOTVALUE;
int APrecharge=NOTVALUE;

    public ArmeGenerique(String nom,int court,int medium,int longe) {
        this.nom=nom;

       porte[COURT]= court;
       porte[MED]= medium;
       porte[LONGE]=longe;
    }
    public String getNom(){
    return nom;
    }
    
    public int getEDP(){
        return evaluateDamagePotentiel;
    }
    public int getEMB(){
        return evaluateModifierBlindee;
    }
    public int getED(){
        return evaluateDificulte;
    }
    public int fireWeapon() throws ModeDeFeuException{
        capaciteMunition--;
        int ap= APfireWeapon[modefeu];
        if (ap==NOTVALUE) throw new ModeDeFeuException("Mode de feu not avaiable");
        return ap;
    }
    public int getCM(){
        return capaciteMunition;
    }

    public void changeModeFeu(int f) throws ModeDeFeuException{
        modefeu=f;
    }
    public int getMF(){
        return modefeu;
    }
    public int shotNumMF(int dist)throws ModeDeFeuException{
        int sn=NOTVALUE;
        sn=shotNumMF[modefeu];
        
        if(sn==NOTVALUE) throw new ModeDeFeuException("Mode de feu pa possible");
        return sn;
    
    }
    public int getDoux(){
        return doux;
    }
    public int getFort(){
        return fort;
    }
    int getTypePorte(int dist){
    if(dist<=porte[COURT]) return COURT;
    else if(dist<=porte[MED]) return MED;
    else if(dist<=porte[LONGE]) return LONGE;
    return NOTVALUE;
    }
    
    public int porteModifier(int dist){
        int tp=getTypePorte(dist);
     if(tp==COURT) return +1;
     else if(tp==MED) return 0;
     else if(tp==LONGE) return -1;
     else return NOTVALUE;//impossible to achieve out of the range
    
    }
    public int rechargeArme(){
        rempirMunition();
        return APrecharge;
    }
 
    public void rempirMunition() {
       capaciteMunition=maxCharge;
    }

    abstract public  ArmeGenerique cloneArmeGenerique();

    @Override
    public String toString() {
        return  this.nom + ""
                + ":" + capaciteMunition + "";
    }
    
     
}
