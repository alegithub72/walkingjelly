/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import java.io.InputStream;
import java.util.regex.Pattern;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.actions.MarcheAction;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author Alessio Sardaro
 */
public abstract class GeneriqueJoeurs {
    public static final int JOEUR_US=30;
    public static final int JOEUR_HOST=20;
    public static final int EQUIPE_SIZE_US=4;
    public static final int EQUIPE_SIZE_HOSTILE=4;
    Pattern pc;
    protected Piece[] equipe = new Piece[4];
    InputStream inCommand;
    String ptSt = "(s|m|f)(\\d){1,3},(\\d){1,3};((\\d){1,3},(\\d){1,3})?";
    Piece selectionee;
    protected Carte carte;
    int turn;
    int jeur;
    public GeneriqueJoeurs(int jeur,Carte c) {
        if(jeur==JOEUR_HOST)
        equipe = new Piece[EQUIPE_SIZE_HOSTILE];
        else equipe=new Piece[EQUIPE_SIZE_US];
        inCommand = System.in;
        this.carte=c;
        this.jeur=jeur;
        
    }

    public int getJeur() {
        return jeur;
    }


    
    public String getPtSt() {
        return ptSt;
    }

    public void setInCommand(InputStream inCommand) {
        this.inCommand = inCommand;
    }

    public Carte getCarte() {
        return carte;
    }


    public Pattern buildCommand(String p) {
        pc = Pattern.compile(p);
        return pc;
    }

    public Piece[] getEquip() {
        return equipe;
    }


    public Soldat findSquadLeader() {
        Soldat leader = null;
        
        for (Piece p : equipe) {
            Soldat tmp = (Soldat) p;
            if ( leader==null ) 
                leader = tmp;
            if(tmp.getClassement().ordinal()
                    > leader.getClassement().ordinal()) leader=tmp;
        }
        return leader;
    }

    public int dice(int n) {
        double lanche = (n * Math.random());
        int f = (int) lanche + 1;
        return f;
    }

    protected BaseAction actual = null;

    public BaseAction getActual() {
        return actual;
    }

    public void setActual(BaseAction actual) {
        this.actual = actual;
    }

    public Piece getPieceSelectionee() {
        return selectionee;
    }
    
    public void setPieceSelectionee(Piece selectionee) {
        this.selectionee = selectionee;
    }    
    
    public abstract void getCommand();
    
  
    BaseAction decodeCommand(String command){

        char c = command.charAt(0);
        int x0 = Integer.parseInt(command.substring(1, command.indexOf(',')));
        int y0 = Integer.parseInt(command.substring(command.indexOf(',') + 1, command.indexOf(';')));
        int x1 = Integer.parseInt(command.substring(command.indexOf(';') + 1, command.lastIndexOf(',')));
        int y1 = Integer.parseInt(command.substring(command.lastIndexOf(',') + 1, command.length()));
        BaseAction act=null;
        if (c == 's') {
            
            GeneriquePiece p = this.carte.getPointCarte(x0, y0).getPiece();
            if (p == null) ;//do something
            else if (p != null
                    && p.getPieceType() == Piece.ActeurType.SOLDAT) {
                selectionee =(Soldat) p;
            }
        }else if (c == 'm') {
            
            act = new MarcheAction( x0, y0, x1, y1, selectionee);
            
        } else if (c == 'f') {
            
            act = new BaseAction(ActionType.FEU, x0, y0, x1, y1, selectionee, null);
            
        }  
        return act;
  }    

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }
    
    
}
