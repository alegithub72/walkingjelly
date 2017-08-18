/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.armes;

import jeu.patrouille.coeur.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.armes.exceptions.PaDeMagazineException;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class GeneriqueArme extends GeneriqueEquipment {



    public static final int COURT = 0, MED = 1, LONGE = 2;
    public static final int MODE_FEU_SS = 0, MODE_FEU_SA = 1, MODE_FEU_BU = 2, MODE_FEU_FA = 3;
    public static final int TEMP_SHOTGUN = 0, TEMP_RIFLE = 1, TEMP_PISTOL = 2, TEMP_MACHINE_GUN = 3;

    int porte[] = new int[3];
    int shotNumMF[] = new int[4];
    int[] TDfireWeapon = new int[4];
    //Map shotNumMFMed;
    //Map shotNumMFLonge;
    int modefeu;
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


    public GeneriqueArme(String nom,Model model,EquipmentType type ,int court, int medium, int longe) {
        super(nom, type, model);
        TDfireWeapon=new int[4];
        porte[COURT] = court;
        porte[MED] = medium;
        porte[LONGE] = longe;
        magazine=new Magazine[14];

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
    public int fireWeapon() throws ModeDeFeuException,LoadMagazineFiniException {
        finalCartouch = load.fire(modefeu);
        int ap = TDfireWeapon[modefeu];
        if (ap == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu not avaiable");
        }
        return ap;
    }

    public void changeModeFeu(int f) throws ModeDeFeuException {
        modefeu = f;
    }

    public int getMF() {
        return modefeu;
    }

    public int hitsNumMF(int dist) throws ModeDeFeuException {
        int sn = NOTVALUE;
        sn = shotNumMF[modefeu];
        
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

    int getTypePorte(int dist) {
        if (dist <= porte[COURT]) {
            return COURT;
        } else if (dist <= porte[MED]) {
            return MED;
        } else if (dist <= porte[LONGE]) {
            return LONGE;
        }
        return NOTVALUE;
    }

    public int porteModifier(int dist) {
        int tp = getTypePorte(dist);
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


   

    
    public int getNumMagazine(){
        return magazine.length;
    }

}
