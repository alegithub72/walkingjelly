/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author appleale
 */
public  class BaseAction extends AbstractAction {
    public static final BaseAction QUIT=new BaseAction(-1, -1, -1, -1,-1, null, null);
    
    Piece protagoniste;
    Piece[] antagonistes;


    public Piece getProtagoniste() {
        return protagoniste;
    }
    
    public BaseAction(int type, int i0, int j0, int i1, int j1, Piece protagoniste, Piece[] antagonistes) {
        this.type = type;
        this.j0 = j0;
        this.i0 = i0;
        this.j1 = j1;
        this.i1 = i1;
        this.protagoniste = protagoniste;
        this.antagonistes = antagonistes;
    }


    
    public int valorActionPointDesActions(){
        return BaseAction.ACTIONPOINTVALOR[type];
    
    }
    
    @Override
    public String toString() {
        char c=' ';
        if(type==BaseAction.FEU) c='f';
        else if(type==BaseAction.MARCHE) c='m';
        return ""+c+" "+i0+","+j0+";"+i1+","+j1;
    }


     
    
}
