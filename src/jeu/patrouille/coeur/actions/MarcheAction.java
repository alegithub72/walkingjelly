/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.terrains.PointCarte;

/**
 *
 * @author appleale
 */
public class MarcheAction extends BaseAction{
    public MarcheAction( int i0, int j0, int i1, int j1, Piece protagoniste){
    super(BaseAction.MARCHE, i0 ,j0, i1, j1, protagoniste, null);
    
    }
    PointCarte mapTile[];

    private void setMapTile(PointCarte[] mapTile) {
        this.mapTile = mapTile;
    }
    
    @Override
    public List<BaseAction> spreadAction() {
        List<BaseAction> list=new ArrayList<>();
        int i1=this.protagoniste.getI();
        int j1=this.protagoniste.getJ();
        for(int k=0;k<mapTile.length;k++){
            if(k>0){ 
                i1=mapTile[k-1].getI();
                j1=mapTile[k-1].getJ();
            }
            int i2=mapTile[k].getI();
            int j2=mapTile[k].getJ();
            MarcheAction b= new MarcheAction(i1, j1, i2, j2, protagoniste);
            b.setMapTile(new PointCarte[]{mapTile[k]});
            b.calculeActionPointDesActions();
            list.add(b);
        }
        return list; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override   
    public void calculeActionPointDesActions() {
            int apbase= BaseAction.ACTIONPOINTVALOR[type];
            if(mapTile==null){
                mapTile =Carte.getLigne(i0, j0, i1, j1);
            }
            int letgthp=mapTile.length;
            System.out.println("distanza calcolata---------_>"+letgthp+"punti base"+apbase);
            int calcp=(apbase* letgthp);
       tempActivite=calcp;
    }


    

    
        public static void main(String[] args) {}
}
