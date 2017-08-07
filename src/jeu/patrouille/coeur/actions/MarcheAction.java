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
    public MarcheAction(MarcheAction a){
    super(BaseAction.MARCHE,a.i0,a.j0,a.i1,a.j1,a.protagoniste,null);
    
    }
    PointCarte mapTile[];

    private void setMapTile(PointCarte[] mapTile) {
        this.mapTile = mapTile;
    }
    
    @Override
    public List<BaseAction> spreadAction() {
        List<BaseAction> list=new ArrayList<>();
        int i1=(protagoniste!=null)?this.protagoniste.getI():this.i0;
        int j1=(protagoniste!=null)?this.protagoniste.getJ():this.j0;
        for(int k=0;k<mapTile.length;k++){
            if(k>0){ 
                i1=mapTile[k-1].getI();
                j1=mapTile[k-1].getJ();
            }
            int i2=mapTile[k].getI();
            int j2=mapTile[k].getJ();
            MarcheAction b= new MarcheAction(i1, j1, i2, j2, protagoniste);
            System.out.println("k="+k+"----->(i1,j1)----(i2,j2)");
            System.out.println("k="+k+"----->"+i1+","+j1+"----"+""+i2+","+j2);
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
            int calcp=(apbase* letgthp);
       tempActivite=calcp;
    }

    @Override
    public MarcheAction clone()  {        
       MarcheAction m= new MarcheAction(this);
       m.setMapTile(this.mapTile);
       m.setOrdreInitiative(ordreInitiative);
       m.setTempActivite(tempActivite);
       return m;
    }
    
    


    

    
        public static void main(String[] args) {}
}
