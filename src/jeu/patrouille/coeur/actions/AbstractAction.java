/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class AbstractAction implements Comparable<BaseAction>{
    

public static final int BANDAGE = 14;
public static final int MARCHE=0,COURS=1,CRAWL=2,COUCHER=3
            ,LEVER=4,MONTER=6,SAUT=7,
            PRENDRE=8,LACHER_OBJ=9,JETER_GRENATE=10,
            OP_FEU=11,
            CHANGE_DIR=12,TRIGGER=13,FEU=15,Reload=16,GEOUX=17,NO_ACTION=18;
public static final int[] ACTIONPOINTVALOR = {1, 3, 2, 3, 0, 0, 0, 0, 0, 0, 4, 5, 0, 0, 0, 6, 2, 0, 0};
    int type;
    int j0;
    int i0;
    int i1;
    int j1;
    int tempActivite;

    public int getTempActivite() {
        return tempActivite;
    }

    public void setTempActivite(int tempActivite) {
        this.tempActivite = tempActivite;
    }
    
    
    public AbstractAction() {
    }

    public int getType() {
        return type;
    }

    public int getJ0() {
        return j0;
    }

    public void setJ0(int j0) {
        this.j0 = j0;
    }

    public int getI0() {
        return i0;
    }

    public void setI0(int i0) {
        this.i0 = i0;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getJ1() {
        return j1;
    }

    public void setJ1(int j1) {
        this.j1 = j1;
    }
    public abstract List<BaseAction> spreadAction();
    
    public abstract Object clone();    
}
