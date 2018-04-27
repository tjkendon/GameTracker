/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.io.File;
import org.joda.time.DateTime;
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
    
}
