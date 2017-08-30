/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.equipments.armes;

import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
/**
 *
 * @author appleale
 */
public class Magazine extends GeneriqueEquipment {
    
    GeneriqueArme.Model mod;
    int quantity;
    int capacity;
    public enum SubType{BUCK,SLUG,STANDARD};
    SubType sub;
    
    public Magazine(GeneriqueArme.Model mod,SubType sub){
        super("Magazine", EquipmentType.MAGAZINE, mod);
        this.sub=sub;
        if(null!=mod) switch (mod) {
            case AK74:
                quantity=30;
                capacity=30;
                break;
            case BENELLI_M3:
                quantity=7;
                capacity=7;
                break;
            case M16:
                quantity=30;
                capacity=30;
                break;
            default:
                break;
        }        
        
        
    }
    
    
    public int depot(int n) throws LoadMagazineFiniException{

    if(quantity<=0) throw new LoadMagazineFiniException();
    if(quantity<n) {
                    n=quantity;
                    quantity=0;
                }
                else  quantity=quantity-n;
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





    
}
