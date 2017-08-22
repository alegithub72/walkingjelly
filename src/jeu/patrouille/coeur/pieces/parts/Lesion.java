/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.pieces.parts.Corp;

/**
 *
 * @author appleale
 */
public class Lesion {
    public enum Statu{CRITIQUE,GRAVE,GRAVE_TETE,LEGER_BLESSE
    ,MANQUE,NORMAL,GRAVE_BRASE}
    Corp.CorpParts location;
    int gravite;
    int blessure;
    Statu statu;
    int turn;
    public static int  SHELL_SHOCK=1000,NOTVALUE=2000;
    public static int  CRITIQUE=0,GRAVE=1,LEGER=2,MANQUE=3;    
    public Lesion(Corp.CorpParts location,int gravite,int blessure,Statu statu,int turn){
        this.location=location;
        this.gravite=gravite;
        this.blessure=blessure;
        this.statu=statu;
    }

    public Corp.CorpParts getLocation() {
        return location;
    }

    public int getGravite() {
        return gravite;
    }

    public int getBlessure() {
        return blessure;
    }

    public Statu getStatu() {
        return statu;
    }

    public void setStatu(Statu statu) {
        this.statu = statu;
    }

    @Override
    public String toString() {
        return "Lesion{" + "location=" + location + ", gravite=" + gravite + ", blessure=" + blessure + ", statu=" + statu + '}';
    }
    
    
}
