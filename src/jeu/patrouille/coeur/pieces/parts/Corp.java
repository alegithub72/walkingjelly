/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.equipments.CoverJaquetteEquipment;
import jeu.patrouille.coeur.equipments.GeneriqueBlindageEquipment;
import jeu.patrouille.coeur.equipments.KevlerCasqueEquipment;

/**
 *
 * @author appleale
 */
public class Corp {
    public enum CorpParts{Tete,JambeDroite,BrasGauche,BrasDroite,Ventre,Abdomen,Thorax,JambeGauche}
    CorpPart corps[]=new CorpPart[CorpParts.values().length];
    public Corp(){
        corps[CorpParts.Abdomen.ordinal()]=new CorpPart();
        corps[CorpParts.BrasDroite.ordinal()]=new CorpPart();
        corps[CorpParts.BrasGauche.ordinal()]=new CorpPart();
        corps[CorpParts.JambeDroite.ordinal()]=new CorpPart();
        corps[CorpParts.JambeGauche.ordinal()]=new CorpPart();
        corps[CorpParts.Tete.ordinal()]=new CorpPart();
        corps[CorpParts.Thorax.ordinal()]=new CorpPart();
        corps[CorpParts.Ventre.ordinal()]=new CorpPart();
    }
    public Corp(GeneriqueBlindageEquipment jambeGauche,
            GeneriqueBlindageEquipment jambeDroite,
            GeneriqueBlindageEquipment brasGauche,
            GeneriqueBlindageEquipment brasDroite,
            GeneriqueBlindageEquipment tete,
            GeneriqueBlindageEquipment ventre,
            GeneriqueBlindageEquipment thorax
            ,GeneriqueBlindageEquipment abdomen){
    
    this.corps[CorpParts.BrasDroite.ordinal()]=new CorpPart(brasDroite);
    this.corps[CorpParts.BrasGauche.ordinal()]=new  CorpPart(brasGauche);
    this.corps[CorpParts.JambeDroite.ordinal()]=new CorpPart(jambeDroite);
    this.corps[CorpParts.JambeGauche.ordinal()]=new CorpPart(jambeGauche);
    this.corps[CorpParts.Tete.ordinal()]=new CorpPart(tete);
    this.corps[CorpParts.Ventre.ordinal()]=new CorpPart(ventre);
    this.corps[CorpParts.Thorax.ordinal()]=new CorpPart(thorax);
    this.corps[CorpParts.Abdomen.ordinal()]=new CorpPart(abdomen);
    
    }
    
    public CorpPart getCorpPart(CorpParts part){
        return corps[part.ordinal()];
    }
    public static Corp buildUSCorpBLindage(){
        Corp c=new Corp(null, null, null, null, 
                new KevlerCasqueEquipment(),
                new CoverJaquetteEquipment(), new CoverJaquetteEquipment(),null);
        return c;
    } 
    public static Corp buildNoCorpBLindage(){
        Corp c=new Corp();
        return c;
    }     
    public void reciveBlessure(Lesion l){
        //TODO aggiungere una lessione ad ogni parte del corpo
        
    }
    public boolean isBrasDroiteBlesse(){
        return this.corps[CorpParts.BrasDroite.ordinal()].isBlesse();
    }
    public boolean isBraceGaucheBlesse(){
        return this.corps[CorpParts.BrasGauche.ordinal()].isBlesse();
    
    }
}
