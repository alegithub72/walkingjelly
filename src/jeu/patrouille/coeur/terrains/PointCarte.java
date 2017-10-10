/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.grafic.TerrainTextRapresentation;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public  class PointCarte implements TerrainTextRapresentation{
    public static final int TERRAIN=0;
    public static final int HAIE=1;
    public static final int ARBRE=2;
    public static final int MURBAS=3;
    public static final int GROSMUR=4;
    public static final int RUINES=5;
    public static final int FENETRE=6;
    public static final int PORTE=7;
    public static final int INTERIOR=8;

    protected int i;
    protected int j;
    protected int type;
    protected double v;
    int k=0;
    GeneriquePiece[] pieces;
    
    public void putPiece(GeneriquePiece piece) {
        if(k>=10) {
             GeneriquePiece[]  piecesTmp=new GeneriquePiece[20];
            for (int l = 0; l < piecesTmp.length; l++) {
                GeneriquePiece piece1 = pieces[l];
                piecesTmp[l]=piece1;
            }
            pieces=piecesTmp;
        }
       
        pieces[k]=piece;
        k++;
        
    }
    public GeneriquePiece[] getPieces(){
        return pieces;
    }
    public void removePiece(GeneriquePiece p){
        k--;
        for (int l = 0; l < pieces.length; l++) {
            GeneriquePiece piece = pieces[l];
            if(piece==p)pieces[l]=null;
            
        }
        GeneriquePiece[] piecesTmp=new GeneriquePiece[pieces.length];
        for (int l = 0; l < pieces.length; l++) {
            GeneriquePiece piece = pieces[l];
            if(piece!=null)piecesTmp[l]=piece;
        }
        this.pieces=piecesTmp;
    }
    public GeneriquePiece getPiece(){
        for (int l = 0; l < pieces.length; l++) {
            GeneriquePiece piece = pieces[l];
            if(piece!=null) return piece;
        }
        return null;
    }
    public boolean isEmpty(){
        boolean b=true;
        for (int l = 0; l < pieces.length; l++) {
            GeneriquePiece piece = pieces[l];
            b=b && (pieces==null); 
            if(b==false) break;
        }
        return b;
    }
    public PointCarte(int i,int j){
     this.i=i;
     this.j=j;
     this.v=1;
     this.pieces=new GeneriquePiece[10];
    }

    public PointCarte() {
        this.pieces=new GeneriquePiece[10];
    }
    int size(){
        return k;
    }
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getType() {
        return type;
    }

    public double getValue() {
        return v;
    }

    @Override
    public char rapresentation() {
       return '#';
    }

    @Override
    public String toString() {
        return "PointCarte{" + "i=" + i + ", j=" + j + ", type=" + type + ", v=" + v + ", pieces=" + k + '}';
    }

    
}
