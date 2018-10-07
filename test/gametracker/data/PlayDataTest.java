/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
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
public class PlayDataTest {
    
    private final Game testgame1;
    private final Game testgame2;
    
    private final LocalDate testDate1;
    
    public PlayDataTest() {
        
        testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        testgame2 = new Game("Test2", Game.Platform.PC_Steam, 2000);
        
        testDate1 = LocalDate.now();
        
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
     * Test of addPlaySession method, of class PlayData.
     */
    @Test
    public void testAddPlaySession() {
        System.out.println("Testing Adding A PlaySession to PlayData");
        PlaySession session = new PlaySession(testgame1, testDate1, 1);
        PlayData instance = new PlayData();
        instance.addPlaySession(session);
        assertTrue(instance.contains(session));
    }

    /**
     * Test of getPlaySessions method, of class PlayData.
     */
    @Test
    public void testGetPlaySessions() {
        System.out.println("Testing Getting PlaySessions from PlayData");
        PlayData instance = new PlayData();
        List<PlaySession> expResult = new ArrayList<>();
        
        PlaySession session1 = new PlaySession(testgame1, testDate1, 0);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0);
        
        expResult.add(session1);
        expResult.add(session2);
        
        instance.addPlaySession(session1);
        instance.addPlaySession(session2);
        
        List<PlaySession> result = instance.getPlaySessions();
        assertEquals(expResult, result);
        
    }
    
    
    
}
