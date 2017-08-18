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
public class Magazine extends GeneriqueEquipment {
    
    GeneriqueArme.Model mod;
    int quantity;
    int capacity;
    public Magazine(GeneriqueArme.Model mod){
        super("Magazine", EquipmentType.MAGAZINE, mod);
        if(mod==GeneriqueArme.Model.AK74) {
            quantity=30;
            capacity=30;
        }
        else if(mod==GeneriqueArme.Model.BENELLI_M3){
            quantity=7;
            capacity=7;
        }
        
    }
    
    
    public int fire(int modFeu) throws LoadMagazineFiniException{
    int n=-1;
    if(quantity<=0) throw new LoadMagazineFiniException();
        switch (modFeu) {
            case GeneriqueArme.MODE_FEU_SS:
                n=1;
                quantity--;
                break;
        //TODO capite
            case GeneriqueArme.MODE_FEU_SA:
                n=1;
                quantity--;
                break;
            case GeneriqueArme.MODE_FEU_BU:
                n=5;
                if(quantity<10) n=10-quantity;
                else quantity=quantity-5;
                break;
            case GeneriqueArme.MODE_FEU_FA:
                n=10;
                if(quantity<10) n=10-quantity;
                else  quantity=quantity-10;
                break;
            default:
                break;
        }
    return n;
    }
    
    public int  getQuantity(){
     return quantity;
    }
    public int getCapacity(){
        return capacity;
    }
    public Model getMagazineModel(){
    return mod;
    }

    @Override
    public GeneriqueArme cloneEquipmentGenerique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
