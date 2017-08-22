/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;
import jeu.patrouille.coeur.pieces.parts.Lesion.*;
import jeu.patrouille.coeur.pieces.parts.Corp.CorpParts;
/**
 *
 * @author appleale
 */
public class LesionEstimation {
    public int[][] estimation=new int[CorpParts.values().length][];

    
    public LesionEstimation() {
        estimation[CorpParts.Tete.ordinal()] = new int[]{-10, -4, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Thorax.ordinal()] = new int[]{-4, -3, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Ventre.ordinal()] = new int[]{-3, -3, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Abdomen.ordinal()] = new int[]{-3, -2, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.JambeDroite.ordinal()] = new int[]{-3, -2, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.JambeGauche.ordinal()] = new int[]{-3, -2, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.BrasDroite.ordinal()] = new int[]{-3, -2, -1, Lesion.SHELL_SHOCK};
        estimation[CorpParts.BrasGauche.ordinal()] = new int[]{-3, -2, -1, Lesion.SHELL_SHOCK};

    }
      public Lesion getLesion(int n,int m,int turn){
         CorpParts position=null;
         int blessure=Lesion.NOTVALUE;
         int gravite=Lesion.NOTVALUE;
          Statu st=Statu.NORMAL;
        if(n==1 ) position=CorpParts.Tete;
            else if(n>=2 && n<=3) position=CorpParts.Thorax;
            else if(n>=4 && n<=5) position=CorpParts.Ventre;
            else if(n==6) position=CorpParts.Abdomen;
            else if(n==7) position=CorpParts.JambeDroite;
            else if(n==8) position=CorpParts.JambeGauche;
            else if(n==9) position=CorpParts.BrasDroite;
            else if(n==10) position=CorpParts.BrasGauche;
         if(m<0)   {
             blessure= estimation[position.ordinal()][Lesion.CRITIQUE];
             gravite=Lesion.CRITIQUE;
             st=Statu.CRITIQUE;
          
         }
            else if(m>=1 && m<=2) {
                blessure= estimation[position.ordinal()][Lesion.GRAVE];
                gravite=Lesion.GRAVE;
                st=Statu.GRAVE;

            }
            else if(m>=3 && m<=5){
                blessure= estimation[position.ordinal()][Lesion.LEGER];
                gravite=Lesion.LEGER;
                st=Statu.LEGER_BLESSE;
            }
            else if(m>6) {
                blessure= estimation[position.ordinal()][Lesion.MANQUE];
                gravite=Lesion.MANQUE;
                st=Statu.MANQUE;
                
            }
         if(position==CorpParts.Tete && gravite==Lesion.GRAVE)
             st=Statu.GRAVE_TETE;
         if((position==CorpParts.BrasDroite || position==CorpParts.BrasGauche)
                 && gravite==Lesion.GRAVE)st=Statu.GRAVE_BRASE;

        
       return new Lesion(position, gravite,blessure,st,turn);
      }
      
                          
}
