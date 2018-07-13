/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.exceptions.ImpossibleRechargeArmeException;
import jeu.patrouille.coeur.equipments.armes.exceptions.IncompatibleMagazineException;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import jeu.patrouille.coeur.terrains.Terrain;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class GeneriqueArme extends GeneriqueEquipment {


    public enum Porte {COURT,MED,LONGE}
    public enum FeuMode{SC("Seul Coup"),SA("Semi Automatic"),RA("Rafale"),PA("Plein Automatic");
        public String txt;
        private FeuMode(String txt) {
            this.txt=txt;
        }
    }
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



    public FeuMode getArmeFeuMode() {
        return this.modefeu;
    }

    public GeneriqueArme(String nom,Model model,EquipmentType type ,int court, int medium, int longe) {
        super(nom, type, model);
        TDfireWeapon=new int[4];
        porte[Porte.COURT.ordinal()] = court;
        porte[Porte.MED.ordinal()] = medium;
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
        magazineUsed=0;
        
        
    }

    public FeuMode[] armeFeuModeDisponible(){
        int n=0;
        for(int h:shotNumMF) if(h!=NOTVALUE) n++;
        FeuMode[] list=new FeuMode[n];
        int h=0;
        for(int k=0 ;k<shotNumMF.length;k++) {
            if(shotNumMF[k]!=NOTVALUE) {
                list[h]=FeuMode.values()[k];
                h++;
            }
        }
        
        return list;
    
    }   
    public void addMagazine(Magazine m){
        Magazine[] list=new Magazine[this.magazine.length+1];
        for (int i = 0; i < list.length; i++) 
             list[i]=magazine[i];                            
        list[magazine.length]=m;
        magazine=list;
    
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
     
        int n=shotNumMF[modefeu.ordinal()];
        finalCartouch = finalCartouch- load.depot(shotNumMF[modefeu.ordinal()]);
        return n;
    }
    public int fireTempNecessarie(ActionType type)throws ModeDeFeuException{
        int td= TDfireWeapon[modefeu.ordinal()];
        if (td == NOTVALUE) {
            throw new ModeDeFeuException("Mode de feu not avaiable");
        }
        if(type==ActionType.FEU_VISER) td=td+((int)type.TN()*shotNumMF[modefeu.ordinal()]);
        return td;
    }
 
    public void changeModeFeu(FeuMode f) throws ModeDeFeuException {
        if(this.shotNumMF[f.ordinal()]==NOTVALUE) throw new ModeDeFeuException();
        modefeu = f;
    }

    public Magazine giveMagazine(){
        Magazine mGive=null;
        for (int i = 0; i < magazine.length; i++) {
            Magazine m= magazine[i];
            if(m!=load && 
                    m.capacity==m.quantity ) {
                mGive=m;
                magazine[i]=null;
                break;
            }
                
                
        }
        return mGive;
    
    }
    


    public int hitsNumMF(double dist) throws ModeDeFeuException {
        return 1;

    }
    public int getShotNumFeu(){
        return shotNumMF[modefeu.ordinal()];
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
    
    public int rechargeArme() throws PaDeMagazineException,IncompatibleMagazineException,ImpossibleRechargeArmeException {
        loadMagazine();
        return TDrecharge;
    }

    public void loadMagazine() throws PaDeMagazineException,IncompatibleMagazineException,ImpossibleRechargeArmeException {
        load = null;
        if(EquipmentType.FIRE_WEAPON!=equipmentType) throw new ImpossibleRechargeArmeException();
        for (Magazine m : magazine) {
            if (m.getQuantity() == m.getCapacity()) {
                load = m;
                finalCartouch=load.capacity;
                 
            }
        }        
        if (load == null) {
            throw new PaDeMagazineException();
        }else if(load.getModel()!=getModel())
            throw new IncompatibleMagazineException();
        magazineUsed++; 

    }
    public int shotRemain(){
    return load.quantity;
    
    }


    public boolean isAutomaticArme(){
        return this.TDfireWeapon[FeuMode.RA.ordinal()]!=NOTVALUE ||
                TDfireWeapon[FeuMode.PA.ordinal()]!=NOTVALUE ||
                TDfireWeapon[FeuMode.SA.ordinal()]>1;

    }


   
    public double getDistancePorte(Porte p){
        return porte[p.ordinal()];
    }
    
    public int getNumMagazine(){
        return (magazine.length-magazineUsed);
    }

}
