/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.HashSet;
import java.util.Set;
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
public class GameSetTest {
    
    public GameSetTest() {
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
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_AllGames() {
        System.out.println("Testing getting all games from a GameSet");
        GameSet instance = new GameSet();
        Game tg1_2000 = new Game("TG1", Game.Platform.PC_Steam, 2000);
        Game tg1_2001 = new Game("TG1", Game.Platform.PC_Steam, 2001);
        Game tg2 = new Game("TG2", Game.Platform.PC_Steam, 2001);
        Set<Game> expResult = new HashSet<>();
        expResult.add(tg1_2000);
        instance.addGame(tg1_2000);
        expResult.add(tg1_2001);
        instance.addGame(tg1_2001);
        expResult.add(tg2);
        instance.addGame(tg2);
        Set<Game> result = instance.getGames();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addGame method, of class GameSet.
     */
    @Test
    public void testAddGame() {
        System.out.println("Testing adding to GameSet and retreiving " +
                "with exact name");
        Game g = new Game("TG1", Game.Platform.PC_Steam, 2000);
        GameSet instance = new GameSet();
        instance.addGame(g);
        
        assertEquals(instance.getGame("TG1", Game.Platform.PC_Steam, 2000), g);
    }

    /**
     * Test of getGame method, of class GameSet.
     */
    
    @Rule
    public ExpectedException getGameNoCorrectRule = ExpectedException.none();
    
    @Test
    public void testGetGame_NoCorrect() {
        System.out.println("Testing getting from GameSet by name with no match");
        String gameName = "TG1";
        GameSet instance = new GameSet();
        
        getGameNoCorrectRule.expect(IllegalArgumentException.class);
        getGameNoCorrectRule.expectMessage(startsWith("Not able to find game"));
        
        instance.getGame(gameName);
    }

        @Rule
    public ExpectedException getGameMultiCorrectRule = ExpectedException.none();
    
    @Test
    public void testGetGame_MulitCorrect() {
        System.out.println("Testing getting from GameSet by name with " + 
                "more than 1 match");
        String gameName = "TG1";
        
        GameSet instance = new GameSet();
        
        instance.addGame(new Game(gameName, Game.Platform.PC_Steam, 2000));
        instance.addGame(new Game(gameName, Game.Platform.PC_Steam, 2001));
        
        getGameMultiCorrectRule.expect(IllegalArgumentException.class);
        getGameMultiCorrectRule.expectMessage(
                startsWith("Multiple games with name"));
        
        instance.getGame(gameName);
    }
    
    /**
     * Test of getGame method, of class GameSet.
     */
    @Test
    public void testGetGame_Exists() {
        System.out.println("Testing getting a game with all data matching");
        String gameName = "TG1";
        Game.Platform p = Game.Platform.PC_Steam;
        int year = 2000;
        GameSet instance = new GameSet();
        instance.addGame(new Game(gameName, p, year));
        Game expResult = new Game(gameName, p, year);
        Game result = instance.getGame(gameName, p, year);
        assertEquals(expResult, result);
    }
    
            @Rule
    public ExpectedException getGameNotMatchRule = ExpectedException.none();

    
        /**
     * Test of getGame method, of class GameSet.
     */
    @Test
    public void testGetGame_NotExists() {
        System.out.println("Testing getting a game with all data matching not" + 
                " matching");
        String gameName = "TG1";
        Game.Platform p = Game.Platform.PC_Steam;
        int year = 2000;
        GameSet instance = new GameSet();
        
        getGameNotMatchRule.expect(IllegalArgumentException.class);
        getGameNotMatchRule.expectMessage(
                startsWith("Not able to find game"));
        
        
        Game result = instance.getGame(gameName, p, year);
        
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_ByName() {
        System.out.println("Testing getting all games that match a given name");
        String gameName = "TG1";
        Game g1 = new Game(gameName, Game.Platform.PC_Steam, 2000);
        Game g2 = new Game(gameName, Game.Platform.PC_Steam, 2001);
        Game g3 = new Game("TG2", Game.Platform.PC_Steam, 2000);
        Game g4 = new Game("TG2", Game.Platform.PC_Steam, 2001);
        GameSet instance = new GameSet();
        
        
        instance.addGame(g1);
        instance.addGame(g2);
        instance.addGame(g3);
        instance.addGame(g4);
        
        Set<Game> expResult = new HashSet<>();
        expResult.add(g1);
        expResult.add(g2);
        
        Set<Game> result = instance.getGames(gameName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_GamePlatform() {
        System.out.println("Testing getting all games that match a given " +
                "Platform");
        Game.Platform platform = Game.Platform.DS;
        Game g1 = new Game("TG1", platform, 2000);
        Game g2 = new Game("TG1", Game.Platform.PC_Steam, 2001);
        Game g3 = new Game("TG2", Game.Platform.PC_Steam, 2000);
        Game g4 = new Game("TG2", platform, 2001);
        
        GameSet instance = new GameSet();
        instance.addGame(g1);
        instance.addGame(g2);
        instance.addGame(g3);
        instance.addGame(g4);
        
        
        Set<Game> expResult = new HashSet<>();
        expResult.add(g1);
        expResult.add(g4);
        Set<Game> result = instance.getGames(platform);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_year() {
        System.out.println("Testing getting all games that match a given year");
        int year = 2000;
        
                Game g1 = new Game("TG1", Game.Platform.PC_Steam, year);
        Game g2 = new Game("TG1", Game.Platform.PC_Steam, 2001);
        Game g3 = new Game("TG2", Game.Platform.PC_Steam, year);
        Game g4 = new Game("TG2", Game.Platform.PC_Steam, 2001);
        
        GameSet instance = new GameSet();
        instance.addGame(g1);
        instance.addGame(g2);
        instance.addGame(g3);
        instance.addGame(g4);
        
        Set<Game> expResult = new HashSet<>();
        expResult.add(g1);
        expResult.add(g3);
       
        Set<Game> result = instance.getGames(year);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of removeGame method, of class GameSet.
     */
    @Test
    public void testRemoveGame() {
        System.out.println("Testing");
        Game g = null;
        GameSet instance = new GameSet();
        boolean expResult = false;
        boolean result = instance.removeGame(g);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_0args() {
        System.out.println("getGames");
        GameSet instance = new GameSet();
        Set<Game> expResult = null;
        Set<Game> result = instance.getGames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGame method, of class GameSet.
     */
    @Test
    public void testGetGame_String() {
        System.out.println("getGame");
        String gameName = "";
        GameSet instance = new GameSet();
        Game expResult = null;
        Game result = instance.getGame(gameName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGame method, of class GameSet.
     */
    @Test
    public void testGetGame_3args() {
        System.out.println("getGame");
        String gameName = "";
        Game.Platform p = null;
        int year = 0;
        GameSet instance = new GameSet();
        Game expResult = null;
        Game result = instance.getGame(gameName, p, year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_String() {
        System.out.println("getGames");
        String gameName = "";
        GameSet instance = new GameSet();
        Set<Game> expResult = null;
        Set<Game> result = instance.getGames(gameName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGames method, of class GameSet.
     */
    @Test
    public void testGetGames_int() {
        System.out.println("getGames");
        int year = 0;
        GameSet instance = new GameSet();
        Set<Game> expResult = null;
        Set<Game> result = instance.getGames(year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class GameSet.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        GameSet instance = new GameSet();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class GameSet.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        GameSet instance = new GameSet();
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}