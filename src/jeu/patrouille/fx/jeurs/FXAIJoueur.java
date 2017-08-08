/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.jeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.joeurs.AIJoeur;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXHostile;

/**
 *
 * @author appleale
 */
public class FXAIJoueur extends AIJoeur{

    FXPlanche fxpl;
    public FXAIJoueur(Carte c , FXPlanche fxpl) {
        super(GeneriqueJoeurs.JOEUR_HOST, c);
        this.fxpl=fxpl;
        buildEquipe();
    }
    FXHostile[] fxequipe=new FXHostile[equipe.length];
    
    private  void buildEquipe(){
        

        
      
        for(int i=0;i<equipe.length;i++) {
           fxequipe[i] = new FXHostile((AISoldat)equipe[i],i);
           fxequipe[i].setDeafultFrme(0);
           fxequipe[i].defaultFrame();
           fxequipe[i].setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(fxequipe[i],fxpl));
          
        }
      
    }    

    public FXHostile[] getFxEquipe() {
        return fxequipe;
    }
    
    
}
