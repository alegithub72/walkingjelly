/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces;
import jeu.patrouille.coeur.pieces.Lesion.*;
/**
 *
 * @author appleale
 */
public class LesionEstimation {
    public int[][] estimation=new int[10][];

    
    public LesionEstimation(){
        for (int i = 0; i < 10; i++) {
            if(i==Lesion.TETE)estimation[i]=new int[]{-10,-4,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.THORAX) estimation[i]=new int[]{-4,-3,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.VENTRE) estimation[i]=new int[]{-3,-3,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.ABDOMEN)estimation[i]=new int[]{-3,-2,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.JAMBE_DROITE)estimation[i]=new int[]{-3,-2,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.JAMBE_GAUCHE)estimation[i]=new int[]{-3,-2,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.BRAS_DROITE)estimation[i]=new int[]{-3,-2,-1,Lesion.SHELL_SHOCK};
            if(i==Lesion.BRAS_GAUCHE)estimation[i]=new int[]{-3,-2,-1,Lesion.SHELL_SHOCK};
        }
    
    }
      public Lesion getLesion(int n,int m){
         int position=Lesion.NOTVALUE;
         int blessure=Lesion.NOTVALUE;
         int gravite=Lesion.NOTVALUE;
          Statu st=Statu.NORMAL;
        if(n==1 ) position=Lesion.TETE;
            else if(n>=2 && n<=3) position=Lesion.THORAX;
            else if(n>=4 && n<=5) position=Lesion.VENTRE;
            else if(n==6) position=Lesion.ABDOMEN;
            else if(n==7) position=Lesion.JAMBE_DROITE;
            else if(n==8) position=Lesion.JAMBE_GAUCHE;
            else if(n==9) position=Lesion.BRAS_DROITE;
            else if(n==10) position=Lesion.BRAS_GAUCHE;
         if(m<0)   {
             blessure= estimation[position][Lesion.SERIAUX];
             gravite=Lesion.SERIAUX;
             st=Statu.IMMOBILSER;
          
         }
            else if(m>=1 && m<=2) {
                blessure= estimation[position][Lesion.RISQUER];
                gravite=Lesion.RISQUER;
                st=Statu.IMPOSSIBLE_COURSE;

            }
            else if(m>=3 && m<=5){
                blessure= estimation[position][Lesion.LEGER];
                gravite=Lesion.LEGER;
            }
            else if(m>6) {
                blessure= estimation[position][Lesion.MANQUE];
                gravite=Lesion.MANQUE;
                
            }
         if(position==Lesion.TETE && gravite==Lesion.SERIAUX)
             st=Statu.MORT;
         if(position==Lesion.TETE && gravite==Lesion.RISQUER)
             st=Statu.IMMOBILSER;
         if((position==Lesion.BRAS_DROITE || position==Lesion.BRAS_GAUCHE)
                 && gravite==Lesion.RISQUER)st=Statu.UN_ACTION;
         if((position==Lesion.BRAS_DROITE|| position==Lesion.BRAS_GAUCHE)
                 && gravite==Lesion.SERIAUX) st=Statu.MAIN_ARME_SERIOUX;
        
       return new Lesion(position, gravite,blessure,st);
      }
      
                          
}
