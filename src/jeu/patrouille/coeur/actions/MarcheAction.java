/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur.actions;

import jeu.patrouille.coeur.actions.enums.ActionType;
import java.util.ArrayList;
import java.util.List;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Soldat;
import jeu.patrouille.coeur.terrains.PointCarte;

/**
 *
 * @author Alessio Sardaro
 */
public class MarcheAction extends BaseAction{
    
    MarcheAction derivedAction;
    
    public MarcheAction( int i0, int j0, int i1, int j1, GeneriquePiece protagoniste){
    super(ActionType.MARCHE, i0 ,j0, i1, j1, protagoniste, null);
    
    }
    MarcheAction(MarcheAction a){
    super(ActionType.MARCHE,a.i0,a.j0,a.i1,a.j1,a.protagoniste.clonerPiece(),null);
    
    }

    public MarcheAction getDerivedAction() {
        return derivedAction;
    }

    public void setDerivedAction(MarcheAction derivedAction) {
        this.derivedAction = derivedAction;
    }
    
    
    
    PointCarte mapTile[];

    private void setMapTile(PointCarte[] mapTile) {
        this.mapTile = mapTile;
    }
    
    @Override
    public List<BaseAction> spreadAction()throws Exception {
        MarcheAction derivedAct=new MarcheAction(this);
        List<BaseAction> list=new ArrayList<>();
        int i1=protagoniste.getI();
        int j1=protagoniste.getJ();
        for(int k=0;k<mapTile.length;k++){
            if(k>0){ 
                i1=mapTile[k-1].getI();
                j1=mapTile[k-1].getJ();
            }
            if(k<mapTile.length && k>0){
                int i2=mapTile[k].getI();
                int j2=mapTile[k].getJ();
                MarcheAction b= new MarcheAction(i1, j1, i2, j2, protagoniste);
                b.setDerivedAction(derivedAct);
                b.setMapTile(new PointCarte[]{mapTile[k]});
                b.calculeActionPointDesAction();
                list.add(b); 
            
            System.out.println("spread action marche k="+k+"--"+protagoniste.toStringSimple()+"--->(i1,j1)----(i2,j2)");
            System.out.println("spread action marche k="+k+"----->"+i1+","+j1+"----"+""+i2+","+j2);                  
        }
         
        }

        
        return list; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override   
    public void calculeActionPointDesAction()throws Exception {
            
            Soldat s=(Soldat)protagoniste;
            int apbase=s.tempNecessarieDesActionBase(ActionType.MARCHE);
            if(mapTile==null){
                mapTile =Carte.getLigne(i0, j0, i1, j1);
            }
            int letgthp=mapTile.length;
            int calcp=(apbase* letgthp);
            System.out.println("----------------->CALCULATE TILE LINE SIZE:->"+mapTile.length);
       tempActivite=calcp;
    }

    @Override
    public MarcheAction clone()  {        
       MarcheAction m= new MarcheAction(this);
       m.setMapTile(this.mapTile);//TODO afre clone di questa operazione
       m.setOrdreInitiative(ordreInitiative);
       m.setTempActivite(tempActivite);
       //m.setDerivedAction(this.derivedAction.clone());
       return m;
    }
    public static MarcheAction marcheLointain(Soldat s) {
        MarcheAction act=null;
        try{
        int tbase=s.tempNecessarieDesActionBase(ActionType.MARCHE);
        if(s.getTempDisponible()>=tbase){
            int delta=(int)s.getTempDisponible()/tbase;
            if ((s.getI() + delta) >= Carte.CARTE_SIZE_I ) 
                delta=0;
            if((s.getJ()+delta)>=Carte.CARTE_SIZE_J) 
                delta=0;
            System.out.println("tbase:"+tbase+" delta:"+delta);
            act = new MarcheAction(s.getI(), s.getJ(), s.getI()+delta, s.getJ()+delta, s);        
        }else System.out.println("TD:"+s.getTempDisponible());
        
        }catch(Exception ex){
            throw new  RuntimeException("non deve succedere");
        }
        System.out.println("fuggire action :"+act);
        return act;
    }
    


    

    
        public static void main(String[] args) {}
}
