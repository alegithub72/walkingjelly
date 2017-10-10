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
    static int[][] estimation=new int[CorpParts.values().length][];
    static LesionEstimation est;
    
     LesionEstimation() {
        estimation[CorpParts.Tete.ordinal()] = new int[]{  -1,-4,-10, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Thorax.ordinal()] = new int[]{ -1,-3, -4, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Ventre.ordinal()] = new int[]{  -1,-3,-3, Lesion.SHELL_SHOCK};
        estimation[CorpParts.Abdomen.ordinal()] = new int[]{  -1,-3,-2, Lesion.SHELL_SHOCK};
        estimation[CorpParts.JambeDroite.ordinal()] = new int[]{  -1,-2,-3, Lesion.SHELL_SHOCK};
        estimation[CorpParts.JambeGauche.ordinal()] = new int[]{  -1,-2,-3, Lesion.SHELL_SHOCK};
        estimation[CorpParts.BrasDroite.ordinal()] = new int[]{  -1,-2,-3, Lesion.SHELL_SHOCK};
        estimation[CorpParts.BrasGauche.ordinal()] = new int[]{  -1,-2,-3, Lesion.SHELL_SHOCK};
        

    }
      public static Lesion getLesion(int n,int m,int turn){
        if(est==null) est=new LesionEstimation();
        System.out.println(" dice location "+n+" , dice degre "+m);
        CorpParts position=null;
        int blessure=0;
        boolean bandage=true;
        Degre gravite=Degre.NODEGRE;
        Statut st=Statut.NORMAL;
        position=getLesionLocation(n);   
        System.out.println("gravite m="+m);
        if(m<=0)   {
             blessure= estimation[position.ordinal()][Degre.CRITIQUE.ordinal()-2];
             gravite=Degre.CRITIQUE;
             st=Statut.CRITIQUE;
             bandage=false;
          
        }else if(m>=1 && m<=2) {
                blessure= estimation[position.ordinal()][Degre.GRAVE.ordinal()-2];
                gravite=Degre.GRAVE;
                st=Statut.GRAVE;
                bandage=false;

        }else if(m>=3 && m<=5){
                blessure= estimation[position.ordinal()][Degre.LEGER.ordinal()-2];
                gravite=Degre.LEGER;
                st=Statut.LEGER_BLESSE;
        }else if(m>=6) {
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
      public static CorpParts getLesionLocation(int dice ){
          System.out.println("dice postion="+dice);
          CorpParts position=null;
        if(dice==1 ) position=CorpParts.Tete;
            else if(dice>=2 && dice<=3) position=CorpParts.Thorax;
            else if(dice>=4 && dice<=5) position=CorpParts.Ventre;
            else if(dice==6) position=CorpParts.Abdomen;
            else if(dice==7) position=CorpParts.JambeDroite;
            else if(dice==8) position=CorpParts.JambeGauche;
            else if(dice==9) position=CorpParts.BrasDroite;
            else if(dice==10) position=CorpParts.BrasGauche;
        return position;
      }
                          
}
