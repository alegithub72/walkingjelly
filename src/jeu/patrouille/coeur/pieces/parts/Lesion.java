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
    public static int  SHELL_SHOCK=1000,NOTVALUE=2000;
    public enum Degre{CRITIQUE,GRAVE,LEGER,MANQUE,NODEGRE};

    public Lesion(Corp.CorpParts location,Degre gravite,int blessure,Soldat.Statut statu,int turn){
        this.location=location;
        this.gravite=gravite;
        this.blessure=blessure;
        this.statu=statu;
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

    @Override
    public String toString() {
        return "Lesion{" + "location=" + location + ", gravite=" + gravite + ", blessure=" + blessure + ", statu=" + statu + '}';
    }
    
    
}
