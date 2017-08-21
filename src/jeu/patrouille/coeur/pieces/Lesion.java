/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces;

/**
 *
 * @author appleale
 */
public class Lesion {
    public enum Statu{CRITIQUE,GRAVE,GRAVE_TETE,LEGER_BLESSE
    ,MANQUE,NORMAL,GRAVE_BRASE}
    int location;
    int gravite;
    int blessure;
    Statu statu;
    public static int  SHELL_SHOCK=1000,TETE=0,THORAX=1,VENTRE=2,
            ABDOMEN=3,JAMBE_DROITE=4,JAMBE_GAUCHE=5,BRAS_DROITE=6,BRAS_GAUCHE=7,NOTVALUE=2000;
    public static int  CRITIQUE=0,GRAVE=1,LEGER=2,MANQUE=3;    
    public Lesion(int location,int gravite,int blessure,Statu statu){
        this.location=location;
        this.gravite=gravite;
        this.blessure=blessure;
        this.statu=statu;
    }

    public int getLocation() {
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
