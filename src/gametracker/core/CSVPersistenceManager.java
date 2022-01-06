package gametracker.core;

import gametracker.data.Game;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlaySessionList;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 *
 *
 */
public class CSVPersistenceManager extends FilePersistenceManager {

    /**
     * Default file name if there is no file name stored under <code>gametracker.datafile</code>.
     */
    private static final String DATA_FILE_DEFAULT = "gametracker.csv";


    /**
     * Creates new <code>FilePersistenceManager</code> with the given arguments.
     * <p>
     * In particular it looks for the -datafile/-f argument to update the data file
     * value. If the argument is found it updates the preference, if not found the
     * existing value is used.
     *
     * @param args the argument list with parameters for this manager.
     */
    public CSVPersistenceManager(String[] args) {
        super(args);
        datafile = new File(Preferences.userNodeForPackage(FilePersistenceManager.class).
                get(DATA_FILE_PERF, DATA_FILE_DEFAULT));
        setMenu(new CSVPersistenceManagerMenu(this));
    }

    /**
     * Loads data from the datafile into the game set and play session list.
     * <p>
     * Looks for files formatted with game lines <code>G, name, platform, year</code> and
     * play session lines <code>S, date, game name, game platform, game year, time</code>
     */
    @Override
    public void load() {

        gameSet = new GameSet();
        sessionList = new PlaySessionList();

        int lineNumber = 1;
        try (Scanner scanner = new Scanner(datafile)) {
            while (scanner.hasNextLine()) {
                String gameString = scanner.nextLine();
                String[] gameStrings = gameString.split(",");
                switch (gameStrings[0]) {
                    case "G":
                        try {
                            gameSet.addGame(parseGame(Arrays.copyOfRange(gameStrings, 1, gameStrings.length)));
                        } catch (Exception e) {
                            Logger.getLogger(CSVPersistenceManager.class.getName()).log(Level.WARNING,
                                    String.format("Line %d: %s - not readable", lineNumber, gameString));
                        }
                        break;
                    case "S":
                        try {
                            sessionList.addPlaySession(parseSession(Arrays.copyOfRange(gameStrings, 1, gameStrings.length), gameSet));
                        } catch (SessionFormatException e) {
                            Logger.getLogger(CSVPersistenceManager.class.getName()).log(Level.WARNING,
                                    String.format("Line %d: not added - %s: %s", lineNumber, e.getMessage(), gameString));
                        } catch (Exception e) {
                            Logger.getLogger(CSVPersistenceManager.class.getName()).log(Level.WARNING,
                                    String.format("Line %d:  %s - not readable", lineNumber, e.getMessage(), gameString));
                        }
                        break;
                }
                lineNumber++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                    String.format("File %s not found. No Data loaded", datafile.getName()),
                    ex);
        } catch (NullPointerException ex) {
            throw new IllegalStateException(
                    "No data file set", ex
            );
        }
    }

    /**
     * Parses a new play session based on the tokenized list of strings from the file line.
     * <p>
     * Expected format is either <code>Date, Game Name, Platform, Year, Time</code> or
     * <code>Date, Game Name, Time</code>
     *
     * @param sessionStrings the tokenized list of strings
     * @param games          the game set to match game names against
     * @return a play session with the included data
     * @throws SessionFormatException if a play station can't be created from the list
     */
    private static PlaySession parseSession(String[] sessionStrings, GameSet games)
            throws SessionFormatException {

        switch (sessionStrings.length) {
            case 3:
                return parseShortSession(sessionStrings, games);
            case 5:
                return parseFullSession(sessionStrings, games);
        }
        throw new SessionFormatException();
    }

    /**
     * Parses a new game based on the tokenized list of strings from the file line.
     * <p>
     * Expected format for <code>Name, Platform, Year</code>
     *
     * @param gameStrings the tokenized list of strings
     * @return a game with the included data
     */
    private Game parseGame(String[] gameStrings) {

        String gameName = gameStrings[0].trim();
        Game.Platform platform = Game.parsePlatform(gameStrings[1].trim());
        int year = Game.parseYear(gameStrings[2].trim());
        return new Game(gameName, platform, year);

    }

    /**
     * Saves the game and play session data to the csv file.
     * <p>
     * Format for games is <code>G, name, platform, year</code> and
     * play session is <code>S, date, game name, game platform, game year, time</code>
     */
    @Override
    public void save() {
        if (datafile == null) {
            throw new IllegalStateException("No File Set to Save To");
        }

        try (PrintWriter writer = new PrintWriter(datafile)) {
            writer.print(writeGameData(gameSet));
            writer.println(writePlaySessionData(sessionList));
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Not able to open/create file "
                    + datafile.getName() + " for writing", ex);
        }
    }

    /**
     * Attempts to parse a play session from 3 terms in an array of strings.
     * (Date, Game, Time).
     * <p>
     * Will fail if the game name is not unique.
     *
     * @param playStrings array of data to attempt to read
     * @return play session from provided data
     */
    private static PlaySession parseShortSession(String[] playStrings, GameSet games) {
        String dateString = playStrings[0].trim();
        LocalDate date = PlaySession.parseDateTime(dateString);

        String gameString = playStrings[1].trim();

        Game game = games.getGame(gameString);

        String timeString = playStrings[2].trim();
        double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }


    /**
     * Parses play session data an array of 5 strings, with the format
     * (Date, Game Name, Platform, Year, Time).
     * <p>
     * The game (as identified by name, platform and year) must be included
     * in the {@link GameSet}.
     *
     * @param sessionStrings array Strings (Date, Game Name, Platform, Year, Time)
     *                       for a play session
     * @return play session based on the strings
     */
    private static PlaySession parseFullSession(String[] sessionStrings, GameSet games) {
        String dateString = sessionStrings[0].trim();
        LocalDate date = PlaySession.parseDateTime(dateString);

        String gameString = sessionStrings[1].trim();
        Game.Platform platform
                = Game.parsePlatform(sessionStrings[2].trim());
        int year = Game.parseYear(sessionStrings[3].trim());

        Game game = games.getGame(gameString, platform, year);

        String timeString = sessionStrings[4].trim();
        double time = PlaySession.parsePlayTime(timeString);

        return new PlaySession(game, date, time);
    }


    /**
     *
     * Writes the play session data as a string, with each session
     * on a line formatted <code>S, date, game name, game platform, game year, time</code>.
     *
     * @param sessions
     * @return
     */
    private static String writePlaySessionData(PlaySessionList sessions) {

        StringBuilder builder = new StringBuilder();
        for (PlaySession s : sessions.getPlaySessions()) {
            builder.append(String.format("S, %s, %s, %s, %s, %s%n",
                    PlaySession.DATE_FORMAT_YMD.print(s.getSessionDate()),
                    s.getGame().getName(),
                    s.getGame().getPlatform(),
                    s.getGame().getYear(),
                    s.getPlayTime()));
        }
        return builder.toString();
    }

    /**
     *
     * Writes the game data as a string, with each game on a line formatted
     * <code>G, name, platform, year</code>.
     *
     * @param set
     * @return
     */
    private static String writeGameData(GameSet set) {

        StringBuilder builder = new StringBuilder();

        for (Game g : set.getGames()) {
            builder.append(String.format("G, %s, %s, %s%n",
                    g.getName(),
                    g.getPlatform(),
                    g.getYear()));
        }

        return builder.toString();

    }


}
