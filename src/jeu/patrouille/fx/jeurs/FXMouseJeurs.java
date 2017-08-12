/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.jeurs;

import jeu.patrouille.coeur.joeurs.KeyboardJoeur;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
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
        displacementEquipe();
        buildFXEquipe();

    }
    FXUSSoldat fxequipe[]=new FXUSSoldat[equipe.length];
    
    
    public void displacementEquipe(){
        for(int k=0;k<equipe.length;k++){
            carte.desplacementSoldat(equipe[k], 30, 20+k);

        }
            
    }
    private  void buildFXEquipe() {

        
        for(int i=0;i<equipe.length;i++){
           fxequipe[i]=new FXUSSoldat((Soldat) equipe[i],i,fxcarte);
            fxequipe[i].setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(fxequipe[i],fxcarte));
            fxequipe[i].setVisible(false);
            fxcarte.addSprite(fxequipe[i]);
           // fxequipeUS[i].setOnMouseClicked(new ActionMenuCloseEventHandler(rootGroup, actionMenu));
        }    
    }    
    
   public  FXUSSoldat[] rebuildFXEquipe(){
        buildFXEquipe();
        return fxequipe;
   }

    @Override
    public void getCommand() {
  
        
    }

    public FXUSSoldat[] getFxEquipe() {
        return fxequipe;
    }

  
    
}
