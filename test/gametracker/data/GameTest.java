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

        @Rule
    public ExpectedException parsePlatformBadRule = ExpectedException.none();

    @Test
    public void testParsePlatform_BadInput() {
        System.out.println("Testing parsing platform with bad input");
        String platformStr = "SquirrelEntertainer5";

        parseYearFiveRule.expect(IllegalArgumentException.class);
        parseYearFiveRule.expectMessage(
                startsWith("Not able to parse platform from"));
        Game.parsePlatform(platformStr);

    }
    
    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Unknown() {
        System.out.println("Parsing Platform Unknown");
        String platformStr = "Unknown";
        Game.Platform expResult = Game.Platform.Unknown;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Playstation() {
        System.out.println("Parsing Platform Playstation");
        String platformStr = "Playstation";
        Game.Platform expResult = Game.Platform.Playstation;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Playstation_2() {
        System.out.println("Parsing Platform Playstation_2");
        String platformStr = "Playstation_2";
        Game.Platform expResult = Game.Platform.Playstation_2;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Playstation_3() {
        System.out.println("Parsing Platform Playstation_3");
        String platformStr = "Playstation_3";
        Game.Platform expResult = Game.Platform.Playstation_3;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_GameBoy_Advance() {
        System.out.println("Parsing Platform GameBoy_Advance");
        String platformStr = "GameBoy_Advance";
        Game.Platform expResult = Game.Platform.GameBoy_Advance;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_DS() {
        System.out.println("Parsing Platform DS");
        String platformStr = "DS";
        Game.Platform expResult = Game.Platform.DS;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_DS_3DS() {
        System.out.println("Parsing Platform DS_3DS");
        String platformStr = "DS_3DS";
        Game.Platform expResult = Game.Platform.DS_3DS;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Super_Nintendo() {
        System.out.println("Parsing Platform Super_Nintendo");
        String platformStr = "Super_Nintendo";
        Game.Platform expResult = Game.Platform.Super_Nintendo;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Nintendo_64() {
        System.out.println("Parsing Platform Nintendo_64");
        String platformStr = "Nintendo_64";
        Game.Platform expResult = Game.Platform.Nintendo_64;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_GameCube() {
        System.out.println("Parsing Platform GameCube");
        String platformStr = "GameCube";
        Game.Platform expResult = Game.Platform.GameCube;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Wii() {
        System.out.println("Parsing Platform Wii");
        String platformStr = "Wii";
        Game.Platform expResult = Game.Platform.Wii;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_Wii_U() {
        System.out.println("Parsing Platform Wii_U");
        String platformStr = "Wii_U";
        Game.Platform expResult = Game.Platform.Wii_U;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_PC_Other() {
        System.out.println("Parsing Platform PC_Other");
        String platformStr = "PC_Other";
        Game.Platform expResult = Game.Platform.PC_Other;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePlatform method, of class Game.
     */
    @Test
    public void testParsePlatform_PC_Steam() {
        System.out.println("Parsing Platform PC_Steam");
        String platformStr = "PC_Steam";
        Game.Platform expResult = Game.Platform.PC_Steam;
        Game.Platform result = Game.parsePlatform(platformStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseYear method, of class Game.
     */
    @Rule
    public ExpectedException parseYearThreeRule = ExpectedException.none();

    @Test
    public void testParseYear_ThreeDigit() {
        System.out.println("Testing parsing year with a string fewer than "
                + "four digits");
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
        System.out.println("Testing parsing year with a string more than "
                + "four digits");
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
