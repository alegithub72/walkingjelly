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
import jeu.patrouille.coeur.equipments.armes.AK74;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.coeur.equipments.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.equipments.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.equipments.armes.exceptions.PaDeMagazineException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author appleale
 */
public class AK74Test {
    
    @Rule
    public ExpectedException ex=ExpectedException.none();
    AK74 ak;
    public AK74Test() throws PaDeMagazineException{
        ak=new AK74();
        ak.loadMagazine();
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
   public void testPorte() throws ModeDeFeuException,LoadMagazineFiniException{
       assertEquals(GeneriqueArme.FeuMode.SA ,  ak.getArmeFeuModel());
       assertEquals( 3 ,ak.feuArme(50));
       assertEquals(29, ak.shotRemain());
   }
   
   @Test 
   public void testPorteBU() throws ModeDeFeuException,LoadMagazineFiniException{
       ak.changeModeFeu(GeneriqueArme.FeuMode.RA);
       assertEquals(GeneriqueArme.FeuMode.RA,ak.getArmeFeuModel());
       assertEquals(5,ak.feuArme(50));
       assertEquals(25, ak.shotRemain());
   
   }
   @Test
   public void testPorteSS()throws ModeDeFeuException,LoadMagazineFiniException{
        ex.expect(ModeDeFeuException.class);
        ak.changeModeFeu(GeneriqueArme.FeuMode.SC);
        ak.feuArme(50);
        assertEquals(30, ak.shotRemain());
       
   }
   @Test
   public void testPorteFA()throws ModeDeFeuException,LoadMagazineFiniException{

        ak.changeModeFeu(GeneriqueArme.FeuMode.PA);
        ak.feuArme(50);
        assertEquals(20, ak.shotRemain());
       
   }   
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
