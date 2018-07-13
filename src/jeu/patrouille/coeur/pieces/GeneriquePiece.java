/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces;

import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;

/**
 *
 * @author appleale
 */
public abstract class GeneriquePiece {
    
    Piece.ActeurType type;
    int i;
    int j;
    GeneriqueJoeurs boss;

    public GeneriquePiece(GeneriqueJoeurs boss) {
        this.boss=boss;
    }

    public Piece.ActeurType getPieceType() {
        return type;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public GeneriqueJoeurs getBoss() {
        return boss;
    }

    @Override
    public String toString() {
        return "GeneriquePiece{" + "type=" + type + ", i=" + i + ", j=" + j + ", boss=" + boss + '}';
    }
    public boolean isTargetable(){
            return type==Piece.ActeurType.SOLDAT || type==Piece.ActeurType.JEEP;
                    
    }
    public abstract GeneriquePiece clonerPiece();

    public abstract String toStringSimple();
    
}
