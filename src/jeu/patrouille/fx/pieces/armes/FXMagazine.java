/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces.armes;

import jeu.patrouille.coeur.armes.GeneriqueEquipment;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;

/**
 *
 * @author appleale
 */
public class FXMagazine extends FXPatrouilleSprite{

     FXMagazine(int h,int w,String f) {
        super(h, w, f, null);
    }
    public static FXMagazine createInstance(GeneriqueEquipment.Model m){
        if(GeneriqueEquipment.Model.AK74==m) return new FXMagazine(23,28,"caricatoreAK.png");
        if(GeneriqueEquipment.Model.M16==m)return new FXMagazine(20,20,"caricatoreM16.png");
        if(GeneriqueEquipment.Model.BENELLI_M3==m) return new FXMagazine(20,20, "cartucciera.png");
        return null;
    }
}
