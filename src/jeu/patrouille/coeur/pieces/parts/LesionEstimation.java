/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;
import jeu.patrouille.coeur.pieces.parts.Lesion.*;
import jeu.patrouille.coeur.pieces.parts.Corp.CorpParts;
import jeu.patrouille.coeur.pieces.Soldat.Statut;
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
          System.out.println(" position "+n+" , gravie"+m);
         CorpParts position=null;
         int blessure=0;
         boolean bandage=true;
         Degre gravite=Degre.NODEGRE;
          Statut st=Statut.NORMAL;
        if(n==1 ) position=CorpParts.Tete;
            else if(n>=2 && n<=3) position=CorpParts.Thorax;
            else if(n>=4 && n<=5) position=CorpParts.Ventre;
            else if(n==6) position=CorpParts.Abdomen;
            else if(n==7) position=CorpParts.JambeDroite;
            else if(n==8) position=CorpParts.JambeGauche;
            else if(n==9) position=CorpParts.BrasDroite;
            else if(n==10) position=CorpParts.BrasGauche;
         if(m<=0)   {
             blessure= estimation[position.ordinal()][Degre.CRITIQUE.ordinal()];
             gravite=Degre.CRITIQUE;
             st=Statut.CRITIQUE;
             bandage=false;
          
         }
            else if(m>=1 && m<=2) {
                blessure= estimation[position.ordinal()][Degre.GRAVE.ordinal()];
                gravite=Degre.GRAVE;
                st=Statut.GRAVE;
                bandage=false;

            }
            else if(m>=3 && m<=5){
                blessure= estimation[position.ordinal()][Degre.LEGER.ordinal()];
                gravite=Degre.LEGER;
                st=Statut.LEGER_BLESSE;
            }
            else if(m>=6) {
                blessure= 0;
                gravite=Degre.MANQUE;
                st=Statut.MANQUE;
                
            }
         if(position==CorpParts.Tete && gravite==Degre.GRAVE)
             st=Statut.GRAVE_TETE;
         if((position==CorpParts.BrasDroite )
                 && gravite==Degre.GRAVE)st=Statut.GRAVE_BRASE_DROITE;
        if(gravite==Degre.GRAVE && position==CorpParts.BrasGauche)
            st=Statut.GRAVE_BRASE_GAUCHE;
        
       Lesion l =new Lesion(position, gravite,blessure,st,turn);
       l.setBandage(bandage);
       return l;
      }
      
                          
}
