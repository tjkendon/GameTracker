package gametracker.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * Loads and saves game data in a {@link GameSet} using a comma-separated 
 * file in the host file system.
 * 
 */
public class CSVGamePersistenceManager implements GamePersistenceManager {

    private File datafile;

    /**
     * 
     * Creates a manager with the given file as its store for game data.
     * 
     * @param datafile the handle for the file this manager should store
     * data in.
     */
    public CSVGamePersistenceManager(File datafile) {
        this.datafile = datafile;
    }

    /**
     * 
     * Returns the handle for the file this manager is using as a persistent
     * store.
     * 
     * @return the file handle for the data file
     */
    public File getDatafile() {
        return datafile;
    }

    /**
     * 
     * Changes the file this manager is using as a persistent store to the 
     * new file given by the handle.
     * 
     * @param datafile the file handle to change this managers store to
     */
    public void setDatafile(File datafile) {
        this.datafile = datafile;
    }
    
    /**
     * 
     * Loads the game set stored in the file represented by this manager.
     * 
     * @return the games stored in the file as a {@link GameSet}
     */

    @Override
    public GameSet load() {

        GameSet returnSet = new GameSet();

        if (datafile == null) {
            throw new IllegalStateException("No File Set to Load From");
        }


        try {
            Scanner scanner = new Scanner(datafile);

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String gameString = scanner.nextLine();
                String[] gameStrings = gameString.split(",");
                try {
                    String gameName = gameStrings[0].trim();
                    Game.Platform platform = Game.parsePlatform(gameStrings[1].trim());
                    int year = Game.parseYear(gameStrings[2].trim());
                    returnSet.addGame(new Game(gameName, platform, year));
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

        return returnSet;

    }

    /**
     * 
     * Saves the games in the {@link GameSet} in the 
     * CSV file this manager is set to.
     * 
     * @param gameSet the games to save.
     */
    @Override
    public void saveGameSet(GameSet gameSet) {
        
        if (datafile == null) {
            throw  new IllegalStateException("No File Set to Save To");
        }

        try (PrintWriter writer = new PrintWriter(datafile)) {

            for (Game g : gameSet.getGames()) {
                writer.printf("%s, %s, %s%n", 
                        g.getName(), 
                        g.getPlatform(), 
                        g.getYear());
            }

        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Not able to open/create file "
                    + datafile.getName() + " for writing", ex);
        }


    }

}
