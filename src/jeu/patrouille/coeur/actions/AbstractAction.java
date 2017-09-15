/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class AbstractAction implements Comparable<BaseAction>{
    
    public enum SubjectType {MYSELF,OTHER};


    ActionType type;
    SubjectType versus;
    
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
    

    public ActionType getType() {
        return type;
    }

    public SubjectType getVersus() {
        return versus;
    }

    public void setVersus(SubjectType versus) {
        this.versus = versus;
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
    public abstract BaseAction[] spreadAction()throws Exception;
    public abstract void calculeActionPointDesAction()throws Exception;
    public abstract Object clone();    
}
