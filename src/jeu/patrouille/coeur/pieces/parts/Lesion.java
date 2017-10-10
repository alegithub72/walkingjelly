/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class Lesion {

    Corp.CorpParts location;
    Degre gravite;
    int blessure;
    Soldat.Statut statu;
    int turn;

    boolean bandage;
    public static int  SHELL_SHOCK=1000,NOTVALUE=2000;
    public enum Degre{NODEGRE,MANQUE,LEGER,GRAVE,CRITIQUE};

    public Lesion(Corp.CorpParts location,Degre gravite,int blessure,Soldat.Statut statu,int turn){
        this.location=location;
        this.gravite=gravite;
        this.blessure=blessure;
        this.statu=statu;
        bandage=false;
        this.turn=turn;
        
    }

    @Override
    public Lesion clone() {
        return new Lesion(location, gravite, blessure, statu, turn);
    }
    
    public Corp.CorpParts getLocation() {
        return location;
    }

    public Degre getGravite() {
        return gravite;
    }

    public int getBlessure() {
        return blessure;
    }

    public Soldat.Statut getStatu() {
        return statu;
    }

    public void setStatu(Soldat.Statut statu) {
        this.statu = statu;
    }
 public boolean isNecessaireDropItem(){
  return statu==Soldat.Statut.GRAVE_BRASE_DROITE ||
          statu==Soldat.Statut.GRAVE_BRASE_GAUCHE;
          
 }
    @Override
    public String toString() {
        return "Lesion{" + "location=" + location + ", gravite=" + gravite + ", blessure=" + blessure + ", statu=" + statu + '}';
    }

    public boolean isBandage() {
        return bandage;
    }

    public void setBandage(boolean bandage) {
        this.bandage = bandage;
    }

    public int getTurn() {
        return turn;
    }


    
    
}
