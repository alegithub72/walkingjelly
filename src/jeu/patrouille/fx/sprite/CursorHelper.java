/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.sprite;

import jeu.patrouille.util.ImageChargeur;

/**
 *
 * @author appleale
 */
public class CursorHelper extends Sprite{
    int type;
    public CursorHelper(int type){
    super(50, 50, 50, 50, null, null);
    this.type=type;
    }

    
}
