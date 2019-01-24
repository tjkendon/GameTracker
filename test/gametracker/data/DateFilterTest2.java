/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.ArrayList;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author tjkendon
 */
public class DateFilterTest2 {
    
    
    
     private final PlaySessionList sourceData;
     private final GameSet games;

     private final Game a = new Game("Game A", Game.Platform.PC_Steam, 2000);
     private final Game b = new Game("Game B", Game.Platform.PC_Steam, 2000);
     private final Game c = new Game("Game C", Game.Platform.PC_Steam, 2000);
     private final Game d = new Game("Game D", Game.Platform.PC_Steam, 2000);
     private final Game e = new Game("Game E", Game.Platform.PC_Steam, 2000);
     private final Game f = new Game("Game F", Game.Platform.PC_Steam, 2000);
     private final Game g = new Game("Game G", Game.Platform.PC_Steam, 2002);
    
    private ArrayList<PlaySession> sessions;

    
    public DateFilterTest2() {
        
        games = new GameSet();
        games.addGame(a);
        games.addGame(b);
        games.addGame(c);
        games.addGame(d);
        games.addGame(e);
        games.addGame(f);
        
        sessions = new ArrayList<>();

        sessions.add(new PlaySession(a, new LocalDate(2000, 1, 1), 1.1));
        sessions.add(new PlaySession(b, new LocalDate(2000, 1, 2), 1.2));
        sessions.add(new PlaySession(c, new LocalDate(2000, 1, 3), 1.3));
        sessions.add(new PlaySession(d, new LocalDate(2000, 1, 4), 1.4));
        sessions.add(new PlaySession(e, new LocalDate(2000, 1, 5), 1.5));
        sessions.add(new PlaySession(f, new LocalDate(2000, 1, 6), 1.6));
        sessions.add(new PlaySession(g, new LocalDate(2000, 1, 7), 1.7));

        sourceData = new PlaySessionList();
        for (PlaySession p : sessions) {
            sourceData.addPlaySession(p);
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
    
    // All Before
    
    /**
     * Test of filter method, of class DateFilter.
     */
    @Test
    public void testFilterGamesBeforeDate() {
        System.out.println("Testing Filter Take Games Before Date");
        
        LocalDate beforeDate =new LocalDate(2000, 1, 4);
        
        DateFilter instance = new DateFilter();
        instance.addWindow(null, beforeDate);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session: sessions) {
            if (session.getSessionDate().isBefore(beforeDate)) {
                expResult.addPlaySession(session);
            }
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesAfterDate() {
        System.out.println("Testing Filter Take Games After Date");
        
        LocalDate afterDate = new LocalDate(2000, 1, 4);
        
        DateFilter instance = new DateFilter();
        instance.addWindow(afterDate, null);
        PlaySessionList expResult = new PlaySessionList();
        
        for (PlaySession session: sessions) {
            if (session.getSessionDate().isAfter(afterDate) || 
                    session.getSessionDate().isEqual(afterDate)) {
                expResult.addPlaySession(session);
            }
        }
        
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesBetweenDates() {
        System.out.println("Testing Filter Take Games Between Two Dates");
        
        LocalDate afterDate = new LocalDate(2000, 1, 2);
        LocalDate beforeDate = new LocalDate(2000, 1, 5);
        
        DateFilter instance = new DateFilter();
        instance.addWindow(afterDate, beforeDate);
        PlaySessionList expResult = new PlaySessionList();
        
        for (PlaySession session: sessions) {
            if ((session.getSessionDate().isAfter(afterDate) || 
                    session.getSessionDate().isEqual(afterDate)) &&
                   session.getSessionDate().isBefore(beforeDate)) {
                expResult.addPlaySession(session);
            }
        }
        
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    /*
    @Test
    public void testFilterGamesBetweenDatesMulti() {
        System.out.println("Testing Filter Take Games Between Two Dates");
        
        DateFilter instance = new DateFilter();
        instance.addWindow(new LocalDate(2000, 1, 2), new LocalDate(2000, 1, 4));
        instance.addWindow(new LocalDate(2000, 1, 5), new LocalDate(2000, 1, 7));
        PlaySessionList expResult = new PlaySessionList();
        for (int i = 6; i <= 17; i++) {
            expResult.addPlaySession(getSessions()[i]);
        }
        for (int i = 24; i <= 35; i++) {
            expResult.addPlaySession(getSessions()[i]);
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesBetweenDatesMultiEncapsulatingAinB() {
        System.out.println("Testing Filter Take Games Between Two Dates, "
                + "Encapsulating (First in Second)");
        
        DateFilter instance = new DateFilter();
        instance.addWindow(new LocalDate(2000, 1, 3), new LocalDate(2000, 1, 5));
        instance.addWindow(new LocalDate(2000, 1, 2), new LocalDate(2000, 1, 6));
        PlaySessionList expResult = new PlaySessionList();
        for (int i = 6; i <= 29; i++) {
            expResult.addPlaySession(getSessions()[i]);
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesBetweenDatesMultiEncapsulatingBinA() {
        System.out.println("Testing Filter Take Games Between Two Dates, "
                + "Encapsulating (Second in First)");
        
        DateFilter instance = new DateFilter();
        instance.addWindow(new LocalDate(2000, 1, 2), new LocalDate(2000, 1, 6));
        instance.addWindow(new LocalDate(2000, 1, 3), new LocalDate(2000, 1, 5));
        PlaySessionList expResult = new PlaySessionList();
        for (int i = 6; i <= 29; i++) {
            expResult.addPlaySession(getSessions()[i]);
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesBetweenDatesMultiOverlap() {
        System.out.println("Testing Filter Take Games Between Two Dates "
                + "Overlapping");
        
        DateFilter instance = new DateFilter();
        instance.addWindow(new LocalDate(2000, 1, 2), new LocalDate(2000, 1, 4));
        instance.addWindow(new LocalDate(2000, 1, 3), new LocalDate(2000, 1, 5));
        PlaySessionList expResult = new PlaySessionList();
        for (int i = 6; i <= 23; i++) {
            expResult.addPlaySession(getSessions()[i]);
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    } */
    
    // test for overlapping multis

    /**
     * @return the sessions
     */
    public ArrayList<PlaySession> getSessions() {
        return sessions;
    }

    /**
     * @param sessions the sessions to set
     */
    public void setSessions(ArrayList<PlaySession> sessions) {
        this.sessions = sessions;
    }
    
    
   

    
}
