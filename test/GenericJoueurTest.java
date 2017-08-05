/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.joeurs.KeyboardJoeur;

/**
 *
 * @author appleale
 */
public class GenericJoueurTest {
    GeneriqueJoeurs mj;
    public GenericJoueurTest() {
        mj=new KeyboardJoeur(GeneriqueJoeurs.JOEUR_US  ,null);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void testDice10(){
          System.out.println("\nINIZIO DICE 10");
        int n=mj.dice(10);
        System.out.print(n+",");
        assertTrue((n<=10));
        for(int i=0;i<100;i++){
        n=mj.dice(10);
        System.out.print(n+",");
        assertTrue((n<=10));
        }
      
    }
    @Test
    public void testDice6(){
        System.out.println("\n INIZIO DICE 6") ;
    int n=mj.dice(6);
    System.out.print(n+",");
    assertTrue(n<=6);
    for(int j=0;j<100;j++){
        n=mj.dice(6);
        System.out.print(n+",");
        assertTrue(n<=6);
    }
       ;
    
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
