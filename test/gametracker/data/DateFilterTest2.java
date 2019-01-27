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

        LocalDate beforeDate = new LocalDate(2000, 1, 4);

        DateFilter instance = new DateFilter();
        instance.addWindow(null, beforeDate);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session : sessions) {
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

        for (PlaySession session : sessions) {
            if (session.getSessionDate().isAfter(afterDate)
                    || session.getSessionDate().isEqual(afterDate)) {
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

        for (PlaySession session : sessions) {
            if ((session.getSessionDate().isAfter(afterDate)
                    || session.getSessionDate().isEqual(afterDate))
                    && session.getSessionDate().isBefore(beforeDate)) {
                expResult.addPlaySession(session);
            }
        }

        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }

    @Test
    public void testFilterGamesBetweenDatesMulti() {
        System.out.println("Testing Filter Take Games Between Two Dates");

        LocalDate start1 = new LocalDate(2000, 1, 2);
        LocalDate end1 = new LocalDate(2000, 1, 3);

        LocalDate start2 = new LocalDate(2000, 1, 5);
        LocalDate end2 = new LocalDate(2000, 1, 7);

        DateFilter instance = new DateFilter();
        instance.addWindow(start1, end1);
        instance.addWindow(start2, end2);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session : sessions) {
            if ((session.getSessionDate().isAfter(start1)
                    || session.getSessionDate().isEqual(start1))
                    && session.getSessionDate().isBefore(end1)) {
                expResult.addPlaySession(session);
            }
            if ((session.getSessionDate().isAfter(start2)
                    || session.getSessionDate().isEqual(start2))
                    && session.getSessionDate().isBefore(end2)) {
                expResult.addPlaySession(session);
            }
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }

    @Test
    public void testFilterGamesBetweenDatesMultiEncapsulatingAinB() {
        System.out.println("Testing Filter Take Games Between Two Dates, "
                + "Encapsulating (First in Second)");

        LocalDate start1 = new LocalDate(2000, 1, 3);
        LocalDate end1 = new LocalDate(2000, 1, 5);

        LocalDate start2 = new LocalDate(2000, 1, 2);
        LocalDate end2 = new LocalDate(2000, 1, 6);

        DateFilter instance = new DateFilter();
        instance.addWindow(start1, end1);
        instance.addWindow(start2, end2);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session : sessions) {
            if (((session.getSessionDate().isAfter(start1)
                    || session.getSessionDate().isEqual(start1))
                    && session.getSessionDate().isBefore(end1)) ||
                            ((session.getSessionDate().isAfter(start2)
                    || session.getSessionDate().isEqual(start2))
                    && session.getSessionDate().isBefore(end2))) {
                expResult.addPlaySession(session);
            }
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }

    
    @Test
    public void testFilterGamesBetweenDatesMultiEncapsulatingBinA() {
        System.out.println("Testing Filter Take Games Between Two Dates, "
                + "Encapsulating (Second in First)");
        
        LocalDate start1 = new LocalDate(2000, 1, 2);
        LocalDate end1 = new LocalDate(2000, 1, 6);
        
        LocalDate start2 = new LocalDate(2000, 1, 3);
        LocalDate end2 = new LocalDate(2000, 1, 5);

        
        
        DateFilter instance = new DateFilter();
        instance.addWindow(start1, end1);
        instance.addWindow(start2, end2);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session : sessions) {
            if (((session.getSessionDate().isAfter(start1)
                    || session.getSessionDate().isEqual(start1))
                    && session.getSessionDate().isBefore(end1)) ||
                            ((session.getSessionDate().isAfter(start2)
                    || session.getSessionDate().isEqual(start2))
                    && session.getSessionDate().isBefore(end2))) {
                expResult.addPlaySession(session);
            }
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFilterGamesBetweenDatesMultiOverlap() {
        System.out.println("Testing Filter Take Games Between Two Dates "
                + "Overlapping");
        
        LocalDate start1 = new LocalDate(2000, 1, 2);
        LocalDate end1 = new LocalDate(2000, 1, 4);
        
        LocalDate start2 = new LocalDate(2000, 1, 3);
        LocalDate end2 = new LocalDate(2000, 1, 5);
        
        DateFilter instance = new DateFilter();
        instance.addWindow(start1, end1);
        instance.addWindow(start2, end2);
        PlaySessionList expResult = new PlaySessionList();
        for (PlaySession session : sessions) {
            if (((session.getSessionDate().isAfter(start1)
                    || session.getSessionDate().isEqual(start1))
                    && session.getSessionDate().isBefore(end1)) ||
                            ((session.getSessionDate().isAfter(start2)
                    || session.getSessionDate().isEqual(start2))
                    && session.getSessionDate().isBefore(end2))) {
                expResult.addPlaySession(session);
            }
        }
        PlaySessionList result = instance.filter(sourceData);
        assertEquals(expResult, result);
    } 


}
