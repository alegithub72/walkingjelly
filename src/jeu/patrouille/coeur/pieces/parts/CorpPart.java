/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.equipments.GeneriqueBlindageEquipment;

/**
 *
 * @author appleale
 */
public class CorpPart {

    private GeneriqueBlindageEquipment blindage;
    Lesion.Degre d;
    public CorpPart(){
        blindage=null;
        d=null;
    }
    public CorpPart(GeneriqueBlindageEquipment bl){
    this.blindage=bl;
    d=null;
    }

    public GeneriqueBlindageEquipment getBlindage() {
        return blindage;
    }

    @Override
    public String toString() {
        return "CorpPart{" + "blindage=" + blindage + ", degreee=" + d + '}';
    }
    public boolean isBlesse(){
        return d==Lesion.Degre.GRAVE || d==Lesion.Degre.CRITIQUE;
    }
    public void setDegre(Lesion.Degre d){
        this.d=d;
    }
    public int degree(){
        if(d!=null )return this.d.ordinal();
        else return -1;
    }
    
}
