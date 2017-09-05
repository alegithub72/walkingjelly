/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.armes.Magazine.*;
import jeu.patrouille.coeur.terrains.Terrain;

/**
 *
 * @author appleale
 */
public class M16 extends GeneriqueArme{
    
    public M16(){
        super("M16 A2", Model.M16, EquipmentType.FIRE_WEAPON, 20, 40, 260);
        this.tempModel=TEMP_RIFLE;
    
        this.shotNumMF[FeuMode.SA.ordinal()]=1;
        this.shotNumMF[FeuMode.RA.ordinal()]=3;


        this.magazine=new Magazine[7];
        for(int n=0;n<7;n++)
            magazine[n]=new Magazine(Model.M16,SubType.STANDARD);
        this.TDrecharge=3;
        this.evaluateDamagePotentiel=3;
        this.evaluateDificulte=3;
        this.evaluateModifierBlindee=-2;
        this.coverPenetration[Terrain.Consistance.LEGER.ordinal()]  =5;        

        load=magazine[0];
        modefeu=FeuMode.SA;

        TDfireWeapon[FeuMode.SA.ordinal()]=3;
        TDfireWeapon[FeuMode.RA.ordinal()]=5;
        finalCartouch=load.capacity;
    
    }

    
    

}
