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
public class CoursAction extends BaseAction {
    public CoursAction( int i0, int j0, int i1, int j1, Piece protagoniste){
    super(BaseAction.COURS, i0, j0, i1, j1, protagoniste, null);
    }

    @Override
    public int valorActionPointDesActions() {
        int base = super.valorActionPointDesActions(); 
        double n= Carte.tileDistance(i0, j0, i1, j1)/5;
        int sum = (int)(base *n);
        return sum;
    }
    
    
}
