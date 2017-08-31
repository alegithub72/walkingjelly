/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.terrains.Terrain;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class GeneriqueArme extends GeneriqueEquipment {


    public enum Porte {COURT,MED,LONGE}
    public enum FeuMode{SC,SA,RA,PA}
    public static final int TEMP_SHOTGUN = 0, TEMP_RIFLE = 1, TEMP_PISTOL = 2, TEMP_MACHINE_GUN = 3;

    int porte[] = new int[3];
    int shotNumMF[] = new int[4];
    int[] TDfireWeapon = new int[FeuMode.values().length];
    //Map shotNumMFMed;
    //Map shotNumMFLonge;
    FeuMode modefeu;
    int tempModel;
    int coverPenetration[];

    Magazine[] magazine;

    int finalCartouch;
    int evaluateDamagePotentiel = NOTVALUE;
    int evaluateModifierBlindee = NOTVALUE;
    int evaluateDificulte = NOTVALUE;
    Magazine load;
    int TDrecharge = NOTVALUE;
    int magazineUsed;
    boolean degat=false;

    public boolean isDegat() {
        return degat;
    }

    public void setDegat(boolean degat) {
        this.degat = degat;
    }



    public FeuMode getArmeFeuModel() {
        return this.modefeu;
    }

    public GeneriqueArme(String nom,Model model,EquipmentType type ,int court, int medium, int longe) {
        super(nom, type, model);
        TDfireWeapon=new int[4];
        porte[Porte.COURT.ordinal()] = court;
        porte[Porte.LONGE.ordinal()] = medium;
        porte[Porte.LONGE.ordinal()] = longe;
        for (FeuMode f : FeuMode.values()) {
            shotNumMF[f.ordinal()]=NOTVALUE;
        }
        for(FeuMode f:FeuMode.values()){
            TDfireWeapon[f.ordinal()]=NOTVALUE;
        }
        this.coverPenetration=new int[2];
        this.coverPenetration[Terrain.Consistance.LEGER.ordinal()] =NOTVALUE;
        this.coverPenetration[Terrain.Consistance.DUR.ordinal()] =NOTVALUE;
    }

    public FeuMode[] armeFeuModeDisponible(){
        int n=0;
        for(int h:shotNumMF) if(h!=NOTVALUE) n++;
        FeuMode[] list=new FeuMode[n];
        for(int k=0 ;k<shotNumMF.length;k++) 
            if(shotNumMF[k]!=NOTVALUE) 
                list[--n]=FeuMode.values()[k];
        
        return list;
    
    }   
    
    // public abstract void loadMagazine();
    public int getEDP() {
        return evaluateDamagePotentiel;
    }

    public int getEMB() {
        return evaluateModifierBlindee;
    }

    public int getED() {
        return evaluateDificulte;
    }

    //TODO vedere per avere AP e shot
    public int feuArme(double dist) throws ModeDeFeuException,LoadMagazineFiniException {
        int ap = TDfireWeapon[modefeu.ordinal()];
        if (ap == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu not avaiable");
        }        
        int n=hitsNumMF(dist);
        finalCartouch = load.depot(n);


        return ap;
    }
    public int fireTempNecessarie()throws ModeDeFeuException{
        int td= TDfireWeapon[modefeu.ordinal()];
        if (td == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu not avaiable");
        }
        return td;
    }
    public void changeModeFeu(FeuMode f) throws ModeDeFeuException {
        modefeu = f;
    }


    


    public int hitsNumMF(double dist) throws ModeDeFeuException {
        int sn = NOTVALUE;
        System.out.println(" mode defeu:" +modefeu);
        sn = shotNumMF[modefeu.ordinal()];
        
        if (sn == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu pa possible");
        }
        if(finalCartouch>sn) return finalCartouch;
        return sn;

    }

public int getCoverPenetration(Terrain.Consistance c){
    return this.coverPenetration[c.ordinal()];
}

    Porte getTypePorte(double dist) {
        if (dist <= porte[Porte.COURT.ordinal()]) {
            return Porte.COURT;
        } else if (dist <= porte[Porte.MED.ordinal()]) {
            return Porte.MED;
        } else if (dist <= porte[Porte.LONGE.ordinal()]) {
            return Porte.LONGE;
        }
        return null;
    }

    public int porteModifier(double dist) {
        Porte tp = getTypePorte(dist);
        switch (tp) {
            case COURT:
                return +1;
            case MED:
                return 0;
            case LONGE:
                return -1;
            default:
                return NOTVALUE;//impossible to achieve out of the range
        }
    }
    
    public int rechargeArme() throws PaDeMagazineException {
        loadMagazine();
        return TDrecharge;
    }

    public void loadMagazine() throws PaDeMagazineException {
        load = null;
        for (Magazine m : magazine) {
            if (m.getQuantity() == m.getCapacity()) {
                load = m;
                magazineUsed++;
            }
        }
        if (load == null) {
            throw new PaDeMagazineException();
        }
    }
    public int shotRemain(){
    return load.quantity;
    
    }





   
    public double getDistancePorte(Porte p){
        return porte[p.ordinal()];
    }
    
    public int getNumMagazine(){
        return (magazine.length-magazineUsed);
    }

}
