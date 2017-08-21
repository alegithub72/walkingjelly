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
public class CoverJaquetteEquipment extends GeneriqueBlindageEquipment{

    public CoverJaquetteEquipment() {
        super("Jaquette Blinde", EquipmentType.JAQUETTE_BLINDE, null);
       position=Lesion.THORAX;
       scoreBlindage=5;//TODO vedere i valori
    }


    
}