/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.armes;

import jeu.patrouille.coeur.armes.exceptions.LoadMagazineFiniException;

/**
 *
 * @author appleale
 */
public class Magazine {
    
    ArmeGenerique.Model mod;
    int quantity;
    int capacity;
    public Magazine(ArmeGenerique.Model mod){
        this.mod=mod;
        if(mod==ArmeGenerique.Model.AK74) {
            quantity=30;
            capacity=30;
        }
        else if(mod==ArmeGenerique.Model.BENELLI_M3){
            quantity=7;
            capacity=7;
        }
        
    }
    
    
    public int fire(int modFeu) throws LoadMagazineFiniException{
    int n=-1;
    if(quantity<=0) throw new LoadMagazineFiniException();
    if(ArmeGenerique.MODE_FEU_SS==modFeu){
        n=1;
        quantity--;
    }
    else if(ArmeGenerique.MODE_FEU_SA==modFeu){
        n=1;
        quantity--;
        
    }//TODO capite
    else if(ArmeGenerique.MODE_FEU_BU==modFeu){
        n=5;
        if(quantity<10) n=10-quantity;
        else quantity=quantity-5;
        
    }
    else if(ArmeGenerique.MODE_FEU_FA==modFeu){
        n=10;
       if(quantity<10) n=10-quantity;
       else  quantity=quantity-10;
    }
    return n;
    }
    
    public int  getQuantity(){
     return quantity;
    }
    public int getCapacity(){
        return capacity;
    }
}
