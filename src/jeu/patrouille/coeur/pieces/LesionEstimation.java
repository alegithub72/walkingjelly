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
             blessure= estimation[position][Lesion.CRITIQUE];
             gravite=Lesion.CRITIQUE;
             st=Statu.CRITIQUE;
          
         }
            else if(m>=1 && m<=2) {
                blessure= estimation[position][Lesion.GRAVE];
                gravite=Lesion.GRAVE;
                st=Statu.GRAVE;

            }
            else if(m>=3 && m<=5){
                blessure= estimation[position][Lesion.LEGER];
                gravite=Lesion.LEGER;
                st=Statu.LEGER_BLESSE;
            }
            else if(m>6) {
                blessure= estimation[position][Lesion.MANQUE];
                gravite=Lesion.MANQUE;
                st=Statu.MANQUE;
                
            }
         if(position==Lesion.TETE && gravite==Lesion.GRAVE)
             st=Statu.GRAVE_TETE;
         if((position==Lesion.BRAS_DROITE || position==Lesion.BRAS_GAUCHE)
                 && gravite==Lesion.GRAVE)st=Statu.GRAVE_BRASE;

        
       return new Lesion(position, gravite,blessure,st);
      }
      
                          
}
