/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.pieces.parts.Corp;
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
        
        feu1=0;
        feu2=1;

        
        
        
    }
    
    @Override
    public void buildBlessAnim() {
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{defaultFrame,defaultFrame}, this, 0, 1, 500, null,Sound.GRUNT5);
        this.frameAnimTimer[0].buildMedia();
    }    
    @Override
    public void buildFramesFeuAnim() {
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{feu1, feu2}, this, 0, 1, 100, s.getArmeUtilise(),null);
        this.frameAnimTimer[0].buildMedia();        
    }
    @Override
    public void buildCrawlAnim() {
        //this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(feu1, feu2, this, 0, 1, 100, s.getArmeUtilise(),null);
        //this.frameAnimTimer[0].buildMedia();        
    }    

    @Override
    public void createFXSoldat() {
        super.createFXSoldat();
        getChildren().remove(classmentImg);
    }

    @Override
    protected void buildFramesMarcheAnim() {
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{4,5,6,7}, this, 0, -1,200, null,Sound.MARCHE);
        this.frameAnimTimer[0].buildMedia();        
    }
    
    @Override
    protected void buildFramesCoursAnim() {
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{8,9}, this, 0, -1,200, null,Sound.COURS);
        this.frameAnimTimer[0].buildMedia();        
    }

    @Override
    public void feuFrame(){
            setFrame(1);
    }

    @Override
    public void droitPosition() {
        buildFrameImages(new Image("frameHostile.png"));
        poseImg=Piece.Pose.DROIT;
        feu1=0;
        feu2=1;
        
    }
    @Override
    public void pronePosition(){
    
    }

    @Override
    void blessFrame(Corp.CorpParts location) {
                setW(100);
                Image img = new Image("feritoHostile.png");
                buildFrameImages(img);
                if(!s.isBandage()){
                    if(location==Corp.CorpParts.Abdomen||
                        location==Corp.CorpParts.Thorax ||
                        location==Corp.CorpParts.Ventre)setFrame(1);
                    else  if(location==Corp.CorpParts.Tete) setFrame(0);
                    else if(location==Corp.CorpParts.BrasDroite || 
                        location==Corp.CorpParts.BrasGauche)setFrame(2);
                    else if(location==Corp.CorpParts.JambeGauche ||
                        location==Corp.CorpParts.JambeDroite) setFrame(3);      
                }else{
                    if(location==Corp.CorpParts.Abdomen||
                        location==Corp.CorpParts.Thorax ||
                        location==Corp.CorpParts.Ventre)setFrame(5);
                    else  if(location==Corp.CorpParts.Tete) setFrame(4);
                    else if(location==Corp.CorpParts.BrasDroite || 
                        location==Corp.CorpParts.BrasGauche)setFrame(6);
                    else if(location==Corp.CorpParts.JambeGauche ||
                        location==Corp.CorpParts.JambeDroite) setFrame(7);                  
                
                }
    }
            
    
}
