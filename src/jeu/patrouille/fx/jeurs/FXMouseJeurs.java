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
    FXUSSoldat[] fxequipe = new FXUSSoldat[equipe.length];
    public FXMouseJeurs(int joueur,FXCarte fxcarte) {  
        super(joueur, fxcarte.getCarte());
        this.fxcarte=fxcarte;
        displacementEquipe();
        buildFXEquipe();

    }
    
    
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
            fxequipe[i].createFXSoldat();
            fxequipe[i].setFrame(0);            
            fxcarte.addSprite(fxequipe[i]);
           // fxequipeUS[i].setOnMouseClicked(new ActionMenuCloseEventHandler(rootGroup, actionMenu));
        }    
    }    
    




    public void removeMenuItemsOnFXEquipe() {
        for (FXUSSoldat sfx : fxequipe) {
            sfx.setOnMouseClicked(null);
        }
    }

    public void mountMenuItemOnFXEquipe() {
        for (FXUSSoldat sfx : fxequipe) {
            sfx.setOnMouseClicked(new SoldatOpenMenuItemsEventHandler(sfx, fxcarte));
        }
    }

    public FXUSSoldat[] rebuildFXEquipe() {
        buildFXEquipe();
        return fxequipe;
    }


    public void getCommand() {
    }

    public FXUSSoldat[] getFxEquipe() {
        return fxequipe;
    }
  
    
}
