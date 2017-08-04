/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.pieces;

import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class Vehicule extends Piece  {
   
    public Vehicule() {
        super(ActeurType.JEEP);
    }

    @Override
    public Piece[] losView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   public Sprite getSprite(){
       return null;
   }

    @Override
    public Piece clonerPiece() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
