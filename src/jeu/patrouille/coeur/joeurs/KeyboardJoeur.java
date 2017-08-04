/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.joeurs;

import java.util.Scanner;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Piece;

/**
 *
 * @author appleale
 */
public class KeyboardJoeur extends GeneriqueJoeurs {

    public KeyboardJoeur(Piece[] equipe) {
        super(equipe);
    }

    public BaseAction getCommand() {
        BaseAction ac = null;
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
        char c = command.charAt(0);
        int x0 = Integer.parseInt(command.substring(1, command.indexOf(',')));
        int y0 = Integer.parseInt(command.substring(command.indexOf(',') + 1, command.indexOf(';')));
        int x1 = Integer.parseInt(command.substring(command.indexOf(';') + 1, command.lastIndexOf(',')));
        int y1 = Integer.parseInt(command.substring(command.lastIndexOf(',') + 1, command.length()));
        if (c == 'm') {
            ac = new BaseAction(BaseAction.MARCHE, x0, y0, x1, y1, null, null);
        } else if (c == 'f') {
            ac = new BaseAction(BaseAction.FEU, x0, y0, x1, y1, null, null);
        }
        return ac;
    }
    
}
