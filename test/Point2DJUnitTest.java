/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */

import jeu.patrouille.fx.board.FXCarte;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author appleale
 */
public class Point2DJUnitTest {
    
    public Point2DJUnitTest() {
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
    public void point2DTes(){
    
    System.out.println(""+FXCarte.angleRotation(250, 300, 550, 250));
        System.out.println(""+FXCarte.angleRotation(250, 300, 550, 300));
        System.out.println(""+FXCarte.angleRotation(250, 300, 450, 450));
         System.out.println(""+FXCarte.angleRotation(250, 300, 250, 450));
           System.out.println(""+FXCarte.angleRotation(250, 300, 200, 500));
    System.out.println(""+FXCarte.angleRotation(250, 300, 0, 450));

    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
