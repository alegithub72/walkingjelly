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
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class FXHostile extends FXSoldat{
    
    public FXHostile(Soldat s,int pos,FXCarte fxcarte){
        super( 320,320,pos,s ,"alienCheckBig.png", fxcarte);
        this.flagImg=new ImageView("hosFlag.png");
       // flagImg.setScaleX(4);
       // flagImg.setScaleY(4);
        feu1=0;
        feu2=1;
       offsx=113;
       offsy=200;
        
        
        
    }

    @Override
    protected void buildFramesMarcheAnim() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildFramesFeuAnim() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildBlessAnim() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void buildFramesCoursAnim() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
