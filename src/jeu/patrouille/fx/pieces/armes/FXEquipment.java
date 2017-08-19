/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.pieces.armes;

import jeu.patrouille.coeur.armes.GeneriqueEquipment;
import jeu.patrouille.fx.sprite.FXPatrouilleSprite;
import jeu.patrouille.coeur.armes.GeneriqueEquipment.*;
/**
 *
 * @author appleale
 */
public class FXEquipment extends FXPatrouilleSprite{

     FXEquipment(int h,int w,String f) {
        super(h, w, f, null);
    }
    
public static FXEquipment createIstance(GeneriqueEquipment.Model mod){

    if(mod==GeneriqueEquipment.Model.AK74) 
        return new FXEquipment(140, 60, "AK47_2.png");
    if(mod==Model.BENELLI_M3) return new FXEquipment(140,60, "benelliM3.png");
    if(mod==Model.M16) return new FXEquipment(140,60, "M16.png");
    return null;

}

public void addUsed(double x){
    FXPatrouilleSprite sp=new FXPatrouilleSprite(40, 33, "usedArmes.png", null);
        sp.create();
    getChildren().add(sp);
    sp.toFront();
    sp.setTranslateX(x-25);
    sp.setTranslateY(10);
    sp.setVisible(true);

}
    
}
