/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.grafic;


import jeu.patrouille.coeur.actions.BaseAction;

/**
 *
 * @author Alessio Sardaro
 */
public interface GraficCarteInterface  {
   public  void  play(BaseAction  b);
    public boolean isAnimOn() ;
    public void setAnimOn(boolean value);
    public void refreshGraficCarte();
    public void reMountFXCarteMenuItemsAndScroll();
}
