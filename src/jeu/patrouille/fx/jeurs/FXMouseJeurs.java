/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.jeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.joeurs.KeyboardJoeur;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXUSSoldat;

/**
 *
 * @author appleale
 */
public class FXMouseJeurs extends KeyboardJoeur{

    FXPlanche fxpl;
    public FXMouseJeurs(int joueur,Carte c,FXPlanche fxpl) {
        super( joueur , c );       
        this.fxpl=fxpl;
        buildEquipe();

    }
    FXUSSoldat fxequipe[]=new FXUSSoldat[equipe.length];
    
    private  void buildEquipe() {

        for(int i=0;i<equipe.length;i++){
           fxequipe[i]=new FXUSSoldat((Soldat) equipe[i],i);
            fxequipe[i].setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(fxequipe[i],fxpl));
           // fxequipeUS[i].setOnMouseClicked(new ActionMenuCloseEventHandler(rootGroup, actionMenu));
        }    
    }    
    
   

    @Override
    public void getCommand() {
  
        
    }

    public FXUSSoldat[] getFxEquipe() {
        return fxequipe;
    }

  
    
}
