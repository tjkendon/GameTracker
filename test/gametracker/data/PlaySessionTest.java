/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import static org.hamcrest.CoreMatchers.startsWith;
import org.joda.time.DateTime;
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
 * @author tjkendon
 */
public class PlaySessionTest {
    
    public PlaySessionTest() {
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
     * Test of hashCode method, of class PlaySession.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        PlaySession instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class PlaySession.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        PlaySession instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parsePlayTime method, of class PlaySession.
     */
    @Test
    public void testParsePlayTimeGoodInput() {
        System.out.println("parsePlayTime - Good input");
        String timeStr = "1.1";
        double expResult = 1.1;
        double result = PlaySession.parsePlayTime(timeStr);
        assertEquals(expResult, result, 0.0);
    }
    
        
    @Rule
    public ExpectedException parsePlayTimeRule = ExpectedException.none();
    
  @Test
    public void testParsePlayTimeBadInput() {
        
        parsePlayTimeRule.expect(IllegalArgumentException.class);
        parsePlayTimeRule.expectMessage(startsWith("Not able to parse play time from "));
        PlaySession.parsePlayTime("One Point One");
    }

    /**
     * Test of parseDateTime method, of class PlaySession.
     */
    @Test
    public void testParseDateTime() {
        System.out.println("parseDateTime");
        String dateStr = "";
        DateTime expResult = null;
        DateTime result = PlaySession.parseDateTime(dateStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
