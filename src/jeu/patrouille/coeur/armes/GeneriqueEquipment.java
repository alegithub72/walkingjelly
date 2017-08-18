/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.armes;

/**
 *
 * @author appleale
 */
public abstract class GeneriqueEquipment {
    public static enum EquipmentType{FIRE_WEAPON,GRENADE,MAGAZINE};
    public enum Model {BENELLI_M3, AK74, M16};
    public static final int NOTVALUE = -111;
     String nom;
     EquipmentType equipmentType;
     Model model;

    public GeneriqueEquipment(String nom,EquipmentType type,Model model) {
        this.nom=nom;
        this.equipmentType=type;
        this.model=model;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public String getNom() {
        return nom;
    }
    abstract public GeneriqueArme cloneEquipmentGenerique();    
    
    
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    
    
}
