/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import static org.hamcrest.CoreMatchers.startsWith;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
     * Test of parsePlayTime method, of class PlaySession.
     */
    @Test
    public void testParsePlayTimeGoodInput() {
        System.out.println("Testing parsing playtime from string - Good input");
        String timeStr = "1.1";
        double expResult = 1.1;
        double result = PlaySession.parsePlayTime(timeStr);
        assertEquals(expResult, result, 0.0);
    }

    @Rule
    public ExpectedException parsePlayTimeRule = ExpectedException.none();

    @Test
    public void testParsePlayTimeBadInput() {
        System.out.println("Testing parsing playtime from string - Bad input");
        parsePlayTimeRule.expect(IllegalArgumentException.class);
        parsePlayTimeRule.expectMessage(
                startsWith("Not able to parse play time from "));
        PlaySession.parsePlayTime("One Point One");
    }

    /**
     * Test of parseDateTime method, of class PlaySession.
     */
    @Test
    public void testParseDateTimeEmpty() {
        System.out.println(
                "Testing parsing date time from string - Empty input");
        String dateStr = "";
        
        
        LocalDate result = PlaySession.parseDateTime(dateStr);
        
        assertNull(result);

    }

    @Test
    public void testParseDateTimeGood() {
        System.out.println(
                "Testing parsing date time from string - 2000/12/31");
        String dateStr = "2000/12/31";
        DateTime expResult = 
                DateTime.parse(dateStr, PlaySession.SESSION_DATE_FORMAT);
        LocalDate result = PlaySession.parseDateTime(dateStr);
        assertEquals(expResult, result);
    }

    @Rule
    public ExpectedException parseDateTimeRule = ExpectedException.none();

    @Test
    public void testParseDateTimeBad() {
        System.out.println(
                "Testing parsing date time from string - Bad input");
        parseDateTimeRule.expect(IllegalArgumentException.class);
        // test fails if you put the rest of the matching message in
        parseDateTimeRule.expectMessage(startsWith("Not able to parse "));
        PlaySession.parsePlayTime("Today");
    }
}
