/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.Magazine.*;

/**
 *
 * @author Alessio Sardaro
 */
public class BenelliM3 extends GeneriqueArme{
    public BenelliM3(){
        super("Benelli M3",Model.BENELLI_M3,EquipmentType.FIRE_WEAPON,5,15,65);
        magazine=new Magazine[14];
        for(int i=0;i<14;i++){
            magazine[i]=new Magazine(Model.BENELLI_M3,SubType.SLUG);
        }
        armeFeuModel=TEMP_RIFLE;
        shotNumMF[FeuMode.SC.ordinal()]=1;
        shotNumMF[FeuMode.SA.ordinal()]=NOTVALUE;
        shotNumMF[FeuMode.RA.ordinal()]=NOTVALUE;
        shotNumMF[FeuMode.PA.ordinal()]=NOTVALUE;

 
        TDfireWeapon[FeuMode.SC.ordinal()]= 3;
        TDfireWeapon[FeuMode.SA.ordinal()]= NOTVALUE;
        TDfireWeapon[FeuMode.RA.ordinal()]= NOTVALUE;
        TDfireWeapon[FeuMode.PA.ordinal()]= NOTVALUE;

        this.evaluateDamagePotentiel=4;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-1;
        this.doux=9;

        
        this.TDrecharge=6;
        this.modefeu=FeuMode.SC;
        load=magazine[0];
        
        
        
        
    }

    


    @Override
    public int hitsNumMF(double dist) throws ModeDeFeuException{
        int s=NOTVALUE;
     
        if(load.sub==SubType.BUCK){
            switch (getTypePorte((int)dist)) {
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
        }else s=shotNumMF[modefeu.ordinal()];
        
        
        if(s==NOTVALUE ) 
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