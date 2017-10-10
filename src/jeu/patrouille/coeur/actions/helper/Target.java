/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.actions.helper;

import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public class Target implements Comparable<Target>{
    Soldat s;
    double dist;
    int score;
    int hits;
    public Target(Soldat s,double dist,int  shotN){
        this.s=s;
        this.dist=dist;
        score=shotN;
    }

    @Override
    public int compareTo(Target o) {
            if(dist==o.dist) return 0;
            if(dist>o.dist) return 1;
            return -1;
    }

    public int getScore() {
        return score;
    }

    public int getI() {
        return s.getI();
    }

    public int getJ() {
        return s.getJ();
    }

    public Soldat getS() {
        return s;
    }

    public double getDist() {
        return dist;
    }

    public int getHits() {
        return hits;
    }

    public void addHits() {
        this.hits++;
    }

    @Override
    public String toString() {
        return "Target{" + "s=" + s.toStringSimple() + ", dist=" + dist + ", score=" + score + ", hits=" + hits + '}';
    }
    
   
    
    
}
