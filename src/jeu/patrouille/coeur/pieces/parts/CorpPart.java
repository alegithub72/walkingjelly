/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.equipments.GeneriqueBlindageEquipment;
import jeu.patrouille.coeur.equipments.KevlerCasqueEquipment;

/**
 *
 * @author appleale
 */
public class CorpPart {

    private GeneriqueBlindageEquipment blindage;
    Lesion l;
    public CorpPart(GeneriqueBlindageEquipment bl){
    this.blindage=bl;
    l=null;
    }

    public GeneriqueBlindageEquipment getBlindage() {
        return blindage;
    }
    
    
    
}
