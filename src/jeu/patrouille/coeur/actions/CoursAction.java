/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.actions.enums.ActionType;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author Alessio Sardaro
 */
public class CoursAction extends MarcheAction {
    public CoursAction( int i0, int j0, int i1, int j1, Piece protagoniste){
        super(ActionType.COURS,i0, j0,i1 ,j1,protagoniste);
 
      
    } 

    public CoursAction(CoursAction a) {
        super(a)   ;
                
                
    }
    

    @Override
    public void calculeActionPointDesAction() throws Exception {
            Soldat s=(Soldat)protagoniste;
            int tempBase=type.TN();
            if(s.isDoubled()) tempBase=tempBase*2;
            //int tempDisponible=s.getTempDisponible();
            double range=Carte.distance(i0, j0, i1, j1, FXCarte.TILE_SIZE);
              mapTile=Carte.getLigne(i0, j0, i1, j1);
            double td=(range/5)*(tempBase);
            //tempDisponible=tempDisponible-(int)td;
            tempActivite=(int)Math.round(td);
    }

    @Override
    public BaseAction[] spreadAction() throws Exception {
          Soldat s=(Soldat)protagoniste;
        
        double tempBase=type.TN();
        if(s.isDoubled()) tempBase=tempBase*2;
        BaseAction list[]=new BaseAction[Math.round(mapTile.length/5)+1];
        int count=0;int size=-1;
        CoursAction cours=new CoursAction(i0, j0, -1, -1, s);
        for(int m=0;m<mapTile.length;m++){
            size=m+5;
            if(size>mapTile.length) size=mapTile.length-m;
            else size=5;
            PointCarte[] l=new PointCarte[size];
            for(int n=0; n<size;n++){
                System.out.println("--->n="+n+" -->m="+m);
                 l[n]=mapTile[n+m];
                }
            m=m+size-1;
            cours.setMapTile(l);
            cours.setI1(l[size-1].getI());
            cours.setJ1(l[size-1].getJ());
            cours.setDerivedAction(this);
            cours.calculeActionPointDesAction();
            
            list[count]=cours;
            System.out.println("action cours "+cours+" temp activite:"+cours.getTempActivite());
            count++;
            cours=new CoursAction(l[size-1].getI(),l[size-1].getJ(), -1, -1, s);
        }

        return list;
    }
    @Override
    public MarcheAction clone()  {        
       CoursAction m= new CoursAction(this);
       m.setMapTile(this.mapTile);//TODO afre clone di questa operazione
       m.setOrdreInitiative(ordreInitiative);
       m.setTempActivite(tempActivite);
       //m.setDerivedAction(this.derivedAction.clone());
       return m;
    }    
    
    
}
