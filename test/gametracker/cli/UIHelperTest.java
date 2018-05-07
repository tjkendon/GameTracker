/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tjkendon
 */
public class UIHelperTest {
    
    public UIHelperTest() {
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
     * Test of checkFor method, of class UIHelper.
     */
    @Test
    public void testCheckForMatch() {
        System.out.println("Testing Check For with Match");
        String arg = "B";
        String[] matches = {"A", "B", "C"};
        
        boolean result = UIHelper.checkFor(arg, matches);
        assertTrue(result);
    }
    
    @Test
    public void testCheckForNoMatch() {
        System.out.println("Testing Check For with no Match");
        String arg = "X";
        String[] matches = {"A", "B", "C"};
        
        boolean result = UIHelper.checkFor(arg, matches);
        assertFalse(result);
    }
    
    @Test
    public void testCheckForNoArgs() {
        System.out.println("Testing Check For with no Match");
        String arg = "B";
        String[] matches = {};
        
        boolean result = UIHelper.checkFor(arg, matches);
        assertFalse(result);
    }
    
}
