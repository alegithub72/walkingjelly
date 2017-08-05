/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur;

import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.fx.board.FXPlanche;

/**
 *
 * @author appleale
 */
public class BasicRond {
    Piece[] equipeRondeUS;
    Piece[] equipeRondHost=new Piece[GeneriqueJoeurs.EQUIPE_SIZE_HOSTILE];
    
    public BasicRond(Piece us[],Piece[] host,Carte c,FXPlanche p){
        equipeRondHost=new Piece[host.length];
        equipeRondeUS=new Piece[us.length];
        for(int k=0;k<host.length;k++)
            equipeRondHost[k]=host[k].clonerPiece();
        for(int k=0;k<us.length;k++)
            equipeRondeUS[k]=us[k].clonerPiece();        
        
    }
    
    public void playTurn(){
        
    //TODO do any action less or  equal to n each    
        for(int n=1;n<=10;n++){

    }
    
    
    }
    BaseAction getLowestActionUS(int point){
    return null;
    
    }
    BaseAction getLowestActionHost(int point){
    return null;
    }
    
}
