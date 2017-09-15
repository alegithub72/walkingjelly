/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.util;

import jeu.patrouille.coeur.actions.BaseAction;

/**
 *
 * @author appleale
 */
public class ArrayUtil {
    
    public static int copyArray(BaseAction[] l,BaseAction[] copyl,int from){
        int n=0;
        for (int i = 0; i < copyl.length; i++) {
            if(copyl[i]!=null) {
                l[from+i]=copyl[i];
                n++;
            }   
            
        }
        return from+n;
    }
    
    
}
