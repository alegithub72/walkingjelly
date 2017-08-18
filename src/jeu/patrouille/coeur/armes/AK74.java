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
public class AK74 extends GeneriqueArme{

    public AK74() {
        super("AK74",Model.AK74,EquipmentType.FIRE_WEAPON, 15, 30, 200);
        this.evaluateDamagePotentiel=3;
        this.evaluateModifierBlindee=-2;
        this.modefeu=MODE_FEU_SA;
        this.doux=5;
        this.fort=NOTVALUE;
        this.TDrecharge=3;
        this.magazine=new Magazine[]{new Magazine(Model.AK74),
            new Magazine(Model.AK74),new Magazine(Model.AK74),new Magazine(Model.AK74)};
        this.evaluateDificulte=3;

        armeFeuModel=TEMP_RIFLE;
 
        this.shotNumMF[MODE_FEU_SA]=1;
        this.shotNumMF[MODE_FEU_BU]=5;
        this.shotNumMF[MODE_FEU_FA]=10;
        this.TDfireWeapon[MODE_FEU_SA]=3;
        this.TDfireWeapon[MODE_FEU_BU ]=5;
        this.TDfireWeapon[MODE_FEU_FA]=6;
        this.TDfireWeapon[MODE_FEU_SS]=NOTVALUE;
        
        
        
        
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
