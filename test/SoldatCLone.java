/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jeu.patrouille.coeur.pieces.GeneriquePiece;
import jeu.patrouille.coeur.pieces.Piece;
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
public class SoldatCLone {
    Piece s;
    public SoldatCLone() {
        s=new Soldat("ROSSI", "Mario",1, 2,3, 4, 5, 6, null, 8, 9, Piece.Direction.S,null);
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
public void testCloneSoldat(){
    GeneriquePiece p=s.clonerPiece();
    assertEquals(s.toStringSimple(),p.toStringSimple());//TODO increase of  variable on the tostring method
}


}
