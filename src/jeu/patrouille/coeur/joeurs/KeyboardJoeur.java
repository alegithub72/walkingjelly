/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import java.util.Scanner;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.armes.GeneriqueArme;
import jeu.patrouille.coeur.armes.BenelliM3;
import jeu.patrouille.coeur.armes.M16;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;

/**
 *
 * @author Alessio Sardaro
 */
public class KeyboardJoeur extends Joeur {

    public KeyboardJoeur(int jeur,Carte c) {
        super(jeur,c);
        costruirePatrouille();
    }
 
    
    public void getCommand() {

        String command = null;
        Scanner sc = new Scanner(inCommand);
        boolean cnotTyped = true;
        do {
            boolean b = sc.hasNext();
            if (b) {
                cnotTyped = false;
                command = sc.next(ptSt);
            }
            System.out.println(command);
        } while (cnotTyped);
        actual=decodeCommand(command);
        textCommand=actual.toString();

  
    }
  void costruirePatrouille() {
        Soldat s = new Soldat("Sgt.", "Smith", 7, 8, 6, 5, 6,
                5, 5, 8, 9,Piece.Direction.N,this);
           BenelliM3 ben=new BenelliM3();
           M16 m16=new M16();
            GeneriqueArme a[] = {ben,m16};
            s.setArmeEquip(a);
            s.setClassement(Soldat.CLASS_SGT);
            s.setArraN(0);
            s.setArmeUtilise(m16);
            
        equipe[0] = s;
         
        s = new Soldat("Rifleman", "Williams", 7, 8, 6, 5, 6,
                5, 5, 8, 8,Piece.Direction.N,this);
            GeneriqueArme a1[] = {new BenelliM3()};
            s.setArmeEquip(a1);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(1);
        equipe[1] = s;
         
        s = new Soldat("Rifleman", "Miller", 7, 8, 6, 5, 6,
                5, 5, 8, 8,Piece.Direction.N,this);
            GeneriqueArme a2[] = {new BenelliM3()};
            s.setArmeEquip(a2);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(2);
       equipe[2] = s;
        
        s = new Soldat("Rifleman", "Anderson", 7, 8, 6, 5, 5, 
                5, 5, 8, 8,Piece.Direction.N,this);
            GeneriqueArme a3[] = {new BenelliM3()};
            s.setArmeEquip(a3);
            s.setClassement(Soldat.CLASS_SOLDAT);
            s.setArraN(3);
        equipe[3] = s;

   }    

}
