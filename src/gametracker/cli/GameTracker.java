package gametracker.cli;

import gametracker.data.CSVGamePersistenceManager;
import gametracker.data.CSVSessionPersistenceManager;
import gametracker.data.DateFilter;
import gametracker.data.Game;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlayData;
import java.io.File;
import org.joda.time.DateTime;

/**
 *
 * @author tjkendon
 */
public class GameTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PlayData sourceData;
        GameSet games;

        Game a = new Game("Game A", Game.Platform.PC_Steam, 2000);
        // used once
        Game b1 = new Game("Game B", Game.Platform.PC_Steam, 2000);
        Game b2 = new Game("Game B", Game.Platform.PC_Steam, 2001);
        Game b3 = new Game("Game B", Game.Platform.PC_Steam, 2002);
        Game c1 = new Game("Game C", Game.Platform.PC_Steam, 2000);
        Game c2 = new Game("Game C", Game.Platform.Wii, 2000);
        Game c3 = new Game("Game C", Game.Platform.DS, 2000);
        Game d = new Game("Game D", Game.Platform.PC_Steam, 2000);
        // never used
        Game e = new Game("Game E", Game.Platform.PC_Steam, 2000);
        Game f = new Game("Game F", Game.Platform.PC_Steam, 2000);
        // once per day (6 total)
        Game g = new Game("Game G", Game.Platform.PC_Steam, 2002);
        // used only once in 36
        PlaySession[] sessions = new PlaySession[37];

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
        
        
        
        System.out.println(
                "Testing saving and loading play data (in that order)");

        // set up games
        Game testgame1 = new Game("Test1", Game.Platform.PC_Steam, 2000);
        Game testgame2 = new Game("Test1", Game.Platform.PC_Steam, 2001);
        GameSet igames = new GameSet();
        igames.addGame(testgame1);
        igames.addGame(testgame2);

        DateTime testDate1 = new DateTime(2000, 1, 6, 0, 0);

        PlaySession session1 = new PlaySession(testgame1, testDate1, 1);
        PlaySession session2 = new PlaySession(testgame2, testDate1, 0.75);

        PlayData original = new PlayData();

        original.addPlaySession(session1);
        original.addPlaySession(session2);

        CSVSessionPersistenceManager instance
                = new CSVSessionPersistenceManager(
                        new File("data/test/session.data"), igames);
        instance.savePlayData(original);

        PlayData result = instance.load();
        
        System.out.println(PlayData.containsMatchingContent(original, result));

        
        System.out.println("Expected");
        for (PlaySession ps : original.getPlaySessions()) {
            System.out.println(ps);
        }
        System.out.println("--------------------------------");
        System.out.println("Actual");
        for (PlaySession ps : result.getPlaySessions()) {
            System.out.println(ps);
        }

    }

}
