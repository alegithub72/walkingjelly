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
import jeu.patrouille.coeur.pieces.exceptions.TomberArmeException;
import jeu.patrouille.coeur.pieces.parts.Corp;
import jeu.patrouille.fx.animation.JeuPatrouilleAnimationTimer;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.animation.JeuPatrouilleAnimationTimer.Sound;

/**
 *
 * @author Alessio Sardaro
 */
public class FXUSSoldat extends FXSoldat {


    
    public FXUSSoldat(Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s,"frameSoldierUS2.png",fxcarte);
        feu1=7;
        feu2=8;

    }


    
    public FXUSSoldat(String f,Soldat s,int pos,FXCarte fxcarte){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,pos,s,f,fxcarte);
        flagImg=new ImageView("americanFlag.png");    
        //defaultFrame=s.getClassement();

    }    

    @Override
    public void buildFramesFeuAnim() {
        try{
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{feu1, feu2}, this, 0,1 , 100,s.getArmeUtilise(),null);
        frameAnimTimer[0].buildMedia();        
         }catch(TomberArmeException ex){
           throw new RuntimeException(ex);
        }
    }

    @Override
    public void buildBlessAnim() {
       
        this.frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{defaultFrame,defaultFrame}, this, 0, 1,500, null,Sound.GRUNT5);
        frameAnimTimer[0].buildMedia();        

    }

    @Override
    public void droitPosition() {
        this.setW(50);
        buildFrameImages(new  Image("frameSoldierUS2.png"));
        poseImg=Piece.Pose.DROIT;
        feu1=7;
        feu2=8;      
        initialAngle=90;
    }
    @Override
    public void pronePosition(){
        this.setW(100);
        this.buildFrameImages(new Image("frameProneUSSoldier.png"));
        this.poseImg=Piece.Pose.PRONE;
        imgView.setTranslateX(-25);
        feu1=0;
        feu2=1;
        initialAngle=180;
        
    }


    @Override
    public String toString() {
        return "s=" + s.toStringSimple() ;
    }
    

    

        
    @Override 
    public void feuFrame(){
        if(s.getPose()==Piece.Pose.DROIT) setFrame(8);
        else if(s.getPose()==Piece.Pose.PRONE) setFrame(1);
    
    }
    

    @Override
    protected void buildFramesMarcheAnim(){
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{1,2,3}, this, 0, -1, 200,null,Sound.MARCHE);
        frameAnimTimer[0].buildMedia();        
        
    }
       @Override
    protected void buildCrawlAnim(){
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{1,2,3}, this, 0, -1, 400,null,Sound.MARCHE);
        frameAnimTimer[0].buildMedia();        
        
    }  

    @Override
    protected void buildFramesCoursAnim() {
        frameAnimTimer[0]=new JeuPatrouilleAnimationTimer(new int[]{9,10}, this, 0, -1, 400,null,Sound.COURS);
        frameAnimTimer[0].buildMedia();    
    }

    @Override
    void blessFrame(Corp.CorpParts location) {
                setW(100);
                Image img = new Image("feritoUS.png");
                buildFrameImages(img);
                if(!s.isBandage()){
                    if(location==Corp.CorpParts.Abdomen||
                        location==Corp.CorpParts.Thorax ||
                        location==Corp.CorpParts.Ventre)setFrame(4);
                    else  if(location==Corp.CorpParts.Tete) setFrame(3);
                    else if(location==Corp.CorpParts.BrasDroite || 
                        location==Corp.CorpParts.BrasGauche)setFrame(6);
                    else if(location==Corp.CorpParts.JambeGauche ||
                        location==Corp.CorpParts.JambeDroite) setFrame(7);      
                }else{
                    if(location==Corp.CorpParts.Abdomen||
                        location==Corp.CorpParts.Thorax ||
                        location==Corp.CorpParts.Ventre)setFrame(1);
                    else  if(location==Corp.CorpParts.Tete) setFrame(0);
                    else if(location==Corp.CorpParts.BrasDroite || 
                        location==Corp.CorpParts.BrasGauche)setFrame(2);
                    else if(location==Corp.CorpParts.JambeGauche ||
                        location==Corp.CorpParts.JambeDroite) setFrame(5);                  
                
                }
    }
    


}