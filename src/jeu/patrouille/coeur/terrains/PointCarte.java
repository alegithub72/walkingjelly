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
    GeneriquePiece piece=null;
    List<GeneriquePiece> extraPiece;
    public GeneriquePiece getPiece() {
        return piece;
    }
    public Soldat getFirstSoldat(){
        Soldat s=null;
        if(piece!=null && piece.getPieceType()==Piece.ActeurType.SOLDAT)
            s=(Soldat) piece;
        else {
            for (GeneriquePiece p : extraPiece) {
                if(p.getPieceType()==Piece.ActeurType.SOLDAT){
                    s=(Soldat)p;
                    break;
                }     
            }
            
        }
        return s;
    }
    public void setPiece(GeneriquePiece p){
        piece=p;
    }
    public PointCarte(int i,int j){
     this.i=i;
     this.j=j;
     this.v=1;
     this.extraPiece=new ArrayList<>();
    }
    public void addExtraPiece(GeneriquePiece p){
    extraPiece.add(p);
    
    }
    public void remvoeExtraPiece(GeneriquePiece p){
    extraPiece.remove(p);
    }
    public boolean isInExtra(GeneriquePiece p){
        return extraPiece.contains(p);
        
    }
    public int extraPiecePostion(Piece p){
    return extraPiece.indexOf(p);
        
    }
    public List<GeneriquePiece> getExtraPiece(){
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

    public double getValue() {
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
