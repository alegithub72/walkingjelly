/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.pieces;

import javafx.scene.image.ImageView;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class FXUSSoldat extends Sprite{

    Soldat s;
    int pos;
    ImageView blessureImg;
    ImageView flagImg;
    ImageView classmentImg;
    int defaultFrame;
    ImageView selectionneImg;

    public FXUSSoldat(Soldat s,int pos){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,"frameFigurineUS.png",null);

        this.s=s;   
        this.pos=pos;
        blessureImg=new ImageView("wound.png");
        flagImg=new ImageView("americanFlag.png");
        if(s.getClassement()==Soldat.CLASS_SGT)  classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Soldat.CLASS_SOLDAT)classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
       defaultFrame=0;
        defaultFrame();

        
    }
    
    public FXUSSoldat(String f,Soldat s,int pos){
        super(FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,
                FXCarte.TILE_SIZE,FXCarte.TILE_SIZE,f,null);

        this.s=s;   
        this.pos=pos;
        blessureImg=new ImageView("wound.png");
        flagImg=new ImageView("americanFlag.png");
        if(s.getClassement()==Soldat.CLASS_SGT)  classmentImg=new ImageView("sgtGrade.png");
        else if(s.getClassement()==Soldat.CLASS_SOLDAT)classmentImg=new ImageView("scelto.png");
        selectionneImg=new ImageView("selectUS.png");       
        //defaultFrame=s.getClassement();
       defaultFrame=0;
        defaultFrame();

        
    }    

    public void setS(Soldat s) {
        this.s = s;
    }
    
    public void setDeafultFrme(int n){
    this.defaultFrame=n;
    }
    public void buildSprite(){
        if(!this.getChildren().contains(blessureImg))this.getChildren().add(blessureImg);
        if(!this.getChildren().contains(flagImg))this.getChildren().add(flagImg);
        if(!this.getChildren().contains(classmentImg))this.getChildren().add(classmentImg);
        flagImg.relocate(this.getX(),this.getY());
        classmentImg.relocate(this.getX(),this.getY());
        classmentImg.setTranslateX(FXCarte.TILE_SIZE-20);
        classmentImg.setTranslateY(  0);
        blessureImg.relocate(this.getX(),this.getY());
        blessureImg.setTranslateX(0);
        blessureImg.setTranslateY(FXCarte.TILE_SIZE-10);
        if(getChildren().contains(selectionneImg)) {
        selectionneImg.relocate(getX(), getY());
        }
        
    }

    public Soldat getSoldat(){
    return s;
    }
    
    public void defaultFrame(){
        setFrame(defaultFrame);
    }
    public void surlignerSoldat(){
   
    if(!getChildren().contains(selectionneImg)) getChildren().add(selectionneImg);
    selectionneImg.relocate(this.getX(), this.getY());
    selectionneImg.toBack();
    //setFrame(3);
    }
    public void soldatDeselectionne(){
    
    if(getChildren().contains(selectionneImg)) getChildren().remove(selectionneImg);

    //setFrame(3);
    }

    @Override
    public String toString() {
        return "s=" + s.toStringSimple() ;
    }
    
    public void playMove(BaseAction act){
    
        
        
    }
    
}
