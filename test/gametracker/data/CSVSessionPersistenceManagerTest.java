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
public class CSVSessionPersistenceManagerTest {
    
    public CSVSessionPersistenceManagerTest() {
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
     * Test of savePlayData method, of class CSVSessionPersistenceManager.
     */
    @Test
    public void testSaveAndLoadPlayData() {
        System.out.println(
                "Testing saving and loading play data (in that order)");
        
        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test2", Game.Platform.PC_Steam, 2000);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);
        
        DateTime testDate1 = DateTime.now();
        
        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);
        
        PlayData original = new PlayData();
        
        original.addPlaySession(session1);
        original.addPlaySession(session2);
        
        CSVSessionPersistenceManager instance = 
                new CSVSessionPersistenceManager(
                        new File("data/test/session.data"), games);
        instance.savePlayData(original);
        
        PlayData result = instance.load();
        
        assertEquals(original, result);
    }
    
}
