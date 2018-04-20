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

    private final File datafile;

    public CSVGamePersistenceManager(File datafile) {
        this.datafile = datafile;
    }

    
    @Override
    public GameSet load() {

        GameSet returnSet = new GameSet();

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
            System.err.println("Not able to find file " + datafile.getName());
        }

        return returnSet;

    }

    @Override
    public void saveGameSet(GameSet gameSet) {
        try (PrintWriter writer = new PrintWriter(datafile)) {
            
            for (Game g : gameSet.getGames()) {
                writer.printf("%s, %s, %s%n", g.getName(), g.getPlatform(), g.getYear());
                System.out.printf("%s, %s, %s%n", g.getName(), g.getPlatform(), g.getYear());
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("Not able to open/create file " 
                    + datafile.getName() + " for writing");
        }
        
    }

}