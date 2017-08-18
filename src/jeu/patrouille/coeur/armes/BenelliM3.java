/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.armes;

import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;



/**
 *
 * @author Alessio Sardaro
 */
public class BenelliM3 extends GeneriqueArme{
    public BenelliM3(){
        super("Benelli M3",Model.BENELLI_M3,EquipmentType.FIRE_WEAPON,5,15,65);
        magazine=new Magazine[14];
        for(int i=0;i<14;i++){
            magazine[i]=new Magazine(Model.BENELLI_M3);
        }
        armeFeuModel=TEMP_RIFLE;
        shotNumMF[MODE_FEU_SS]=1;
        shotNumMF[MODE_FEU_SA]=NOTVALUE;
        shotNumMF[MODE_FEU_BU]=NOTVALUE;
        shotNumMF[MODE_FEU_FA]=NOTVALUE;

 
        TDfireWeapon[MODE_FEU_SS]= 3;
        TDfireWeapon[MODE_FEU_SA]= NOTVALUE;
        TDfireWeapon[MODE_FEU_BU]= NOTVALUE;
        TDfireWeapon[MODE_FEU_FA]= NOTVALUE;

        this.evaluateDamagePotentiel=4;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-1;
        this.doux=9;

        
        this.TDrecharge=6;
        this.modefeu=MODE_FEU_SS;
        
        
        
        
        
    }

    


    @Override
    public int hitsNumMF(int dist) throws ModeDeFeuException{
        int s=NOTVALUE;
        
        switch (getTypePorte(dist)) {
            case COURT:
                s=3;
                break;
            case MED:
                s=2;
                break;
            case LONGE:
                s=1;
                break;
            default:
                break;
        }
        if(s==NOTVALUE||modefeu!=MODE_FEU_SS ) 
            throw new ModeDeFeuException("Mode de feu pa possible");

        return s;
        
    }
    
 

    @Override
    public GeneriqueArme cloneEquipmentGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "BenelliM3{" + '}';
    }

    
    
}
