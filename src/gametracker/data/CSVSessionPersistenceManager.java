/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.joda.time.DateTime;

/**
 *
 * @author tjkendon
 */
public class CSVSessionPersistenceManager implements SessionPersistenceManager {

    private final File datafile;
    private final GameSet gameSet;

    public CSVSessionPersistenceManager(File datafile, GameSet gameSet) {
        this.datafile = datafile;
        this.gameSet = gameSet;
    }

    @Override
    public PlayData load() {
        PlayData returnData = new PlayData();

        try {
            Scanner scanner = new Scanner(datafile);

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String playString = scanner.nextLine();
                String[] playStrings = playString.split(",");
                try {
                    String dateString = playStrings[0].trim();
                    DateTime date = PlaySession.parseDateTime(dateString);

                    String gameString = playStrings[1].trim();
                    Game.Platform platform = Game.parsePlatform(playStrings[2].trim());
                    int year = Game.parseYear(playStrings[3].trim());
                    
                    
                    Game game = gameSet.getGame(gameString, platform, year);

                    String timeString = playStrings[4].trim();
                    Double time = PlaySession.parsePlayTime(timeString);

                    returnData.addPlaySession(
                            new PlaySession(game, date, time));
                } catch (Exception e) {
                    System.err.println("Line " + lineNumber
                            + ": not added - " + e.getMessage());
                } finally {
                    lineNumber++;
                }

            }

        } catch (FileNotFoundException ex) {
            System.err.println("Not able to find file " + datafile.getName());
        }

        return returnData;
    }

    @Override
    public void savePlayData(PlayData sessions) {
        try (PrintWriter writer = new PrintWriter(datafile)) {
            for (PlaySession s : sessions.getPlaySessions()) {
                writer.printf("%s, %s, %s, %s, %s%n",
                        PlaySession.SESSION_DATE_FORMAT.print(s.getSessionDate()),
                        s.getGame().getName(),
                        s.getGame().getPlatform(),
                        s.getGame().getYear(),
                        s.getPlayTime());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Not able to find file " + datafile.getName());
        }

    }

}
