package gametracker.core;

import gametracker.data.Game;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlaySessionList;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVPersistenceManagerTest {

    @Test
    public void testPersistence() {

        String[] pArgs = new String[2];
        pArgs[0] = "-f";
        pArgs[1] = "csvtest.data";
        CSVPersistenceManager manager = new CSVPersistenceManager(pArgs);

        GameSet originalSet = manager.getGameSet();
        Game g1 = new Game("Test1", Game.Platform.DS, 2001);
        Game g2 = new Game("Test2", Game.Platform.DS, 2002);
        Game g3 = new Game("Test3", Game.Platform.DS, 2003);
        originalSet.addGame(g1);
        originalSet.addGame(g2);
        originalSet.addGame(g3);

        PlaySession p1 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p2 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p3 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p4 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p5 = new PlaySession(g3, LocalDate.now(), 1);

        PlaySessionList originalList = manager.getPlaySessionList();
        originalList.addPlaySession(p1);
        originalList.addPlaySession(p2);
        originalList.addPlaySession(p3);
        originalList.addPlaySession(p4);
        originalList.addPlaySession(p5);

        manager.save();

        GameSet loadedSet = new GameSet();
        PlaySessionList loadedList = new PlaySessionList();

        manager.load();

        loadedSet = manager.getGameSet();
        loadedList = manager.getPlaySessionList();

        assertEquals(originalSet, loadedSet);
        assertEquals(originalList, loadedList);
    }


    @Test
    public void testClear() {

        String[] pArgs = new String[2];
        pArgs[0] = "-f";
        pArgs[1] = "csvtest.data";
        CSVPersistenceManager manager = new CSVPersistenceManager(pArgs);

        GameSet originalSet = manager.getGameSet();
        Game g1 = new Game("Test1", Game.Platform.DS, 2001);
        Game g2 = new Game("Test2", Game.Platform.DS, 2002);
        Game g3 = new Game("Test3", Game.Platform.DS, 2003);
        originalSet.addGame(g1);
        originalSet.addGame(g2);
        originalSet.addGame(g3);

        PlaySession p1 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p2 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p3 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p4 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p5 = new PlaySession(g3, LocalDate.now(), 1);

        PlaySessionList originalList = manager.getPlaySessionList();
        originalList.addPlaySession(p1);
        originalList.addPlaySession(p2);
        originalList.addPlaySession(p3);
        originalList.addPlaySession(p4);
        originalList.addPlaySession(p5);

        manager.clear();

        assertEquals(0, manager.getGameSet().size());
        assertEquals(0, manager.getPlaySessionList().size());


    }

    @Test
    public void testSetAndGetFile() {
        String[] pArgs = new String[2];
        pArgs[0] = "-f";
        pArgs[1] = "testfilename.data";
        CSVPersistenceManager manager = new CSVPersistenceManager(pArgs);
        assertEquals("testfilename.data", manager.getFileName());
        manager.setFile("testfilename2.data");
        assertEquals("testfilename2.data", manager.getFileName());
    }

}