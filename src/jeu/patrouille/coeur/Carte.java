/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.coeur;


import jeu.patrouille.coeur.terrains.PointCarte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.grafic.GraficCarteInterface;
import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
import jeu.patrouille.coeur.pieces.Piece.Direction;
import jeu.patrouille.coeur.terrains.Terrain;
import jeu.patrouille.coeur.terrains.AngleGrosMur;
import jeu.patrouille.coeur.terrains.Arbre;
import jeu.patrouille.coeur.terrains.Arbre2;
import jeu.patrouille.coeur.terrains.Arbre3;
import jeu.patrouille.coeur.terrains.Arbre4;
import jeu.patrouille.coeur.terrains.Fenetre;
import jeu.patrouille.coeur.terrains.GrosMur;
import jeu.patrouille.coeur.terrains.Haie;
import jeu.patrouille.coeur.terrains.Interior;
import jeu.patrouille.coeur.terrains.Porte;
import jeu.patrouille.coeur.terrains.Street;
import jeu.patrouille.coeur.terrains.StreetBorder;

/**
 *
 * @author Alessio Sardaro
 */
public class Carte implements GraficCarteInterface{

    public static  int CARTE_SIZE_J = 48;
    public static  int CARTE_SIZE_I= 36;
    /**
     * 5m*5m 1000m*1000m 1000/5=200
     */
    Terrain[][] terrain ;
    String fileMap;
    StringBuffer mapTxt;
    public Carte(String file) throws IOException {
        fileMap=file;
        mapDelimiter="q{1}\\R?Q?";
        mapInput=new FileInputStream(new File(fileMap));
        System.out.println("map"+fileMap);
    }
    public Carte(){

    }

