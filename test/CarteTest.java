/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import javafx.geometry.Point2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import jeu.patrouille.coeur.Carte;
import jeu.patrouille.coeur.terrains.PointCarte;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class CarteTest {

    Carte c;

    public CarteTest() throws IOException {
        c = new Carte("src/mapDesert.txt");
        c.loadMap();
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
    public void testLOSinfinity_31() {
        //eligne (2 ,2 ) , (9 ,7 )
        int i0 = 2, j0 = 2, i1 = 9, j1 = 7;
        double a=-4,b=5;
        assertTrue((a/b)<0);
        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        
        assertEquals(9, line[line.length - 1].getI());
        assertEquals(7, line[line.length - 1].getJ());
    }

    
    @Test
    public void testLOSinfinity_1() {
        ///geligne (30 ,20 ) , (26 ,17 )
        int i0 = 30, j0 = 20, i1 = 26, j1 = 17;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(26, line[line.length - 1].getI());
        assertEquals(17, line[line.length - 1].getJ());
    }   
    
    @Test
    public void testLOSinfinity0() {
        //geligne (2 ,3 ) , (7 ,5 )
        int i0 = 30, j0 = 20, i1 = 26, j1 = 20;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(26, line[line.length - 1].getI());
        assertEquals(20, line[line.length - 1].getJ());
    }    

    @Test
    public void testLOSinfinity() {
        //geligne (2 ,3 ) , (7 ,5 )
        int i0 = 2, j0 = 3, i1 = 7, j1 = 5;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(7, line[line.length - 1].getI());
        assertEquals(5, line[line.length - 1].getJ());
    }
    
    
    
    @Test
    public void testLOSinfinity2() {
        //(2 ,3 ) , (1 ,4 )
        int i0 = 2, j0 = 3, i1 = 1, j1 = 4;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(1, line[line.length - 1].getI());
        assertEquals(4, line[line.length - 1].getJ());
    }    
    
    @Test
    public void testLOSinfinity3() {
        //    geligne (1 ,10 ) , (3 ,10 )   
        int i0 = 1, j0 = 10, i1 = 3, j1 = 10;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(3, line[line.length - 1].getI());
        assertEquals(10, line[line.length - 1].getJ());
    }      

 
    @Test
    public void testLOS12() {
        // geligne (30 ,21 ) , (27 ,18 )
        int i0 = 30, j0 = 21, i1 = 27, j1 = 18;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(27, line[line.length - 1].getI());
        assertEquals(18, line[line.length - 1].getJ());
    } 
    @Test
    public void testLOS1() {

        int i0 = 4, j0 = 2, i1 = 8, j1 = 7;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(8, line[line.length - 1].getI());
        assertEquals(7, line[line.length - 1].getJ());
    }

    @Test
    public void testLOS2() {

        int i0 = 30, j0 = 22, i1 = 27, j1 = 20;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(27, line[line.length - 1].getI());
        assertEquals(20, line[line.length - 1].getJ());
    }

    @Test
    public void testLOS3() {

        int i0 = 30, j0 = 22, i1 = 28, j1 = 25;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        for (int n = 0; n < line.length; n++) {
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }

        assertEquals(28, line[line.length - 1].getI());
        assertEquals(25, line[line.length - 1].getJ());
    }

    @Test
    public void testLOS4() {

        int i0 = 0, j0 = 0, i1 = 1, j1 = 1;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        assertNotNull(line);
        assertTrue(line.length > 0);
        for (int n = 0; n < line.length; n++) {
            assertNotNull(line[n]);
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(1, line[line.length - 1].getI());
        assertEquals(1, line[line.length - 1].getJ());
    }
        @Test
    public void testLOS6() {

        int i0 = 0, j0 = 0, i1 = 0, j1 = 1;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        assertNotNull(line);
        assertTrue(line.length > 0);
        for (int n = 0; n < line.length; n++) {
            assertNotNull(line[n]);
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(0, line[line.length - 1].getI());
        assertEquals(1, line[line.length - 1].getJ());
    }    
        @Test
    public void testLOS7() {

        int i0 = 0, j0 = 0, i1 = 0, j1 = 9;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        assertNotNull(line);
        assertTrue(line.length > 0);
        for (int n = 0; n < line.length; n++) {
            assertNotNull(line[n]);
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(0, line[line.length - 1].getI());
        assertEquals(9, line[line.length - 1].getJ());
    }    
        @Test
    public void testLOS10() {

        int i0 = 0, j0 = 0, i1 = 9, j1 = 0;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        assertNotNull(line);
        assertTrue(line.length > 0);
        for (int n = 0; n < line.length; n++) {
            assertNotNull(line[n]);
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(0, line[0].getI());
        assertEquals(0, line[0].getJ());
        assertEquals(9, line[line.length - 1].getI());
        assertEquals(0, line[line.length - 1].getJ());
    }     
        @Test
    public void testLOS11() {

        int i0 = 3, j0 = 9, i1 = 3, j1 = 0;

        PointCarte[] line = null;
        line = c.getLigne(i0, j0, i1, j1);
        assertNotNull(line);
        assertTrue(line.length > 0);
     
        for (int n = 0; n < line.length; n++) {
            assertNotNull(line[n]);
            System.out.println("(" + line[n].getI() + "," + line[n].getJ() + ")");
        }
        assertEquals(3, line[line.length - 1].getI());
        assertEquals(0, line[line.length - 1].getJ());
    }      
    @Test
    public void teqtMaploader() throws IOException {
        String p = "q{1}\\R?Q?";
        String mapLines = "(.*" + p + ")+";
        String fileMap = "1x50q\n12345678901234567890123456789012345678901234567890q"
                + "                              sssssssss           q\n"
                + "qQ";
        c.setMmapDelimiter(p);
        //assertTrue(Pattern.matches(p, fileMap));

        c.setMapInput(new ByteArrayInputStream(fileMap.getBytes()));
        c.loadMap();
        assertTrue(Pattern.matches(mapLines, c.getMapTxt()));

    }
    @Test 
    public void testDistance(){
        double d=Carte.distance(0, 0, 10, 10, FXCarte.TILE_SIZE);
        Point2D p0=new Point2D(25, 25);
        Point2D p1=new Point2D((10*FXCarte.TILE_SIZE)+(FXCarte.TILE_SIZE/2)
                ,(10*FXCarte.TILE_SIZE)+(FXCarte.TILE_SIZE/2));
        assertEquals(p1.distance(p0)*FXCarte.INCHxPIXEL,d,0d);
    
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
