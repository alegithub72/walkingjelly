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
 * @author Alessio Sardaro
 */
public class FXUSSoldat extends FXSoldat {


    
    public FXUSSoldat(Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s,"frameSoldierUS2.png",fxcarte);
        
    }


    
    public FXUSSoldat(String f,Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s,f,fxcarte);
        flagImg=new ImageView("americanFlag.png");    
        //defaultFrame=s.getClassement();

    }    

    @Override
    protected void buildFramesFeuAnim() {
        this.frameAnimTimer[0]=new FrameAnimationTimer(7, 8, this, 0, true, 100, null);
    }


    



    @Override
    public String toString() {
        return "s=" + s.toStringSimple() ;
    }
    

    

        
    @Override 
    public void feuFrame(){
        if(s.getPose()==Piece.Pose.DROIT) setFrame(7);
    
    }
    

    
    protected void buildFramesMarcheAnim(){
        frameAnimTimer[0]=new FrameAnimationTimer(1, 3, this, 0, true, 300, FrameAnimationTimer.MARCHE);
        
    }
     

}