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

    private File datafile;
    private GameSet gameSet;

    public CSVSessionPersistenceManager() {

    }

    public CSVSessionPersistenceManager(File datafile, GameSet gameSet) {
        this.datafile = datafile;
        this.gameSet = gameSet;
    }

    public File getDatafile() {
        return datafile;
    }

    public GameSet getGameSet() {
        return gameSet;
    }

    public void setDatafile(File datafile) {
        this.datafile = datafile;
    }

    public void setGameSet(GameSet gameSet) {
        this.gameSet = gameSet;
    }

    @Override
    public PlayData load() {

        PlayData returnData = new PlayData();

        if (datafile == null) {
            throw new IllegalStateException(
                    "Datafile not set to load session data");
        }

        if (gameSet == null) {
            throw new IllegalStateException(
                    "GameSet not set to load session data");
        }

        try {
            Scanner scanner = new Scanner(datafile);

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String playString = scanner.nextLine();
                String[] playStrings = playString.split(",");
                try {
                    PlaySession s = null;
                   if (playStrings.length == 3) {
                       s = parseShort(playStrings);
                   } else if (playStrings.length == 5) {
                       s = parseFull(playStrings);
                   } else {
                       System.err.println("Line " + lineNumber
                            + ": not added - Format not recognized");
                       continue;
                   }
                   returnData.addPlaySession(s);
                } catch (Exception e) {
                    System.err.println("Line " + lineNumber
                            + ": not added - " + e.getMessage());
                } finally {
                    lineNumber++;
                }

            }

        } catch (FileNotFoundException ex) {
            throw new IllegalStateException(
                    "Not able to find file " + datafile.getName(), ex);
        }

        return returnData;
    }

    /**
     * Attempts to parse a play session from 3 terms in an array of strings.
     * (Date, Game, Time).
     * 
     * Will fail if the game name is not unique.
     * 
     * 
     * @param playStrings array of data to attempt to read
     * @return play session from provided data
     */
    private PlaySession parseShort(String[] playStrings) {
        String dateString = playStrings[0].trim();
        DateTime date = PlaySession.parseDateTime(dateString);

        String gameString = playStrings[1].trim();

        Game game = gameSet.getGame(gameString);

        String timeString = playStrings[2].trim();
        Double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }

    
        /**
     * Attempts to parse a play session from 5 terms in an array of strings.
     * (Date, Game Name, Platform, Year, Time).
     * 
     * Will fail if the game is not in the game set
     * 
     * 
     * @param playStrings array of data to attempt to read
     * @return play session from provided data
     */
    private PlaySession parseFull(String[] playStrings) {
        String dateString = playStrings[0].trim();
        DateTime date = PlaySession.parseDateTime(dateString);

        String gameString = playStrings[1].trim();
        Game.Platform platform
                = Game.parsePlatform(playStrings[2].trim());
        int year = Game.parseYear(playStrings[3].trim());

        Game game = gameSet.getGame(gameString, platform, year);

        String timeString = playStrings[4].trim();
        Double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }

    @Override
    public void savePlayData(PlayData sessions) {

        if (datafile == null) {
            throw new IllegalStateException(
                    "Datafile not set to save session data");
        }

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
            throw new IllegalStateException(
                    "Not able to find file " + datafile.getName(), ex);
        }

    }

}
