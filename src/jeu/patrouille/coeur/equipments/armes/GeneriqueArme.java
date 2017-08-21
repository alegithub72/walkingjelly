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
    int[] TDfireWeapon = new int[4];
    //Map shotNumMFMed;
    //Map shotNumMFLonge;
    FeuMode modefeu;
    int doux = NOTVALUE;
    int fort = NOTVALUE;
    Magazine[] magazine;
    int armeFeuModel;
    int finalCartouch;
    int evaluateDamagePotentiel = NOTVALUE;
    int evaluateModifierBlindee = NOTVALUE;
    int evaluateDificulte = NOTVALUE;
    Magazine load;
    int TDrecharge = NOTVALUE;
    int magazineUsed;

    public int getArmeFeuModel() {
        return armeFeuModel;
    }

    public GeneriqueArme(String nom,Model model,EquipmentType type ,int court, int medium, int longe) {
        super(nom, type, model);
        TDfireWeapon=new int[4];
        porte[Porte.COURT.ordinal()] = court;
        porte[Porte.LONGE.ordinal()] = medium;
        porte[Porte.LONGE.ordinal()] = longe;
        



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
    public int feuArme() throws ModeDeFeuException,LoadMagazineFiniException {
        finalCartouch = load.depot(modefeu);
        int ap = TDfireWeapon[modefeu.ordinal()];
        if (ap == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu not avaiable");
        }
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

    public FeuMode getMF() {
        return modefeu;
    }
    


    public int hitsNumMF(double dist) throws ModeDeFeuException {
        int sn = NOTVALUE;
        sn = shotNumMF[modefeu.ordinal()];
        
        if (sn == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu pa possible");
        }
        if(finalCartouch>sn) return finalCartouch;
        return sn;

    }

    public int getDoux() {
        return doux;
    }

    public int getFort() {
        return fort;
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
    abstract public GeneriqueArme cloneEquipmentGenerique();


   
    public double getDistancePorte(Porte p){
        return porte[p.ordinal()];
    }
    
    public int getNumMagazine(){
        return (magazine.length-magazineUsed);
    }

}
