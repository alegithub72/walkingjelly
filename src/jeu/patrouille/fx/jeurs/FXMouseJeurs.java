/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.jeurs;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.joeurs.KeyboardJoeur;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.board.FXPlanche;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXUSSoldat;

/**
 *
 * @author appleale
 */
public class FXMouseJeurs extends KeyboardJoeur{

    FXCarte fxcarte;
    public FXMouseJeurs(int joueur,FXCarte fxcarte) {
        super( joueur , fxcarte.getCarte() );       
        this.fxcarte=fxcarte;
        buildEquipe();

    }
    FXUSSoldat fxequipe[]=new FXUSSoldat[equipe.length];
    
    private  void buildEquipe() {

        for(int i=0;i<equipe.length;i++){
           fxequipe[i]=new FXUSSoldat((Soldat) equipe[i],i,fxcarte);
            fxequipe[i].setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(fxequipe[i],fxcarte));
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
