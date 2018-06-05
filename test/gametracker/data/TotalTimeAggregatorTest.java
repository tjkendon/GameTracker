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
public class TotalTimeAggregatorTest {
    
   static PlayData sourceData;
    static GameSet games;

    static Game a = new Game("Game A", Game.Platform.PC_Steam, 2000);
    // used once (1.0 - total time)
    static Game b1 = new Game("Game B", Game.Platform.PC_Steam, 2000);
    // 1.25 - total time
    static Game b2 = new Game("Game B", Game.Platform.PC_Steam, 2001);
    // 1.0 - total time
    static Game b3 = new Game("Game B", Game.Platform.PC_Steam, 2002);
    // 1.5 - total time
    static Game c1 = new Game("Game C", Game.Platform.PC_Steam, 2000);
    // 3.5 - total time
    static Game c2 = new Game("Game C", Game.Platform.Wii, 2000);
    // 4.25 - total time
    static Game c3 = new Game("Game C", Game.Platform.DS, 2000);
    // 0.5 - total time
    static Game d = new Game("Game D", Game.Platform.PC_Steam, 2000);
    // never used
    static Game e = new Game("Game E", Game.Platform.PC_Steam, 2000);
    // 9.75 - total time
    static Game f = new Game("Game F", Game.Platform.PC_Steam, 2000);
    // once per day (6 total - 0.6 Total time)
    static Game g = new Game("Game G", Game.Platform.PC_Steam, 2002);
    // used only once in 36 (1.5 - total time)
    static PlaySession[] sessions = new PlaySession[37];

    
    public TotalTimeAggregatorTest() {
        
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
        
        sessions[36] = new PlaySession(g, new DateTime(2000, 1, 7, 0, 0), 1.5);

        sourceData = new PlayData();

        for (int i = 0; i < 37; i++) {
            sourceData.addPlaySession(sessions[i]);
        }
        
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
     * Test of aggregate method, of class TotalTimeAggregator.
     */
    @Test
    public void testAggregateEmptyPlayData() {
        System.out.println("Testing Aggregating An Empty Play Data");
        
        PlayData source = new PlayData();
        
        TotalTimeAggregator instance = new TotalTimeAggregator(source);
        PlayAggregate expResult = new PlayAggregate();
        PlayAggregate result = instance.aggregate();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testAggregatePlayDataOneGame() {
        System.out.println("Testing Aggregating A Play Data With only 1 game");
        
        PlayData source = new PlayData();
        source.addPlaySession(sessions[5]);
        source.addPlaySession(sessions[11]);
        source.addPlaySession(sessions[17]);
        source.addPlaySession(sessions[23]);
        source.addPlaySession(sessions[29]);
        source.addPlaySession(sessions[35]);
                // six fs 5, 11, 17, 23, 29, 35 - Total time = 0.6

        TotalTimeAggregator instance = new TotalTimeAggregator(source);
        PlayAggregate expResult = new PlayAggregate();
        
        expResult.putAggregate(f, PlayAggregate.AggregateType.TOTAL_TIME, 0.6);
        
        PlayAggregate result = instance.aggregate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAggregatePlayDataMultiGame() {
        System.out.println("Testing Aggregating A Play Data With multi games");
        
        TotalTimeAggregator instance = new TotalTimeAggregator(sourceData);
        
        PlayAggregate expResult = new PlayAggregate();
        
        expResult.putAggregate(a, PlayAggregate.AggregateType.TOTAL_TIME, 1.0);
        expResult.putAggregate(
                b1, PlayAggregate.AggregateType.TOTAL_TIME, 1.25);
        expResult.putAggregate(
                b2, PlayAggregate.AggregateType.TOTAL_TIME, 1.0);
        expResult.putAggregate(
                b3, PlayAggregate.AggregateType.TOTAL_TIME, 1.5);
        expResult.putAggregate(
                c1, PlayAggregate.AggregateType.TOTAL_TIME, 3.5);
        expResult.putAggregate(
                c2, PlayAggregate.AggregateType.TOTAL_TIME, 4.25);
        expResult.putAggregate(
                c3, PlayAggregate.AggregateType.TOTAL_TIME, 0.5);
        expResult.putAggregate(
                d, PlayAggregate.AggregateType.TOTAL_TIME, 0);
        expResult.putAggregate(
                e, PlayAggregate.AggregateType.TOTAL_TIME, 9.75);
        expResult.putAggregate(
                f, PlayAggregate.AggregateType.TOTAL_TIME, 0.6);
        expResult.putAggregate(
                g, PlayAggregate.AggregateType.TOTAL_TIME, 1.5);
        
        PlayAggregate result = instance.aggregate();
        assertEquals(expResult, result);

    }
    
}
