/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import static org.hamcrest.CoreMatchers.startsWith;
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
public class GameTest {
    
    public GameTest() {
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
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform() {
        System.out.println("parsePlatform");
        String platformStr = "";
        Game.Platform expResult = null;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseYear method, of class Game.
     */
    
        @Rule
    public ExpectedException parseYearThreeRule = ExpectedException.none();

    
    @Test
    public void testParseYear_ThreeDigit() {
        System.out.println("Testing parsing year with a string fewer than " +
                 "four digits");
        String yearStr = "999";
        
        parseYearThreeRule.expect(IllegalArgumentException.class);
        parseYearThreeRule.expectMessage(
                startsWith("Not able to parse year from"));
        Game.parseYear(yearStr);
        
        
        
    }
    
    @Rule
    public ExpectedException parseYearFiveRule = ExpectedException.none();
    
    @Test
    public void testParseYear_FiveDigit() {
        System.out.println("Testing parsing year with a string more than " +
                 "four digits");
        String yearStr = "10001";
        
        parseYearFiveRule.expect(IllegalArgumentException.class);
        parseYearFiveRule.expectMessage(
                startsWith("Not able to parse year from"));
        Game.parseYear(yearStr);
        
    }
    
    @Rule
    public ExpectedException parseYearBadRule = ExpectedException.none();
    
    @Test
    public void testParseYear_BadInput() {
        System.out.println("Testing parsing year with a bad input string");
        String yearStr = "Today";
        
        parseYearBadRule.expect(IllegalArgumentException.class);
        parseYearBadRule.expectMessage(
                startsWith("Not able to parse year from"));
        Game.parseYear(yearStr);
        
    }
    
    @Test
    public void testParseYear_GoodInput() {
        System.out.println("Testing parsing year with a correct input string");
        String yearStr = "2000";
        int expected = 2000;
        int result = Game.parseYear(yearStr);
        assertEquals(expected, result);
        
    }
    
    
    
}
