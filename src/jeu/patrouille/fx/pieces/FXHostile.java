/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.animation.FrameAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class FXHostile extends FXSoldat{
    
    public FXHostile(Soldat s,int pos,FXCarte fxcarte){
        super( FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s ,"frameHostile.png", fxcarte);
        this.flagImg=new ImageView("hosFlag.png");
        
     
        
        
        
        
    }
    
    protected void buildFramesFeuAnim() {
        this.frameAnimTimer[0]=new FrameAnimationTimer(7, 8, this, 0, true, 100, null);
    }

    @Override
    public void createFXSoldat() {
        super.createFXSoldat();
        getChildren().remove(classmentImg);
    }

    @Override
    protected void buildFramesMarcheAnim() {
        frameAnimTimer[0]=new FrameAnimationTimer(4, 7, this, 0, true, 200, FrameAnimationTimer.MARCHE);
    }
    
    @Override
    public void feuFrame(){
        if(s.getPose()==Piece.Pose.DROIT)
            setFrame(7);
    }
    
}
