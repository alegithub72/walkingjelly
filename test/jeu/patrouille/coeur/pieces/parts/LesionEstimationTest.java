/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.coeur.pieces.parts;

import jeu.patrouille.coeur.pieces.Soldat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author appleale
 */
public class LesionEstimationTest {
    
    public LesionEstimationTest() {
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

    /**
     * Test of getLesion method, of class LesionEstimation.
     */
    @Test
    public void testGetLesion() {
        System.out.println("getLesion");
        int n = 0;
        int m = 0;
        int turn = 0;

        LesionEstimation instance = new LesionEstimation();
        Lesion expResult = new Lesion(Corp.CorpParts.Tete, Lesion.Degre.CRITIQUE, -10, Soldat.Statut.CRITIQUE, turn);
        Lesion result = instance.getLesion(1, 0, turn);
        assertEquals(expResult.location, result.location);
        assertEquals(expResult.gravite, result.gravite);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    @Test
    public void testGetLesion1() {
        System.out.println("getLesion");
        int n = 0;
        int m = 0;
        int turn = 0;

        LesionEstimation instance = new LesionEstimation();
        Lesion expResult = new Lesion(Corp.CorpParts.Thorax, Lesion.Degre.CRITIQUE, -10, Soldat.Statut.CRITIQUE, turn);
        Lesion result = instance.getLesion(2, 0, turn);
        assertEquals(expResult.location, result.location);
         result = instance.getLesion(3, 0, turn);
        assertEquals(expResult.location, result.location);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }    
   
    @Test
    public void testGetLesion2() {
        System.out.println("getLesion");
        int n = 0;
        int m = 0;
        int turn = 0;

        LesionEstimation instance = new LesionEstimation();
        Lesion expResult = new Lesion(Corp.CorpParts.Ventre, Lesion.Degre.CRITIQUE, -10, Soldat.Statut.CRITIQUE, turn);
        Lesion result = instance.getLesion(4, 0, turn);
        assertEquals(expResult.location, result.location);
         result = instance.getLesion(5, 0, turn);
        assertEquals(expResult.location, result.location);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }      
}
