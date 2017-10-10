/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.armes.BenelliM3;
import jeu.patrouille.coeur.equipments.armes.exceptions.ImpossibleRechargeArmeException;
import jeu.patrouille.coeur.equipments.armes.exceptions.IncompatibleMagazineException;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


/**
 *
 * @author appleale
 */
public class BenelliM3Test {
    BenelliM3 b;

    
    @Rule
    public ExpectedException ex= ExpectedException.none();
    
    public BenelliM3Test() throws PaDeMagazineException,ImpossibleRechargeArmeException,IncompatibleMagazineException,ImpossibleRechargeArmeException{
           b=new BenelliM3();
   
           
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
    public void testNom(){
          assertEquals("Benelli M3",b.getNom());
    }
    @Test 
    public void testShotMFCourt() throws ModeDeFeuException{
        assertEquals(1,b.hitsNumMF(1));
    }
    @Test
    public void testShotNumMFMed() throws ModeDeFeuException{
        assertEquals( 1,b.hitsNumMF(6));
    
    }
    
    @Test
    public void testShotNumFA() throws ModeDeFeuException{         
        ex.expect(ModeDeFeuException.class);
        b.changeModeFeu(GeneriqueArme.FeuMode.PA);
        b.hitsNumMF(1);
       
        
    }
    @Test
    public void testCapaciteMunition(){
    
        assertEquals(7,b.shotRemain());
    }
    
    @Test
    public void testPorteModifier(){
    assertEquals(1,b.porteModifier(2));
    }
    @Test
    public void testPorteModifierMed(){
    assertEquals(0,b.porteModifier(6));
    }
    @Test
    public void testPorteModifierLong(){
    assertEquals(-1,b.porteModifier(64));
    }
    @Test 
    public void testFireAShot() throws ModeDeFeuException,LoadMagazineFiniException{
    b.feuArme();
    assertEquals(6,b.shotRemain());
    }

    @Test 
    public void testChargeArme() throws ModeDeFeuException,LoadMagazineFiniException,IncompatibleMagazineException,ImpossibleRechargeArmeException,PaDeMagazineException{
       b.feuArme();
      
    assertEquals(6,b.shotRemain());   
    assertEquals (6,b.rechargeArme());
    assertEquals(7,b.shotRemain());
    
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
