package gametracker.cli;

import gametracker.data.Game;
import gametracker.data.PlaySession;
import gametracker.data.PlayData;
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

        Game breach = new Game("In To The Breach", Game.Platform.PC_Steam, 2018);
        Game stardew = new Game("Stardew Valley", Game.Platform.PC_Steam, 2018);

        PlaySession s1 = new PlaySession(
                breach,
                new DateTime(2018, 4, 9, 0, 0),
                1.0);
        
        
        PlaySession s2 = new PlaySession(
                stardew,
                new DateTime(2018, 4, 9, 0, 0),
                0.3);
        
        PlayData set = new PlayData();
        
        set.addPlaySession(s1);
        set.addPlaySession(s2);
        
        for (PlaySession s : set.getPlaySessions()) {
            System.out.println(s);
        }
        

    }

}
