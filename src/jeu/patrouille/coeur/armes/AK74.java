/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.armes;

/**
 *
 * @author appleale
 */
public class AK74 extends ArmeGenerique{

    public AK74() {
        super("AK74", 15, 30, 200);
        this.evaluateDamagePotentiel=3;
        this.evaluateModifierBlindee=-2;
        this.modefeu=MODE_FEU_SA;
        this.doux=5;
        this.fort=NOTVALUE;
        this.APrecharge=3;
        this.maxCharge=30;
        this.capaciteMunition=30;
        this.evaluateDificulte=3;
        armeType=TEMP_RIFLE;
        this.shotNumMF[MODE_FEU_SA]=1;
        this.shotNumMF[MODE_FEU_BU]=5;
        this.shotNumMF[MODE_FEU_FA]=10;
        this.APfireWeapon[MODE_FEU_SA]=3;
        this.APfireWeapon[MODE_FEU_BU ]=5;
        this.APfireWeapon[MODE_FEU_FA]=6;
        this.APfireWeapon[MODE_FEU_SS]=NOTVALUE;
        
        
        
        
    }

    @Override
    public ArmeGenerique cloneArmeGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
