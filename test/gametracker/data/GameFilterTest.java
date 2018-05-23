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

    static PlaySession[] sessions = new PlaySession[36];

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


        sessions[0] = new PlaySession(a, new DateTime(2000, 1, 1, 0, 0), 1.0);
        // only a
        sessions[1] = new PlaySession(e, new DateTime(2000, 1, 1, 0, 0), 1.0);
        sessions[2] = new PlaySession(b1, new DateTime(2000, 1, 1, 0, 0), 0.25);
        sessions[3] = new PlaySession(b2, new DateTime(2000, 1, 1, 0, 0), 0.5);
        sessions[4] = new PlaySession(b3, new DateTime(2000, 1, 1, 0, 0), 0.75);
        sessions[5] = new PlaySession(f, new DateTime(2000, 1, 1, 0, 0), 0.1);
        // six fs 5, 11, 17, 23, 29, 35

        sessions[6] = new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 1.0);
        sessions[7] = new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 1.0);
        sessions[8] = new PlaySession(e, new DateTime(2000, 1, 2, 0, 0), 0.5);
        sessions[9] = new PlaySession(c1, new DateTime(2000, 1, 2, 0, 0), 0.5);
        sessions[10] = new PlaySession(c2, new DateTime(2000, 1, 2, 0, 0), 4);
        sessions[11] = new PlaySession(f, new DateTime(2000, 1, 2, 0, 0), 0.1);
        
        sessions[12] = new PlaySession(e, new DateTime(2000, 1, 3, 0, 0), 1.0);
        sessions[13] = new PlaySession(c1, new DateTime(2000, 1, 3, 0, 0), 1.0);
        sessions[14] = new PlaySession(c2, new DateTime(2000, 1, 3, 0, 0), 0.25);
        sessions[15] = new PlaySession(c3, new DateTime(2000, 1, 3, 0, 0), 0.5);
        sessions[16] = new PlaySession(e, new DateTime(2000, 1, 3, 0, 0), 0.75);
        sessions[17] = new PlaySession(f, new DateTime(2000, 1, 3, 0, 0), 0.1);

        sessions[18] = new PlaySession(e, new DateTime(2000, 1, 4, 0, 0), 1.0);
        sessions[19] = new PlaySession(e, new DateTime(2000, 1, 4, 0, 0), 1.0);
        sessions[20] = new PlaySession(b1, new DateTime(2000, 1, 4, 0, 0), 0.25);
        sessions[21] = new PlaySession(b2, new DateTime(2000, 1, 4, 0, 0), 0.5);
        sessions[22] = new PlaySession(b3, new DateTime(2000, 1, 4, 0, 0), 0.75);
        sessions[23] = new PlaySession(f, new DateTime(2000, 1, 4, 0, 0), 0.1);

        sessions[24] = new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 1.0);
        sessions[25] = new PlaySession(b1, new DateTime(2000, 1, 5, 0, 0), 1.0);
        sessions[26] = new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 0.25);
        sessions[27] = new PlaySession(b1, new DateTime(2000, 1, 5, 0, 0), 0.5);
        sessions[28] = new PlaySession(c1, new DateTime(2000, 1, 5, 0, 0), 0.75);
        sessions[29] = new PlaySession(f, new DateTime(2000, 1, 5, 0, 0), 0.1);

        sessions[30] = new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 1.0);
        sessions[31] = new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 1.0);
        sessions[32] = new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.25);
        sessions[33] = new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.5);
        sessions[34] = new PlaySession(e, new DateTime(2000, 1, 6, 0, 0), 0.75);
        sessions[35] = new PlaySession(f, new DateTime(2000, 1, 6, 0, 0), 0.1);

        sourceData = new PlayData();

        for (int i = 0; i < 36; i++) {
            sourceData.addPlaySession(sessions[i]);
        }

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

    @Test
    public void testFilterGameOnceInData() {
        System.out.println("Testing Filter With Game only once in Data");

        GameFilter instance = new GameFilter(a);
        // a is used only once in session[0]
        PlayData expResult = new PlayData();
        expResult.addPlaySession(sessions[0]);
        PlayData result = instance.filter(sourceData);
        assertEquals(expResult, result);
        //assertTrue()

    }
    
    @Test
    public void testFilterGameMultiInData() {
        System.out.println("Testing Filter With Game repeatedlyin Data");

        GameFilter instance = new GameFilter(f);
        // a is used only once in session[0]
        PlayData expResult = new PlayData();
        // six fs 5, 11, 17, 23, 29, 35
        expResult.addPlaySession(sessions[5]);
        expResult.addPlaySession(sessions[11]);
        expResult.addPlaySession(sessions[17]);
        expResult.addPlaySession(sessions[23]);
        expResult.addPlaySession(sessions[29]);
        expResult.addPlaySession(sessions[35]);
        PlayData result = instance.filter(sourceData);
        assertEquals(expResult, result);

    }

}
