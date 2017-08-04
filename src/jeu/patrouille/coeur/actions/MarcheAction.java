/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author appleale
 */
public class MarcheAction extends BaseAction{
    public MarcheAction( int i0, int j0, int i1, int j1, Piece protagoniste){
    super(BaseAction.MARCHE, i0 ,j0, i1, j1, protagoniste, null);
    
    }

    @Override   
    public int valorActionPointDesActions() {
        int apbase= super.valorActionPointDesActions();
        int letgthp=Carte.tileDistance(i0, j0, i1, j1);
        System.out.println("distanza calcolata---------_>"+letgthp+"punti base"+apbase);
       int calcp=(apbase* letgthp);
       return calcp;
    }


    

    
        public static void main(String[] args) {}
}
