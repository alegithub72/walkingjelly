/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.board;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;
import jeu.patrouille.coeur.terrains.PointCarte;

import static jeu.patrouille.fx.board.FXCarte.TILE_SIZE;
import jeu.patrouille.fx.menu.AbstractMenuItemButton;
import jeu.patrouille.fx.menu.BandageItem;
import jeu.patrouille.fx.menu.DisableMenuItems;
import jeu.patrouille.fx.menu.FeuItem;
import jeu.patrouille.fx.menu.LoadMagazineItem;
import jeu.patrouille.fx.menu.MenuItemButton;
import jeu.patrouille.fx.menu.OpFeuItem;
import jeu.patrouille.fx.menu.RunItem;
import jeu.patrouille.fx.menu.SuppressifFeuItem;
import jeu.patrouille.fx.menu.ViserFeuItem;
import jeu.patrouille.fx.menu.WalkItem;
import jeu.patrouille.fx.menu.eventhandler.ScrollEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatClickedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatPressedOnMenuItemsEventHandler;
import jeu.patrouille.fx.menu.eventhandler.SoldatRelasedOnMenuItemsEventHandler;
import jeu.patrouille.fx.pieces.FXSoldat;

/**
 *
 * @author appleale
 */
public class FXMenuItemsDossier {
    
