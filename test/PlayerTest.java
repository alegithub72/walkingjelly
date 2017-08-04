/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;
import jeu.patrouille.coeur.actions.BaseAction;
import jeu.patrouille.coeur.joeurs.GeneriqueJoeurs;
import jeu.patrouille.coeur.joeurs.KeyboardJoeur;
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
public class PlayerTest {

    GeneriqueJoeurs gj;
    String pt;

    public PlayerTest() {
        gj = new KeyboardJoeur(null);
        pt = gj.getPtSt();
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
    public void testPatternCommand() {
        gj.setInCommand(new ByteArrayInputStream("m12,13;56,67".getBytes()));
        assertEquals("m12,13;56,67", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand1() {
        gj.setInCommand(new ByteArrayInputStream("m1,13;2,3".getBytes()));
        assertEquals("m1,13;2,3", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand2() {
        gj.setInCommand(new ByteArrayInputStream("m12,3;12,3".getBytes()));
        assertEquals("m12,3;12,3", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand3() {
        gj.setInCommand(new ByteArrayInputStream("m12,3;12,3".getBytes()));
        assertEquals("m12,3;12,3", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand4() {
        gj.setInCommand(new ByteArrayInputStream("m120,130;120,130".getBytes()));
        assertEquals("m120,130;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand5() {
        gj.setInCommand(new ByteArrayInputStream("m1,1;120,130".getBytes()));
        assertEquals("m1,1;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand6() {
        gj.setInCommand(new ByteArrayInputStream("m12,13;120,130".getBytes()));
        assertEquals("m12,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand7() {
        gj.setInCommand(new ByteArrayInputStream("m125,13;120,130".getBytes()));
        assertEquals("m125,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand8() {
        gj.setInCommand(new ByteArrayInputStream("m0,0;120,130".getBytes()));
        assertEquals("m0,0;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand9() {
        gj.setInCommand(new ByteArrayInputStream("f12,13;120,130".getBytes()));
        assertEquals("f12,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand10() {
        gj.setInCommand(new ByteArrayInputStream("f1,13;120,130".getBytes()));
        assertEquals("f1,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand11() {
        gj.setInCommand(new ByteArrayInputStream("f12,3;120,130".getBytes()));
        assertEquals("f12,3;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand12() {
        gj.setInCommand(new ByteArrayInputStream("f120,130;120,130".getBytes()));
        assertEquals("f120,130;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand13() {
        gj.setInCommand(new ByteArrayInputStream("f1,1;120,130".getBytes()));
        assertEquals("f1,1;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand14() {
        gj.setInCommand(new ByteArrayInputStream("f1,1;120,130".getBytes()));
        assertEquals("f1,1;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand15() {
        gj.setInCommand(new ByteArrayInputStream("f12,13;120,130".getBytes()));
        assertEquals("f12,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand16() {
        gj.setInCommand(new ByteArrayInputStream("f125,13;120,130".getBytes()));
        assertEquals("f125,13;120,130", gj.getCommand().toString());
    }

    @Test
    public void testPatternCommand18() {
        gj.setInCommand(new ByteArrayInputStream("f0,0;120,130".getBytes()));
        assertEquals("f0,0;120,130", gj.getCommand().toString());

    }

    @Test
    public void testPatternErrorCommand() {
        assertFalse(Pattern.matches(pt, ""));
        assertFalse(Pattern.matches(pt, "m,;120,130"));
        assertFalse(Pattern.matches(pt, "m1000,13;120,130"));
        assertFalse(Pattern.matches(pt, "m;120,130"));
        assertFalse(Pattern.matches(pt, "f,13;120,130"));
        assertFalse(Pattern.matches(pt, "m1,13000;120,130"));
        assertFalse(Pattern.matches(pt, "mé,&;120,130"));
        assertFalse(Pattern.matches(pt, "f0000,13;120,130"));
        assertFalse(Pattern.matches(pt, "f,13000;120,130"));

    }

    @Test
    public void testGetCoord() {
        gj.setInCommand(new ByteArrayInputStream("m123,789;345,7".getBytes()));
        assertEquals(BaseAction.MARCHE, gj.getCommand().getType());

    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
