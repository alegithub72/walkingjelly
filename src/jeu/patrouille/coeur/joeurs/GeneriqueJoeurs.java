/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author appleale
 */
public abstract class GeneriqueJoeurs {
    public static final int JOEUR_US=30;
    public static final int JOEUR_HOST=20;
    Pattern pc;
    Piece[] equip = new Piece[4];
    InputStream inCommand;
    String ptSt = "(m|f)(\\d){1,3},(\\d){1,3};(\\d){1,3},(\\d){1,3}";

    public GeneriqueJoeurs(Piece[] patrouille) {
        equip = patrouille;
        inCommand = System.in;
    }

    public String getPtSt() {
        return ptSt;
    }

    public void setInCommand(InputStream inCommand) {
        this.inCommand = inCommand;
    }


    public Pattern buildCommand(String p) {
        pc = Pattern.compile(p);
        return pc;
    }



    public Soldat findSquadLeader() {
        Soldat leader = null;
        
        for (int i = 0; i < equip.length; i++) {
            Soldat tmp=(Soldat)equip[i];
            if (leader == null || tmp.getClassement() > leader.getClassement()) {
                leader = tmp;
            }
        }
        return leader;
    }

    public int dice(int n) {
        double lanche = (n * Math.random());
        int f = (int) lanche + 1;
        return f;
    }
    public abstract BaseAction getCommand();
}
