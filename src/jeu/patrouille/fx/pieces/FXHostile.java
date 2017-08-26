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
import jeu.patrouille.fx.animation.JeuPatrouilleAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.animation.JeuPatrouilleAnimationTimer.Sound;

/**
 *
 * @author appleale
 */
public class FXHostile extends FXSoldat{
    
    public FXHostile(Soldat s,int pos,FXCarte fxcarte){
        super( FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s ,"frameHostile.png", fxcarte);
        this.flagImg=new ImageView("hosFlag.png");
        
     
        
        
        
        
    }
    
    @Override
    public void buildBlessAnim() {
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(defaultFrame,defaultFrame, this, 0, 1, 300, null,Sound.GRUNT5);
        this.frameAnimTimer[0].buildMedia();
    }    
    @Override
    public void buildFramesFeuAnim() {
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(0, 1, this, 0, 5, 100, s.getArmeUtilise(),null);
        this.frameAnimTimer[0].buildMedia();        
    }

    @Override
    public void createFXSoldat() {
        super.createFXSoldat();
        getChildren().remove(classmentImg);
    }

    @Override
    protected void buildFramesMarcheAnim() {
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(4, 7, this, 0, -1, 200, null,Sound.MARCHE);
        this.frameAnimTimer[0].buildMedia();        
    }
    
    @Override
    public void feuFrame(){
        if(s.getPose()==Piece.Pose.DROIT)
            setFrame(0);
    }
    
}
