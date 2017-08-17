/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.jeurs;

import jeu.patrouille.coeur.joeurs.AIJoeur;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.AISoldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.eventhandler.SoldatOpenMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXHostile;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class FXAIJoueur extends AIJoeur{

    FXCarte fxcarte;
    public FXAIJoueur(FXCarte fxcarte) {
        super(GeneriqueJoeurs.JOEUR_HOST,fxcarte.getCarte());
        this.fxcarte=fxcarte;
        displacementEquipe();   
        buildFXEquipe();
    }
    FXHostile[] fxequipe=new FXHostile[equipe.length];
    

    public void displacementEquipe(){
        for(int k=0;k<equipe.length;k++){
            carte.desplacementSoldat(equipe[k], 2, k);

        }
            
    }    
    

    private  void buildFXEquipe(){
        for(int i=0;i<equipe.length;i++) {
           fxequipe[i] = new FXHostile((AISoldat)equipe[i],i,fxcarte);
           fxequipe[i].setDeafultFrame(3);
           fxequipe[i].createFXSoldat();  
           fxequipe[i].defaultFrame();
          


           fxequipe[i].setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(fxequipe[i],fxcarte));
           fxcarte.addSprite(fxequipe[i]);
          
        }
      
    }    
    
   public  FXSoldat[] rebuildFXEquipe(){
        buildFXEquipe();
        return fxequipe;
   }    

    public FXHostile[] getFxEquipe() {
        return fxequipe;
    }
    public void removeMenuItemsOnFXEquipe() {
        for (FXSoldat sfx : fxequipe) {
            sfx.setOnMouseClicked(null);
        }
    }

    public void mountMenuItemOnFXEquipe() {
        for (FXSoldat sfx : fxequipe) {
            sfx.setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(sfx, fxcarte));
        }
    }    
    
}
