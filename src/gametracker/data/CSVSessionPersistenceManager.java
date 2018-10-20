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
import org.joda.time.LocalDate;

/**
 * 
 * Loaded and saves play session data in a comma-separated file in the host 
 * file system.
 * 
 */
public class CSVSessionPersistenceManager implements SessionPersistenceManager {

    /**
     * The file to load from and save to
     */
    private File datafile; // 
    /**
     * The list of games that is used to link game names in sessions when 
     * loading from the file
     */
    private GameSet gameSet; // the set that will be saved


    /**
     * Creates a new CSVSessionPersistenceManager with a handle for a file
     * to save the data in and a gameSet to use to match game data.
     * 
     * @param datafile the handle for the file this manager will store the data
     * in
     * @param gameSet the game data to use to link games in sessions
     */
    public CSVSessionPersistenceManager(File datafile, GameSet gameSet) {
        this.datafile = datafile;
        this.gameSet = gameSet;
    }

    /**
     * 
     * Changes the file handle this manager is using to save data to the 
     * new file handle.
     * 
     * @param datafile the handle this manager will use to save data
     */
    public void setDatafile(File datafile) {
        this.datafile = datafile;
    }
    
    
    public boolean hasDataFile() {
        return this.datafile != null;
    }

    public void setGameSet(GameSet gameSet) {
        this.gameSet = gameSet;
    }

    @Override
    public PlaySessionList load() {

        PlaySessionList returnData = new PlaySessionList();

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
        LocalDate date = PlaySession.parseDateTime(dateString);

        String gameString = playStrings[1].trim();

        Game game = gameSet.getGame(gameString);

        String timeString = playStrings[2].trim();
        Double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }

    
    /**
     * Parses play session data an array of 5 strings, with the format
     * (Date, Game Name, Platform, Year, Time).
     * 
     * The game (as identified by name, platform and year) must be included
     * in the {@link GameSet}.
     * 
     * @param sessionStrings array Strings (Date, Game Name, Platform, Year, Time) 
     * for a play session
     * 
     * @return play session based on the strings
     */
    private PlaySession parseFull(String[] sessionStrings) {
        String dateString = sessionStrings[0].trim();
        LocalDate date = PlaySession.parseDateTime(dateString);

        String gameString = sessionStrings[1].trim();
        Game.Platform platform
                = Game.parsePlatform(sessionStrings[2].trim());
        int year = Game.parseYear(sessionStrings[3].trim());

        Game game = gameSet.getGame(gameString, platform, year);

        String timeString = sessionStrings[4].trim();
        Double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }

    /**
     * 
     * Saves the give play sessions in the csv file this manager is 
     * representing.
     * 
     * 
     */
    @Override
    public void savePlayData(PlaySessionList sessions) {

        if (datafile == null) {
            throw new IllegalStateException(
                    "No file set to save session data");
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
