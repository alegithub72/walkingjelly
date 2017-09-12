/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.menu;

import javafx.scene.control.Label;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.ViserFeuAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class ViserFeuItem extends FeuItem{

    
    public ViserFeuItem(FXSoldat sfx,FXCarte fxcarte) {
        super(ActionType.FEU_VISER,  sfx,fxcarte);

        
    }


    @Override
    void buildButtonState(){
    if(mode==GeneriqueArme.FeuMode.SA || 
       mode==GeneriqueArme.FeuMode.SC){
        buildFrameImages(MenuImageChargeur.getImageMenu(actionType));
        n=1;
    }
    else if(mode==GeneriqueArme.FeuMode.RA) {
        buildFrameImages(MenuImageChargeur.getImageMenuSubType(2));
        n=2;
    }
    else if(mode==GeneriqueArme.FeuMode.PA){
        buildFrameImages(MenuImageChargeur.getImageMenuSubType(3));
        n=3;
    }     
    }
    @Override
    public BaseAction buildMenuItemAction() {
        ViserFeuAction act=new ViserFeuAction(null, null, mode);
        return act;
    }
    
    
}
