/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.FeuAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class FeuItem extends MenuItemButton{
    GeneriqueArme.FeuMode mode;

    int n;
    public FeuItem(FXSoldat fxs){
        super(ActionType.FEU,fxs);
        initButtonState();
    }
    
    
    public FeuItem(ActionType type,FXSoldat sfx) {
        super(type, sfx);
        initButtonState();
    }
    public FeuItem(){
        super(ActionType.FEU, null);
    }   
    

    @Override
    public BaseAction buildMenuItemAction() {
        BaseAction act=new FeuAction(fxs.getSoldat(), null,mode);
        return act;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        @Override
    public void changeStates() {
            try{
                n++;
                GeneriqueArme.FeuMode modes[]=null;
                Soldat s=fxs.getSoldat();
                if(s.getArmeUtilise()!=null && 
                        s.getArmeUtilise().getEquipmentType()==
                        GeneriqueEquipment.EquipmentType.FIRE_WEAPON ){
                    GeneriqueArme arme=(GeneriqueArme)s.getArmeUtilise();
                    if(arme==null) return;
                    modes= arme.armeFeuModeDisponible();
                }
                if(n>modes.length) n=1;
                
                if(n==1  ) mode=modes[0];
                else if(n==2 && modes.length>1) mode=modes[1];
                else if(n==3 && modes.length>2) mode=modes[2];

                s.getArmeUtilise().changeModeFeu(mode);
                buildButtonState();

            }catch(ModeDeFeuException m){
                throw new RuntimeException(m);
            }

    }
    void initButtonState(){
      if(fxs!=null && fxs.getSoldat().getArmeUtilise()!=null)  
          this.mode=fxs.getSoldat().getArmeUtilise().getArmeFeuModel();        
     buildButtonState();
    
    }
    
    void buildButtonState(){
    if(mode==GeneriqueArme.FeuMode.SA || 
       mode==GeneriqueArme.FeuMode.SC){
          buildFrameImages(MenuImageChargeur.getImageMenu(actionType));
          n=1;
    }
      
    else if(mode==GeneriqueArme.FeuMode.RA){
        buildFrameImages(MenuImageChargeur.getImageMenuSubType(0));
        n=2;
    }
    else if(mode==GeneriqueArme.FeuMode.PA){
        buildFrameImages(MenuImageChargeur.getImageMenuSubType(1));
        n=3;
    }     
    
    }

    @Override
    public void updateState() {
        initButtonState();
    }
    
    
}
