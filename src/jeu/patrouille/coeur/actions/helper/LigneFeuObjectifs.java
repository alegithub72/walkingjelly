/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions.helper;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.util.Arrays;
import jeu.patrouille.coeur.pieces.GeneriquePiece;

/**
 *
 * @author appleale
 */
public class LigneFeuObjectifs {
    Target[] targets;
    int k;
    public LigneFeuObjectifs() {
        targets=new Target[10];
        k=0;
    }   
    
    public void addTarget(Target t ){
        if(duplicatedTarget(t)) return;
        targets[k]=t;
        k++;
        if(k>=10){
            Target[] targetsTmp=new Target[20];
            for (int i = 0; i < targetsTmp.length; i++) {
                targetsTmp[i] = targets[i];
            }
            targets=targetsTmp;
        }
    }
    boolean duplicatedTarget(Target t){
   
        for (int i = 0; i < targets.length; i++) 
            if(targets[i]!=null && targets[i].getS()==t.getS()) return true;
            
        return false;
    
    }
    public int size(){
        return k;
    }
    
    public GeneriquePiece getGeneriqueTarget(int i){
       if(targets[i]!=null) return targets[i].getS();
       return null;
    
    }
   public Target getTarget(int i){
    if(targets[i]!=null) return targets[i] ;
    return null;
   }
   public Target[] sortedDistanceTarget(){
       Target[] tmp=new Target[k];
       for (int i = 0; i < tmp.length; i++) {
          tmp[i]=targets[i];
           
       }
       Arrays.sort(tmp);
       return tmp;
   } 
}
