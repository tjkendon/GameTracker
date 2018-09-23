/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.io.File;
import static org.hamcrest.CoreMatchers.startsWith;
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
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);

        LocalDate testDate1 = LocalDate.now();

        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);

        PlayData original = new PlayData();

        original.addPlaySession(session1);
        original.addPlaySession(session2);

        CSVSessionPersistenceManager instance
                = new CSVSessionPersistenceManager(
                        new File("data/test/session.data"), games);
        instance.savePlayData(original);

        PlayData result = instance.load();

        assertTrue(PlayData.containsMatchingContent(original, result));
        
    }

    @Rule
    public ExpectedException noPlayDataFileRule = ExpectedException.none();

    @Test
    public void testLoadPlayDataNoFile() {
        System.out.println(
                "Testing loading play data with no file");

        noPlayDataFileRule.expect(IllegalStateException.class);
        noPlayDataFileRule.expectMessage(
                startsWith("Datafile not set"));

        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);

        LocalDate testDate1 = LocalDate.now();

        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);

        CSVSessionPersistenceManager instance
                = new CSVSessionPersistenceManager();
        instance.setGameSet(games);
        PlayData load = instance.load();

    }

    @Rule
    public ExpectedException noPlayDataGameRule = ExpectedException.none();

    @Test
    public void testLoadPlayDataNoGame() {
        System.out.println(
                "Testing loading play data with no file");

        noPlayDataGameRule.expect(IllegalStateException.class);
        noPlayDataGameRule.expectMessage(
                startsWith("GameSet not set"));

        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);

        LocalDate testDate1 = LocalDate.now();

        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);

        PlayData original = new PlayData();

        original.addPlaySession(session1);
        original.addPlaySession(session2);

        CSVSessionPersistenceManager instance
                = new CSVSessionPersistenceManager();
        instance.setDatafile(new File("data/test/session.data"));
        PlayData load = instance.load();

    }
    
        @Rule
    public ExpectedException noPlayDataFileRuleSave = ExpectedException.none();

    @Test
    public void testSavePlayDataNoFile() {
        System.out.println(
                "Testing saving play data with no file");

        noPlayDataFileRuleSave.expect(IllegalStateException.class);
        noPlayDataFileRuleSave.expectMessage(
                startsWith("Datafile not set"));

        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet games = new GameSet();
        games.addGame(testgame1);
        games.addGame(testgame2);

        LocalDate testDate1 = LocalDate.now();

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
