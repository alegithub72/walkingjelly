/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.armes.Magazine.*;

/**
 *
 * @author appleale
 */
public class M16 extends GeneriqueArme{
    
    public M16(){
        super("M16 A2", Model.M16, EquipmentType.FIRE_WEAPON, 20, 40, 260);
        this.armeFeuModel=TEMP_RIFLE;
        this.shotNumMF[FeuMode.SA.ordinal()]=1;
        this.shotNumMF[FeuMode.RA.ordinal()]=3;
        this.shotNumMF[FeuMode.PA.ordinal()]=NOTVALUE;
        this.shotNumMF[FeuMode.SC.ordinal()]=NOTVALUE;
        this.magazine=new Magazine[7];
        for(int n=0;n<7;n++)
            magazine[n]=new Magazine(Model.M16,SubType.STANDARD);
        this.TDrecharge=3;
        this.evaluateDamagePotentiel=3;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-2;
        this.doux=5;        
        this.fort=NOTVALUE;
        load=magazine[0];
        modefeu=FeuMode.SA;
    
    }

    @Override
    public GeneriqueArme cloneEquipmentGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
