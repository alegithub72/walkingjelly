/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.menu.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.menu.MenuItem;
import jeu.patrouille.fx.menu.WalkItem;

/**
 *
 * @author appleale
 */
public class ActionItemMenuPressedEventHandler implements EventHandler<MouseEvent>{
    
    MenuItem item;
    MenuItem mainMenu[];
    FXCarte fxcarte;
    public ActionItemMenuPressedEventHandler(MenuItem item,MenuItem[] mainMenu,FXCarte fxcarte) {
    this.item=item;
    this.mainMenu=mainMenu;
    this.fxcarte=fxcarte;
    }
    
    
    
    
    
    @Override
    public void handle(MouseEvent event) {
        //System.out.println(event.toString());
        if(event.getButton()==MouseButton.PRIMARY){
            item.setFrame(1);
            for(int k=0;k<mainMenu.length;k++)
               if(mainMenu[k]!=null && mainMenu[k]!=item) mainMenu[k].setFrame(0);
           //buil the default comman action
           BaseAction act=item.buildMenuItemAction();
            
           if(act.getType()==BaseAction.MARCHE) {
                fxcarte.setActActualCommad(act);  
                ImageCursor cr=new ImageCursor(new Image("handCursor.png"));   
                fxcarte.setOnMouseClicked(new ActionConfirmMarcheEventHandler((WalkItem)item, fxcarte));               
                fxcarte.setCursor(Cursor.HAND);
                fxcarte.setOnMouseMoved(new ActionRangeDisplayHandler(fxcarte));
                fxcarte.closeMenuItems();
                fxcarte.getFxpl().sendMessageToPlayer("Scegli posizione");

           }else if(act.getType()==BaseAction.FEU){
                //ImageCursor cr=new ImageCursor(new Image("fireCursor.png"));  
              // fxcarte.setOnMouseClicked(new ActionConfirmMarcheEventHandler((Action)(item), fxcarte));
               //fxcarte.setCursor(cr);
               fxcarte.current=Cursor.CROSSHAIR;
               fxcarte.setCursor(Cursor.CROSSHAIR);
             
           
           }else fxcarte.setActActualCommad(null);  
              
            
        //TODO set the actual action command in base a questo determinare le successive mosse...  
        }
      
    event.consume();
    }
    
}
