/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments;

import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;

/**
 *
 * @author appleale
 */
public abstract class GeneriqueBlindageEquipment extends GeneriqueEquipment {
    
    int scoreBlindage;

    public GeneriqueBlindageEquipment(String nom, EquipmentType type, Model model) {
        super(nom, type, model);
    }




    public int getAr() {
        return scoreBlindage;
    }
    
}
