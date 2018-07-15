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
    public void testPutandGetAggregate() {
        System.out.println("Testing Putting and Getting Aggregates");
        Game game = new Game("Test", Game.Platform.PC_Steam, 2000);
        PlayAggregate.AggregateType type = 
                PlayAggregate.AggregateType.AVERAGE_TIME;
        double value = 2.0;
        PlayAggregate instance = new PlayAggregate();
        instance.putAggregate(game, type, value);
        
        assertEquals(instance.getAggregatesForGame(game).get(type), value, 0.001);
    }

    /**
     * Test of mergeAggregates method, of class PlayAggregate.
     */
    @Test
    public void testMergeAggregates() {
        System.out.println("Testing Merging Aggregates");
        
        Game game = new Game("Test", Game.Platform.PC_Steam, 2000);
        PlayAggregate.AggregateType type = 
                PlayAggregate.AggregateType.AVERAGE_TIME;
        double value = 2.0;
        PlayAggregate instance = new PlayAggregate();
        instance.putAggregate(game, type, value);
        
        PlayAggregate.AggregateType type2 = 
                PlayAggregate.AggregateType.MEDIAN_TIME;
        double value2 = 3.0;
        PlayAggregate instance2 = new PlayAggregate();
        instance2.putAggregate(game, type2, value2);
        
        PlayAggregate.AggregateType type3 = 
                PlayAggregate.AggregateType.TOTAL_COUNT;
        double value3 = 4.0;
        PlayAggregate instance3 = new PlayAggregate();
        instance2.putAggregate(game, type3, value3);
        
        PlayAggregate.AggregateType type4 = 
                PlayAggregate.AggregateType.TOTAL_TIME;
        double value4 = 5.0;
        PlayAggregate instance4 = new PlayAggregate();
        instance2.putAggregate(game, type4, value4);
        
        instance.mergeAggregates(instance2);
        double result2 = instance.getAggregatesForGame(game).get(type2);
        assertEquals(result2, value2, 0.001);
        
        instance.mergeAggregates(instance3, instance4);
        double result3 = instance.getAggregatesForGame(game).get(type3);
        assertEquals(result3, value3, 0.001);
        double result4 = instance.getAggregatesForGame(game).get(type4);
        assertEquals(result4, value4, 0.001);
        
        
    }



    
    
}
