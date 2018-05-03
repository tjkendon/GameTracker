package gametracker.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author tjkendon
 */
public class CSVGamePersistenceManager implements GamePersistenceManager {

    private File datafile;

    public CSVGamePersistenceManager() {

    }

    public CSVGamePersistenceManager(File datafile) {
        this.datafile = datafile;
    }

    public File getDatafile() {
        return datafile;
    }

    public void setDatafile(File datafile) {
        this.datafile = datafile;
    }

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

    @Override
    public void saveGameSet(GameSet gameSet) {
        
        if (datafile == null) {
            throw  new IllegalStateException("No File Set to  Save To");
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
