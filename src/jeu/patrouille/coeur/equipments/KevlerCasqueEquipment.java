/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments;

import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.pieces.Lesion;

/**
 *
 * @author appleale
 */
public class KevlerCasqueEquipment extends GeneriqueBlindageEquipment{
    public KevlerCasqueEquipment() {
        super("Casque Blinde", EquipmentType.CASQUE_BLINDE, null);
        position=Lesion.TETE;
        scoreBlindage=5;
    }

    
    
    
}
