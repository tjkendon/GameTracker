/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

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
public class GameFilterTest {

    static PlayData sourceData;
    static GameSet games;

    static Game a = new Game("Game A", Game.Platform.PC_Steam, 2000);
        // used once
    static Game b1 = new Game("Game B", Game.Platform.PC_Steam, 2000);
    static Game b2 = new Game("Game B", Game.Platform.PC_Steam, 2001);
    static Game b3 = new Game("Game B", Game.Platform.PC_Steam, 2002);
    static Game c1 = new Game("Game C", Game.Platform.PC_Steam, 2000);
    static Game c2 = new Game("Game C", Game.Platform.Wii, 2000);
    static Game c3 = new Game("Game C", Game.Platform.DS, 2000);
    static Game d = new Game("Game D", Game.Platform.PC_Steam, 2000); 
        // never used
    static Game e = new Game("Game E", Game.Platform.PC_Steam, 2000);
    static Game f = new Game("Game F", Game.Platform.PC_Steam, 2000);
        // once per day (6 total)

    public GameFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        games = new GameSet();
        games.addGame(a);
        games.addGame(b1);
        games.addGame(b2);
        games.addGame(b3);
        games.addGame(c1);
        games.addGame(c2);
        games.addGame(c3);
        games.addGame(d);
        games.addGame(e);
        games.addGame(f);

        sourceData = new PlayData();

        sourceData.addPlaySession(
                new PlaySession(a, new DateTime(2000, 1, 1, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 1, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(b1, new DateTime(2000, 1, 1, 0, 0), 0.25));
        sourceData.addPlaySession(
                new PlaySession(b2, new DateTime(2000, 1, 1, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(b3, new DateTime(2000, 1, 1, 0, 0), 0.75));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 1, 0, 0), 0.1));

        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(c1, new DateTime(2000, 1, 2, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(c2, new DateTime(2000, 1, 2, 0, 0), 4));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 2, 0, 0), 0.1));

        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 3, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(c1, new DateTime(2000, 1, 3, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(c2, new DateTime(2000, 1, 3, 0, 0), 0.25));
        sourceData.addPlaySession(
                new PlaySession(c3, new DateTime(2000, 1, 3, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 3, 0, 0), 0.75));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 3, 0, 0), 0.1));

        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 4, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 4, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(b1, new DateTime(2000, 1, 4, 0, 0), 0.25));
        sourceData.addPlaySession(
                new PlaySession(b2, new DateTime(2000, 1, 4, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(b3, new DateTime(2000, 1, 4, 0, 0), 0.75));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 4, 0, 0), 0.1));

        sourceData.addPlaySession(
                new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(b1, new DateTime(2000, 1, 5, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 0.25));
        sourceData.addPlaySession(
                new PlaySession(b1, new DateTime(2000, 1, 5, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 0.75));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 5, 0, 0), 0.1));

        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 1.0));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.25));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.5));
        sourceData.addPlaySession(
                new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.75));
        sourceData.addPlaySession(
                new PlaySession(f, new DateTime(2000, 1, 6, 0, 0), 0.1));

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
     * Test of filter method, of class GameFilter.
     */
    @Test
    public void testFilterGameNotInData() {
        System.out.println("Testing Filter With Game not in Data");

        GameFilter instance = new GameFilter(d); 
                // d is never used in the test data
        PlayData expResult = new PlayData();
        PlayData result = instance.filter(sourceData);
        assertEquals(expResult, result);
        
    }

}
