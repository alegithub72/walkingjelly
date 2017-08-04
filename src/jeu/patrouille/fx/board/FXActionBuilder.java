/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.board;

import javafx.scene.Node;
import jeu.patrouille.coeur.actions.BaseAction;

/**
 *
 * @author appleale
 */
public class FXActionBuilder {
    private BaseAction act;
    private Node carteNode;
    public FXActionBuilder(Node cartenode,BaseAction act){
        this.act=act;
        this.carteNode=carteNode;
    }
    
    
    
    
    
}
