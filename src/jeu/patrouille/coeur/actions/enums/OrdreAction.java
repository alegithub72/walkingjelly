/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions.enums;

/**
 *
 * @author appleale
 */
public enum OrdreAction {MARCHE(1),COURS(1),RAMPER(2),COUCHER(0),LEVER(3),
MONTER(2),SAUT(3),PRENDRE_OBJ(1),LACHER_OBJ(0),JETER_GRENATE(3),OCCASION_DE_FEU(10),
CHANGE_ORIENTATION(0),MANETTE(1),FEU(-1)
,ARME_RECHARGE(3),GEOUX(0),PA_ACTION(0),FEU_VISER(0),BANDAGE(5);
    

private int TN;
    OrdreAction(int tn){
    this.TN=tn;
    }
    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public int TN() {
        return TN;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void TN(int value) {
        this.TN = value;
    }

    
}
