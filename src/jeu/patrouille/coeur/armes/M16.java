/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.armes;

import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;

/**
 *
 * @author appleale
 */
public class M16 extends GeneriqueArme{
    
    public M16(){
        super("M16 A2", Model.M16, EquipmentType.FIRE_WEAPON, 20, 40, 260);
        this.armeFeuModel=TEMP_RIFLE;
        this.shotNumMF[MODE_FEU_SA]=1;
        this.shotNumMF[MODE_FEU_BU]=3;
        this.shotNumMF[MODE_FEU_FA]=NOTVALUE;
        this.shotNumMF[MODE_FEU_SS]=NOTVALUE;
        this.magazine=new Magazine[7];
        for(int n=0;n<7;n++)
            magazine[n]=new Magazine(Model.M16);
        this.TDrecharge=3;
        this.evaluateDamagePotentiel=3;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-2;
        this.doux=5;        
        this.fort=NOTVALUE;
        
    
    }

    @Override
    public GeneriqueArme cloneEquipmentGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