    @Override
    public void reMountFXCarteMenuItemsAndScroll() {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public int getMapW(){
        return CARTE_SIZE_J;
    }
    public int getMapH(){
    return CARTE_SIZE_I;
    }
    String mapDelimiter;
    InputStream mapInput;

    public void setMmapDelimiter(String mapDelimiter) {
        this.mapDelimiter = mapDelimiter;
    }
    
    public void setMapInput(InputStream mapInput) {
        
        this.mapInput = mapInput;
    }

    public String getMapTxt() {
        return mapTxt.toString();
    }

    @Override
    public void play(BaseAction act1, BaseAction act2) {
       System.out.println("play garafic text");
    }
    

    
    
private void decoderTaille(String line){
        int ipos=line.indexOf('x');
        String is=line.substring(0,ipos);
        int i_taille=Integer.parseInt(is);
        int posj=line.indexOf('q');
        String js=line.substring(ipos+1,posj);
        int j_taille=Integer.parseInt(js);
        terrain=new Terrain[i_taille][j_taille];
        CARTE_SIZE_I=i_taille;
        CARTE_SIZE_J=j_taille;
        
}
    
    public void loadMap() throws IOException {
        
        Scanner mapF = new Scanner(mapInput);
        int i=0;
        mapF.useDelimiter(mapDelimiter);
        mapTxt=new StringBuffer("");
        while(mapF.hasNext()){
           String line=mapF.next()+"q";
           if(i==0) decoderTaille(line);
           buildCarteString(line, i);
           if( i>1 ){
               elaborationFileLine(i-2, line);
               
           }
          
           i++;
        }


    }
    void buildCarteString(String line,int i){
            String ra=line.replaceAll("\\R", "");
           if(i==37) ra=ra+"Q";  
           ra=ra.trim()+"\n";
           if(i==0) ra=" "+ra;
           if(i!=37) mapTxt.append(ra);
    
    } 
    private void setTerrain(int i, int j, int t) {
        terrain[i][j] = new Terrain(i, j);
    }

    public double getTerrain(int i, int j) {
        return (double) terrain[i][j].getValue() / 100d;
    }

    public Terrain getPointCarte(int i, int j) {
        return terrain[i][j];
    }
    public Terrain getPointCarte(PointCarte p) {
        return terrain[p.getI()][p.getJ()];
    }

     public static PointCarte[] getLigne(int i1, int j1, int i2, int j2) {
         System.out.println("-------------------LIGNE DEMARRAGE----------------------------------");
         System.out.println("geligne (i1 ,j1 ) , (i2 ,j2 )");
         System.out.println("geligne (" + i1 + " ," + j1 + " ) , (" + i2 + " ," + j2 + " )");
         double i2i1 = (i2 - i1);
         double j2j1 = (j2 - j1);
         double m =  i2i1/j2j1 ;
         System.out.println("(i2-i1)=" + i2i1);
         System.out.println("(j2-j1)=" + j2j1);
         System.out.println("m=" + m);
         double h =0;
         if(j2j1==0) 
             h=0d;
         else {
                double a=(j2*i1)-(j1*i2);
                double b=(j2-j1);
                h=a/b;
         }
             
         System.out.println("h=" + h);
         double circleX = Math.pow(i2i1, 2);
         double circleY = Math.pow(j2j1, 2);
         double r = Math.sqrt(circleX + circleY);
         int size=(int)r;

         
         List<PointCarte> listp = new ArrayList();


         System.out.println("r="+r+" size="+size);
        
         
         if(j1<j2 && Math.abs(i2i1)<=Math.abs(j2j1) && h!=0)  {
                Integer  iy=null;
                Integer  jx=null;
                int k=0;
               do{
                    jx=k+j1;
                    double iydouble=m*jx+h; 
                    iy=aroundN(iydouble,size,k,i1,i2);  
                    PointCarte p=new PointCarte(iy, jx);                    
                    listp.add(p);
                    System.out.println("cas1) k="+k+"-> "+p) ;                       
                    k++;      
                 } while(iy==null || !(iy==i2 && jx==j2));
             }else
                
             if(j1>j2 && Math.abs(i2i1)<=Math.abs(j2j1)&& h!=0)  {
                Integer  iy=null;
                Integer  jx=null;
                int k=0;
               do{
                    jx=k+j2;
                    double iydouble=m*jx+h; 
                    iy=aroundN(iydouble,size,k,i2,i1);                   
                    PointCarte p=new PointCarte(iy, jx);
                    if(k>0) listp.add(0,p);
                    else listp.add(p);
                    System.out.println("cas2) k="+k+"-> "+p) ;        
                    k++;
                 } while(iy==null || !(iy==i1 && jx==j1));
             }
             else if(i1<i2 && Math.abs(i2i1)>Math.abs(j2j1)&& h!=0) {
               Integer  jx=null;
                int k=0;
                 Integer  iy=null;
                 do{
                    iy=k+i1;
                    double jxdouble=(iy-h)/m; 
                    jx=aroundN(jxdouble,size,k,j1,j2);  
                    PointCarte p=new PointCarte(iy, jx);                    
                    listp.add(p);
                    System.out.println("cas3) k="+k+"-> "+p) ;                       
                    k++;         
                 }while((iy==null && jx==null) || !(iy==i2 && jx==j2));
                 
             }else  if(i1>i2 && Math.abs(i2i1)>Math.abs(j2j1)&& h!=0) {
               Integer  jx=null;
                int k=0;
                 Integer  iy=null;
                 do{
                     
                    iy=k+i2;
                    double jxdouble=(iy-h)/m;
                    //TODO il piu grande se e' il primo il piu piccolo se e' l'ultimo
                    jx=aroundN(jxdouble,size,k,j2,j1);  
                    PointCarte p=new PointCarte(iy, jx);    
                    if(k>0) listp.add(0,p);
                    else listp.add(p);
                    
                    System.out.println("cas4) k="+(k-1)+"-> "+p) ;                     
                    k++;         
                 }while((iy==null && jx==null) || !(iy==i1 && jx==j1));
                 
             }else {
                 if(i2>i1){
                 for(int k=i1;k<=i2;k++){
                     PointCarte p=new PointCarte(k, j2);
                     listp.add(p);
                    System.out.println("cas5) k="+(k-i1)+"-> "+p) ;                       
                    }
                 }else if(i2<i1){
                 for(int k=i2;k<=i1;k++){
                      PointCarte p=new PointCarte(k,j2);
                      if(k>i2)
                      listp.add(0,p);
                      else listp.add(p);  
                    System.out.println("cas6) k="+(k-i2)+"-> "+p) ;                         
                     
                 }
                 }else if(i2==i1){
                         if(j1>=j2){
                             for(int k=j1;k<=j2;k++){
                             PointCarte p=new PointCarte(i1, k);
                             listp.add(p);
                             }
                             
                         }else if(j1<j2){
                             for(int k=j1;k<=j2;k++){
                             PointCarte p=new PointCarte(i1, k);
                             if(k>j2) listp.add(0,p);
                             else listp.add(p);
                         }
                         
                         }
                 }
             
             }
             System.out.println("-------------------LIGNE FINI----------------------------------");
             PointCarte[] customs = new PointCarte[listp.size()];
            
         return listp.toArray(customs);
    }
     
   static int  aroundN(double n,int size,int k,int first,int last){
        int plusgrand=(int)n;
        double intn=(int)n;
        if((n-intn)>0 && (k<size && k>0) && n<last ) 
            plusgrand=(int)n+1;
        else 
            if(k==0)
               plusgrand=first ;
            else if(k==size)plusgrand=last;
    
        return plusgrand;
    }
    
    public static int tileDistance(int i0,int j0,int i1,int j1){
        Object[] l = getLigne(i0, j0, i1, j1);
        if (l != null) {
            return l.length;
        } else {
            return 0;
        }
    }
    
public static double distance(int i0,int j0,int i1,int j1,int tilesize){
        double x0=(j0*tilesize)+(tilesize/2);
        double y0=(i0*tilesize)+(tilesize/2);
        double y1=(i1*tilesize)+(tilesize/2);
        double x1=(j1*tilesize)+(tilesize/2);
        double x1x0=x1-x0;
        double y1y0=y1-y0;
       // x1x0=x1x0*x1x0;
       // y1y0=y1y0*y1y0;
       double inch= 1.0d/tilesize;
        double dist=Math.sqrt(Math.pow(y1y0,2)+Math.pow(x1x0,2));
    return dist*inch;
    
    
    }    


    public PointCarte[] getLigne(Terrain p0, Terrain p1) {

            return getLigne(p0.getI(), p0.getJ(), p1.getI(), p1.getJ());

    }

    private void elaborationFileLine(int i, String line) {
        char[] l = line.toCharArray();
        char c1='x';
        char c2='x';
        int h=1;
        do{
            
            int j=(h-1)/2;
            if(h<(l.length)) c1=l[h];
            else c1='q';
            if((h+1)<(l.length))  c2=l[h+1];
            else c2='q';
            
            if(j<CARTE_SIZE_J && i<CARTE_SIZE_I){
                if (c1 == 'o' && c2 == 'o') {
                    int n=((int)(Math.random()*5))+1;
                   // System.out.println("--->"+n);
                   terrain[i][j] =  new Haie(i, j,n);
                   
                }
                else
                if (c1 == 'm' && c2 == '7') {
                    terrain[i][j] = new GrosMur(i, j,-90);
                    terrain[i][j].setOrientation(Piece.Direction.E);
                }else
                if (c1 == 'm' && c2 == '5') {
                    terrain[i][j] = new GrosMur(i, j,-180);
                    terrain[i][j].setOrientation(Piece.Direction.S);
                } 
                else
                if (c1 == 'm' && c2 == '1') {
                    terrain[i][j] = new GrosMur(i, j);
                } 
                else                    
                if (c1 == 'm' && c2 == '3') {
                    terrain[i][j] = new GrosMur(i, j,90);
                    terrain[i][j].setOrientation(Piece.Direction.W);
                } 
                else                    
                if (c1== ' ' && c2 == ' ') {
                    terrain[i][j] = new Terrain(i,j);
                }
                else
                if (c1 == 's' && c2 == 's') {
                    terrain[i][j] = new Street(i, j);
                } 
                else
                if (c1 == 's' && c2 == '1') {
                    terrain[i][j] = new StreetBorder(i, j);
                } 
                else
                if (c1 == 's' && c2 == '3') {
                    terrain[i][j] = new StreetBorder(i, j,90);
                    terrain[i][j].setOrientation(Piece.Direction.W);
                }                 
                else 
                if (c1 == 's' && c2 == '7') {
                    terrain[i][j] = new StreetBorder(i, j,-90);
                    terrain[i][j].setOrientation(Piece.Direction.E);
                }                 
                else    
                if (c1 == 's' && c2 == '5') {
                    terrain[i][j] = new StreetBorder(i, j,-180);
                    terrain[i][j].setOrientation(Piece.Direction.S);
                }
                else                    
                if (c1 == 'F' && c2 == '7') {
                    terrain[i][j] = new Fenetre(i, j,-90);
                    terrain[i][j].setOrientation(Piece.Direction.E);
                } 
                else
                if (c1 == 'F' && c2 == '3') {
                    terrain[i][j] = new Fenetre(i, j,90);
                    terrain[i][j].setOrientation(Piece.Direction.W);
                } 
                else
                if (c1 == 'F' && c2 == '1') {
                    terrain[i][j] = new Fenetre(i, j);
                } 
                else    
                if (c1 == 'F' && c2 == '5') {
                    terrain[i][j] = new Fenetre(i, j,-180);
                    terrain[i][j].setOrientation(Piece.Direction.S);
                } 
                else    
                if (c1 == 'P' && c2 == '1') {
                    terrain[i][j] = new Porte(i, j);
                } 
                else
                if (c1 == 'P' && c2 == '7') {
                    terrain[i][j] = new Porte(i, j,-90);
                    terrain[i][j].setOrientation(Piece.Direction.E);
                } 
                else    
                if (c1 == 'P' && c2 == '5') {
                    terrain[i][j] = new Porte(i, j,-180);
                    terrain[i][j].setOrientation(Piece.Direction.S);
                } 
                else    
                if (c1 == 'P' && c2 == '3') {
                    terrain[i][j] = new Porte(i, j,90);
                    terrain[i][j].setOrientation(Piece.Direction.W);
                } 
                else    
                if (c1 == 'T' && c2 == '1') {
                    terrain[i][j] = new Arbre(i, j);
                }else 
                if (c1 == 'T' && c2 == '2') {
                    terrain[i][j] = new Arbre2(i, j);
                } else 
                if (c1 == 'T' && c2 == '3') {
                    terrain[i][j] = new Arbre3(i, j);
                } else 
                if (c1 == 'T' && c2 == '4') {
                    terrain[i][j] = new Arbre4(i, j);
                } else    
                if (c1 == 'i' || c2 == 'i') {
                    terrain[i][j] = new Interior(i, j);
                } 
                else    
                if (c1 == 'a' && c2 == '1') {
                    terrain[i][j] = new AngleGrosMur (i, j);
                }else
                if (c1 == 'a' && c2 == '2') {
                    terrain[i][j] = new AngleGrosMur (i, j,90);
                    terrain[i][j].setOrientation(Piece.Direction.W);
                }else                
                if (c1 == 'a' && c2 == '3') {
                    terrain[i][j] = new AngleGrosMur (i, j,-90);
                    terrain[i][j].setOrientation(Piece.Direction.E);
                }else   
                if (c1 == 'a' && c2 == '4') {
                   terrain[i][j] = new AngleGrosMur (i, j,180);
                   terrain[i][j].setOrientation(Piece.Direction.S);
                }                
                else terrain[i][j] = new Terrain(i, j);
                
                
            }
            h=h+2;
        }while(c1!='q' && c2!='q' && i<CARTE_SIZE_I);

    }

    public static void main(String[] args) throws IOException {

        int i0 = 4, i1 = 8, j0 = 2, j1 = 7;
        Carte c = new Carte("src/mapDesert.txt");
        PointCarte[] p = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < p.length; n++) {
            System.out.println("(" + p[n].getI() + "," + p[n].getJ() + ")");
        }
        System.out.println(c.getMapTxt());

    }

    

    public void desplacementSoldat(Piece s,int i,int j){
        s.setI(i);
        s.setJ(j);
        terrain[i][j].putPiece(s);
    }
    
    public   PointCarte  validerLeRoute(BaseAction act){
        PointCarte ligne[]=getLigne(act.getI0(), act.getJ0(), act.getI1(), act.getJ1());
        PointCarte product=null;
        Terrain t=null;
        for (int i = 0; i < ligne.length; i++) {
            PointCarte c = ligne[i];
             t=terrain[c.getI()][c.getJ()];
            if((t.getType()==Terrain.FENETRE && i>0 && !t.accesibleFrom(ligne[i-1] )  ) ||
                  (t.getType()==Terrain.FENETRE && i==0 && !t.accesibleFrom(ligne[1])) ||  t.getType()==Terrain.GROSMUR ||
                    t.getType()==Terrain.MURBAS)  {
                product=ligne[i];
                break;
              
            }
            

        }  
        System.out.println("Obstacle "+t);
        return product;   
    }
    @Override
    public boolean isAnimOn() {
       return false;
    }
    public void setAnimOn(boolean value){
        return ;
    }

    @Override
    public void refreshGraficCarte() {
        return;
    }

    

}
