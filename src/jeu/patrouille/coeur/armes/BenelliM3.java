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
public class BenelliM3 extends ArmeGenerique{
    public BenelliM3(){
        super("Benelli M3",5,15,65);
        armeType=TEMP_SHOTGUN;
        maxCharge=7;
        APfireWeapon=new int[4];
        
        shotNumMF[MODE_FEU_SS]=1;
        shotNumMF[MODE_FEU_SA]=NOTVALUE;
        shotNumMF[MODE_FEU_BU]=NOTVALUE;
        shotNumMF[MODE_FEU_FA]=NOTVALUE;

 
        APfireWeapon[MODE_FEU_SS]= 3;
        APfireWeapon[MODE_FEU_SA]= NOTVALUE;
        APfireWeapon[MODE_FEU_BU]= NOTVALUE;
        APfireWeapon[MODE_FEU_FA]= NOTVALUE;

        this.evaluateDamagePotentiel=4;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-1;
        this.doux=9;
        this.capaciteMunition=7;
        
        this.APrecharge=6;
        this.modefeu=MODE_FEU_SS;
        
        
        
        
    }

    


    @Override
    public int shotNumMF(int dist) throws ModeDeFeuException {
        int s=NOTVALUE;
        
        if(getTypePorte(dist)==COURT) s=3;
        else if(getTypePorte(dist)==MED)s=2;
        else if(getTypePorte(dist)==LONGE)s=1;
        if(s==NOTVALUE||modefeu!=MODE_FEU_SS ) throw new ModeDeFeuException("Mode de feu pa possible");
        return s;
        
    }
    


    @Override
    public ArmeGenerique cloneArmeGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