    AbstractMenuItemButton[] items=new AbstractMenuItemButton[10];
    FXPlanche fxpl;
    FXCarte fxcarte;
    public FXMenuItemsDossier(FXPlanche fxpl) {
        this.fxpl=fxpl;
        this.fxcarte=fxpl.fxCarte;
    }
    private void removeMenuItems() {
        for (int k = 0; k < items.length; k++) {
            if (items[k] != null) {
                fxpl.rootScene.getChildren().remove(items[k]);
            }
        }
    }    
    synchronized public void openSoldatMenuItems(FXSoldat sfx) {
        try{
            fxcarte.setCursor(Cursor.HAND);
            removeMenuItems();
            fxcarte.deselectionneAllSoldats();  
            fxcarte.initFXHelperInstance(sfx);
            fxpl.imprimerFXPLInfo(sfx.getSoldat());
            double sx=sfx.getTranslateX();
            double sy=sfx.getTranslateY();
            int i=sfx.getSoldat().getI();
            int j=sfx.getSoldat().getJ();
            buildMenuItems(sx,sy,i,j);
            fxpl.buildFXSoldatEquipement(sfx.getSoldat());
          
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex.getCause());
        }

    }    
    
    protected  void buildMenuItems(double sx,double sy,int i,int j) throws ModeDeFeuException,TomberArmeException{
            
            FXSoldat sfx=fxcarte.fxIMHelper.getFXSoldatSelectionee();

            Point2D spritecoord2D=fxcarte.getMenuCoord(sx,sy,i,
                    j);

            double r= (2 * Math.PI) /10;
            sfx.selectioneFXSoldat();
            Soldat s=sfx.getSoldat();
            
            if (s.isPossibleAction() ) {
            
            buildMarcheMenuItem(sfx,spritecoord2D.getX(),spritecoord2D.getY());
            buildCoreurMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildFeuMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildOpFeuMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildViserFeuMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            if(s.isUtilizeAutomaticArme()) buildSuppressifMenuItem(sfx, spritecoord2D.getX(),spritecoord2D.getY(), r);
            buildBandageMenuItem(sfx, spritecoord2D.getX(), spritecoord2D.getY(), r);
            buildLoadMagazineMenuItem(sfx, spritecoord2D.getX(), spritecoord2D.getY(), r);           
          //  buildBurstFeuMenuItem(sfx, spritecoord2D.getX(), spritecoord2D.getY(), r);            
            //rootGroup.getChildren().add(l);
            items[2].addLink(items[4]);
            items[4].addLink(items[2]);
            fxcarte.visualizeBarSoldatAction();
            if(s.isBleeding()) fxpl.sendMessageToPlayer("Il moruire  est a bison doctoeur ou un bandée d'urgence!!",Color.YELLOW);
            else fxpl.sendMessageToPlayer("Choisir une action from ("+i+","+j+")"); 
        }else {
            removeMenuItems();
            buildDisableMenu(sfx);
            messageMenuItemDisable(s);

        }
    
    }    
    private void buildMarcheMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery){

                MenuItemButton m = new WalkItem(sfx,fxcarte);
                Soldat s=sfx.getSoldat();
                

                double x = AbstractMenuItemButton.MENU_W * Math.cos(0);
                double y = AbstractMenuItemButton.MENU_H * Math.sin(0);

                double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
                double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
                //m.setOnMouseClicked(new ActionMenuSelectionEventHandler(m, actionMenu, this));
                items[0] = m;
                m.setTranslateX(menuItemx);
                m.setTranslateY(menuItemy);               
                fxpl.rootScene.getChildren().add(m);   

                m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,  fxcarte));
                m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));
                m.enable();

    
    }
   private void buildCoreurMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad){
      
                MenuItemButton  m = new RunItem(sfx,fxcarte);
                double x = (100 * Math.cos(1 * grad));
                double y = (100 * Math.sin(1 * grad));
                double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
                double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
                m.setTranslateX(menuItemx);
                m.setTranslateY(menuItemy);   
                fxpl.rootScene.getChildren().add(m);   
                m.setOnMouseClicked(new SoldatClickedOnMenuItemsEventHandler(m,fxcarte));
                m.setOnMousePressed(new SoldatPressedOnMenuItemsEventHandler(m));
                m.setOnMouseReleased(new SoldatRelasedOnMenuItemsEventHandler(m));            
                items[1] = m;     
                m.enable();
                

    
    }
   
   private void buildBandageMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad){

                MenuItemButton  m = new BandageItem(sfx,fxcarte);
                double x = (100 * Math.cos(6 * grad));
                double y = (100 * Math.sin(6 * grad));
                double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
                double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
                m.setTranslateX(menuItemx);
                m.setTranslateY(menuItemy);   
                fxpl.rootScene.getChildren().add(m);           
                items[6] = m;     
                m.enable();

    
    }   
   private void buildLoadMagazineMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad){
     
                MenuItemButton  m = new LoadMagazineItem(sfx,fxcarte);
                double x = (100 * Math.cos(5 * grad));
                double y = (100 * Math.sin(5 * grad));
                double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
                double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
                m.setTranslateX(menuItemx);
                m.setTranslateY(menuItemy);   
                fxpl.rootScene.getChildren().add(m);          
                items[5] = m;     
                m.enable();

               
    
    }      
   
     
    private void buildFeuMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad)throws ModeDeFeuException,TomberArmeException {
        
            MenuItemButton m = new FeuItem(sfx,fxcarte);
            
            double x = (100 * Math.cos(2 * grad));
            double y = (100 * Math.sin(2 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            fxpl.rootScene.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            items[2] = m;    
            Soldat s=sfx.getSoldat();
            m.enable();
            
    }

    private void buildOpFeuMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad)throws ModeDeFeuException,TomberArmeException {
        
            MenuItemButton m = new OpFeuItem(sfx,fxcarte);
            
            double x = (100 * Math.cos(3 * grad));
            double y = (100 * Math.sin(3 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            fxpl.rootScene.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            items[3] = m;    
            Soldat s=sfx.getSoldat();
            m.enable();
            
    }
    private void buildSuppressifMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad)throws ModeDeFeuException,TomberArmeException {
        
            MenuItemButton m = new SuppressifFeuItem(sfx,fxcarte);
            
            double x = (100 * Math.cos(8 * grad));
            double y = (100 * Math.sin(8 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            fxpl.rootScene.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            items[8] = m;    
            Soldat s=sfx.getSoldat();
            m.enable();
            
    }    
    private void buildViserFeuMenuItem(FXSoldat sfx,double spritecenterx,double spritecentery,double grad)throws TomberArmeException{
 
            MenuItemButton m = new ViserFeuItem(sfx,fxcarte);
            double x = (100 * Math.cos(4 * grad));
            double y = (100 * Math.sin(4 * grad));
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
       
            //l = new Line(spritecenterx, spritecentery, menuItemx, menuItemy);
            fxpl.rootScene.getChildren().add(m);
            //rootGroup.getChildren().add(l);
            items[4] = m;
            m.enable();
}
 
    
protected void buildDisableMenu(FXSoldat s){

            MenuItemButton m = new DisableMenuItems(s,fxcarte);

            int relativeI = s.getSoldat().getI() - fxcarte.posI;
            int relativeJ = s.getSoldat().getJ() - fxcarte.posJ;
            System.out.println(s.getSoldat().getNom() + "->menu di i,j=" + relativeI + "," + relativeJ);
            double x = 100 * Math.cos(0);
            double y = 100 * Math.sin(0);
            // Point2D d2s = s.localToScene(s.getX(), s.getY());
            double sx = s.getTranslateX();
            double sy = s.getTranslateY();
            double spritecentery = sy + (TILE_SIZE / 2);
            double spritecenterx = sx + (TILE_SIZE / 2);
            if (relativeI <= 3) {
                spritecentery = spritecentery + (m.getH() * 2);
            }
            if (relativeJ <= 3) {
                spritecenterx = spritecenterx + (m.getW() * 2);
            }
            if (relativeI >= (FXCarte.AREA_SCROLL - 3)) {
                spritecentery = spritecentery - (m.getH() * 2);
            }
            if (relativeJ >= (FXCarte.AREA_SCROLL- 3)) {
                spritecenterx = spritecenterx - (m.getW() * 2);
            }
            double menuItemx = ((spritecenterx) + x) - (m.getW() / 2);
            double menuItemy = ((spritecentery) + y) - (m.getH() / 2);
           // m.setOnMouseClicked(new SoldatClickOnActionItemsEventHandler(m, actionMenu, fxpl));
            items[0] = m;
            //m.setEffect(new GaussianBlur());
            m.setTranslateX(menuItemx);
            m.setTranslateY(menuItemy);
            fxpl.rootScene.getChildren().add(m);
            
            //m.setOnMouseClicked(new SoldatClickOnActionItemsEventHandler(m, actionMenu, fxpl));
           

}       

    void messageMenuItemDisable(Soldat s){
        String message=s.toStringSimple();
        if(s.isEnVie() && s.getTempDisponible()<=0) {
            fxpl.sendMessageToPlayer("Le temp disponible est fini.");
            return;
        }
        if(s.isKIA()) {
            fxpl.sendMessageToPlayer(message+" est Mort.",Color.TOMATO);
            return;
        }
       
        if(s.isImmobilize()) message=message+" est immobilize ";
        if(s.isChoc()) message=message + " est chocked ";
        if(s.isIncoscient()) message=message+" est incoscient "; 
        fxpl.sendMessageToPlayer(message+".",Color.TOMATO);
        
    }
   
    
    protected void devisualizeMenuItems() {
        for (int k = 0; k < items.length; k++) {
            if (items[k] != null) {
                items[k].setVisible(false);
               
            }
        }

    }
    
    protected void visualizeMenuItems(){
        for (int i = 0; i < items.length; i++) {
            AbstractMenuItemButton item = items[i];
            if(item!=null) {
                item.setVisible(true);
                item.enable();
            }
        }
    }
    synchronized public void closeFXCarteMenuItems() {
        
       devisualizeMenuItems();
       fxcarte.setOnScroll(new ScrollEventHandler(fxcarte));

    }    
    
    synchronized public void openCurrentSoldatMenuItems(double sx,double sy) {
        try{
           
            PointCarte p=fxcarte.convertSceneCoord(sx, sy);
            if(fxcarte.fxIMHelper!=null){
            FXSoldat sfx=fxcarte.fxIMHelper.getFXSoldatSelectionee();
                System.out.println("-------->"+sfx);
                fxcarte.fxIMHelper.setCommanNotvalid(true);
                fxpl.imprimerFXPLInfo(sfx.getSoldat());
                fxcarte.initFXHelperInstance(sfx);
                closeFXCarteMenuItems();
                visualizeMenuItems();
                //buildMenuItems(sx,sy,p.getI(),p.getJ());
               
                
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }      
    
    public void sendMessageToPlayer(String text) {
        fxpl.sendMessageToPlayer(text);
    }    
    
}
