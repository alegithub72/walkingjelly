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
import jeu.patrouille.coeur.armes.GeneriqueArme;
import jeu.patrouille.coeur.armes.exceptions.LoadMagazineFiniException;
import jeu.patrouille.coeur.armes.exceptions.ModeDeFeuException;
import jeu.patrouille.coeur.armes.exceptions.PaDeMagazineException;
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
       assertEquals(GeneriqueArme.MODE_FEU_SA ,  ak.getMF());
       assertEquals( 3 ,ak.fireWeapon());
       assertEquals(29, ak.shotRemain());
   }
   
   @Test 
   public void testPorteBU() throws ModeDeFeuException,LoadMagazineFiniException{
       ak.changeModeFeu(GeneriqueArme.MODE_FEU_BU);
       assertEquals(GeneriqueArme.MODE_FEU_BU,ak.getMF());
       assertEquals(5,ak.fireWeapon());
       assertEquals(25, ak.shotRemain());
   
   }
   @Test
   public void testPorteSS()throws ModeDeFeuException,LoadMagazineFiniException{
        ex.expect(ModeDeFeuException.class);
        ak.changeModeFeu(GeneriqueArme.MODE_FEU_SS);
        ak.fireWeapon();
        assertEquals(29, ak.shotRemain());
       
   }
   @Test
   public void testPorteFA()throws ModeDeFeuException,LoadMagazineFiniException{

        ak.changeModeFeu(GeneriqueArme.MODE_FEU_FA);
        ak.fireWeapon();
        assertEquals(20, ak.shotRemain());
       
   }   
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
