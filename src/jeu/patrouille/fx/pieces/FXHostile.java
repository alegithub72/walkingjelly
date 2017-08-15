/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.fx.animation.FrameAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class FXHostile extends FXUSSoldat{
    
    public FXHostile(Soldat s,int pos,FXCarte fxcarte){
        super( "frameHostile.png", s, pos,fxcarte);
        this.flagImg=new ImageView("hosFlag.png");
        
     
        
        
        
        
    }

    @Override
    public void buildFXUSSoldat() {
        super.buildFXUSSoldat();
        getChildren().remove(classmentImg);
    }

    @Override
    protected void createMove() {
        frameAnimTimer[0]=new FrameAnimationTimer(4, 7, this, 0, true, 200, FrameAnimationTimer.MARCHE);
    }
    
    
}
