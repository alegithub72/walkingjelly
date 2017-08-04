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
import jeu.patrouille.coeur.armes.AK74;
import jeu.patrouille.coeur.armes.ArmeGenerique;
import jeu.patrouille.coeur.armes.ModeDeFeuException;
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
    public AK74Test() {
        ak=new AK74();
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
   public void testPorte() throws ModeDeFeuException{
       assertEquals(ArmeGenerique.MODE_FEU_SA ,  ak.getMF());
       assertEquals( 3 ,ak.fireWeapon());
   }
   
   @Test 
   public void testPorteBU() throws ModeDeFeuException{
       ak.changeModeFeu(ArmeGenerique.MODE_FEU_BU);
       assertEquals(ArmeGenerique.MODE_FEU_BU,ak.getMF());
       assertEquals(5,ak.fireWeapon());
   
   }
   @Test
   public void testPorteSS()throws ModeDeFeuException{
        ex.expect(ModeDeFeuException.class);
        ak.changeModeFeu(ArmeGenerique.MODE_FEU_SS);
        ak.fireWeapon();
    
       
   }
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
