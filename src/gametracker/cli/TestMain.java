package gametracker.cli;


import gametracker.core.CSVPersistenceManager;
import gametracker.data.*;
import org.joda.time.LocalDate;

public class TestMain {

    public static void main(String[] args) {

        String[] pArgs = new String[2];
        pArgs[0] = "-f";
        pArgs[1] = "testmain.data";
        CSVPersistenceManager manager = new CSVPersistenceManager(pArgs);

        GameSet set = manager.getGameSet();
        Game g1 = new Game("Test1", Game.Platform.DS, 2001);
        Game g2 = new Game("Test2", Game.Platform.DS, 2002);
        Game g3 = new Game("Test3", Game.Platform.DS, 2003);
        set.addGame(g1);
        set.addGame(g2);
        set.addGame(g3);

        PlaySession p1 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p2 = new PlaySession(g1, LocalDate.now(), 1);
        PlaySession p3 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p4 = new PlaySession(g2, LocalDate.now(), 1);
        PlaySession p5 = new PlaySession(g3, LocalDate.now(), 1);

        PlaySessionList list = manager.getPlaySessionList();
        list.addPlaySession(p1);
        list.addPlaySession(p2);
        list.addPlaySession(p3);
        list.addPlaySession(p4);
        list.addPlaySession(p5);

       manager.save();

        set = new GameSet();
        list = new PlaySessionList();

        manager.load();

        set = manager.getGameSet();
        list = manager.getPlaySessionList();

        System.out.println(set.size());
        System.out.println(list.getPlaySessions().size());



    }

}
