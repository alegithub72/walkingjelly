/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.armes.Magazine.*;/**
 *
 * @author Alessio Sardaro
 */
public class AK74 extends GeneriqueArme{

    public AK74() {
        super("AK74",Model.AK74,EquipmentType.FIRE_WEAPON, 15, 30, 200);
        this.evaluateDamagePotentiel=3;
        this.evaluateModifierBlindee=-2;
        this.doux=5;
        this.fort=NOTVALUE;
        this.TDrecharge=3;
        this.magazine=new Magazine[]{new Magazine(Model.AK74,SubType.STANDARD),
            new Magazine(Model.AK74,SubType.STANDARD),new Magazine(Model.AK74,SubType.STANDARD),new Magazine(Model.AK74,SubType.STANDARD)};
        this.evaluateDificulte=3;

        armeFeuModel=TEMP_RIFLE;
        this.shotNumMF[FeuMode.SC.ordinal()]=NOTVALUE;
        this.shotNumMF[FeuMode.SA.ordinal()]=3;
        this.shotNumMF[FeuMode.RA.ordinal()]=5;
        this.shotNumMF[FeuMode.PA.ordinal()]=10;
        this.TDfireWeapon[FeuMode.SA.ordinal()]=3;
        this.TDfireWeapon[FeuMode.RA.ordinal()]=5;
        this.TDfireWeapon[FeuMode.PA.ordinal()]=6;
        this.TDfireWeapon[FeuMode.SC.ordinal()]=NOTVALUE;
        load=magazine[0];
        modefeu=FeuMode.SA;
        
        
    }

    @Override
    public GeneriqueArme cloneEquipmentGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "AK74{" + '}';
    }


    
}
