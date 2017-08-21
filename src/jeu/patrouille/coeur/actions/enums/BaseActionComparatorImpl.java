/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions.enums;

import java.util.Comparator;
import jeu.patrouille.coeur.actions.BaseAction;

/**
 *
 * @author Alessio Sardaro
 */
public class BaseActionComparatorImpl implements Comparator<BaseAction> {

        public BaseActionComparatorImpl() {
        }

        @Override
        public int compare(BaseAction b1, BaseAction b2) {
            
            if  (b1.getOrdreInitiative()<b2.getOrdreInitiative()) return -1;
            if(b1.getOrdreInitiative()>b2.getOrdreInitiative()) return 1;
            if(b1.getOrdreInitiative()==b2.getOrdreInitiative()) return 0;
            return 0;
        } 
}
