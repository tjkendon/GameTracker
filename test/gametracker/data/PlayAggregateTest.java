/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.Map;
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
public class PlayAggregateTest {
    
    public PlayAggregateTest() {
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
     * Test of putAggregate method, of class PlayAggregate.
     */
    @Test
    public void testPutAggregate() {
        System.out.println("Put Aggregate");
        Game game = new Game("Test", Game.Platform.PC_Steam, 2000);
        PlayAggregate.AggregateType type = 
                PlayAggregate.AggregateType.AVERAGE_TIME;
        double value = 2.0;
        PlayAggregate instance = new PlayAggregate();
        instance.putAggregate(game, type, value);
        
        
        assertEquals(instance.getAggregatesForGame(game).get(type), value, 0.1);
    }

    /**
     * Test of mergeAggregates method, of class PlayAggregate.
     */
    @Test
    public void testMergeAggregates() {
        System.out.println("mergeAggregates");
        PlayAggregate[] others = null;
        PlayAggregate instance = new PlayAggregate();
        instance.mergeAggregates(others);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAggregates method, of class PlayAggregate.
     */
    @Test
    public void testGetAggregates() {
        System.out.println("getAggregates");
        PlayAggregate instance = new PlayAggregate();
        Map<Game, Map<PlayAggregate.AggregateType, Double>> expResult = null;
        Map<Game, Map<PlayAggregate.AggregateType, Double>> result = instance.getAggregates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAggregatesForGame method, of class PlayAggregate.
     */
    @Test
    public void testGetAggregatesForGame() {
        System.out.println("getAggregatesForGame");
        Game game = null;
        PlayAggregate instance = new PlayAggregate();
        Map<PlayAggregate.AggregateType, Double> expResult = null;
        Map<PlayAggregate.AggregateType, Double> result = instance.getAggregatesForGame(game);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class PlayAggregate.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        PlayAggregate instance = new PlayAggregate();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearGame method, of class PlayAggregate.
     */
    @Test
    public void testClearGame() {
        System.out.println("clearGame");
        Game game = null;
        PlayAggregate instance = new PlayAggregate();
        instance.clearGame(game);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
}
