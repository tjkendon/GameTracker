/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.io.File;
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
public class CSVGamePersistenceManagerTest {
    
    public CSVGamePersistenceManagerTest() {
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
     * Test of load method, of class CSVGamePersistenceManager.
     */
    /**
     * Test of savePlayData method, of class CSVSessionPersistenceManager.
     */
    @Test
    public void testSaveAndLoadGameData() {
        System.out.println(
                "Testing saving and loading game data (in that order)");
        
        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test2", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);
        
        CSVGamePersistenceManager instance = 
                new CSVGamePersistenceManager(new File("data/test/game.data"));
        instance.saveGameSet(games);
        GameSet result = instance.load();
        
        assertEquals(games, result);
    }
    
            @Rule
    public ExpectedException noGameDataFileSaveRule = ExpectedException.none();

    @Test
    public void testSavePlayDataNoFile() {
        System.out.println(
                "Testing saving game data with no file");

        noGameDataFileSaveRule.expect(IllegalStateException.class);
        noGameDataFileSaveRule.expectMessage(
                startsWith("Datafile not set"));

        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);

        DateTime testDate1 = DateTime.now();

        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);

        PlayData original = new PlayData();

        original.addPlaySession(session1);
        original.addPlaySession(session2);
        
        CSVSessionPersistenceManager instance
                = new CSVSessionPersistenceManager();
        instance.setGameSet(games);
        instance.savePlayData(original);

    }
    
    
}
