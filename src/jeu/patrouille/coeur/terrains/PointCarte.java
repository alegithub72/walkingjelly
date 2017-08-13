/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.terrains;

import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.grafic.TerrainTextRapresentation;
import jeu.patrouille.coeur.pieces.Piece;

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
    protected int v;
    Piece piece=null;
    List<Piece> extraPiece;
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece p){
        piece=p;
    }
    public PointCarte(int i,int j){
     this.i=i;
     this.j=j;
     this.v=1;
     this.extraPiece=new ArrayList<>();
    }
    public void addExtraPiece(Piece p){
    extraPiece.add(p);
    
    }
    public void remvoeExtraPiece(Piece p){
    extraPiece.remove(p);
    }
    public boolean isInExtra(Piece p){
        return extraPiece.contains(p);
        
    }
    public int extraPiecePostion(Piece p){
    return extraPiece.indexOf(p);
        
    }
    public List<Piece> getExtraPiece(){
        return extraPiece;
    }
    public PointCarte() {
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

    public int getValue() {
        return v;
    }

    @Override
    public char rapresentation() {
       return '#';
    }

    @Override
    public String toString() {
        return "PointCarte{" + "i=" + i + ", j=" + j + ", type=" + type + ", v=" + v + ", piece=" + piece + '}';
    }

    
}
