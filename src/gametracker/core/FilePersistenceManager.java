package gametracker.core;

import gametracker.data.GameSet;
import gametracker.data.PlaySessionList;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * Common implementation of <code>PersistenceManager</code> when the data is stored in files.
 *
 * Provides common behaviours for managing files and a model for handling game and session data.
 *
 */
public abstract class FilePersistenceManager implements PersistenceManager {

    /**
     * Preference key for the data file file name.
     */
    protected static final String DATA_FILE_PERF = "gametracker.datafile";

    /**
     * Main game set which data will be loaded into.
     */
    GameSet gameSet = new GameSet();

    /**
     * Main play session list which data will be loaded into.
     */
    PlaySessionList sessionList = new PlaySessionList();

    /**
     * The file handle for the data file.
     */
    File datafile;

    FilePersistenceManagerMenu menu;

    /**
     *
     * Creates new <code>FilePersistenceManager</code> with the given arguments.
     *<p>
     * In particular it looks for the -datafile/-f argument to update the data file
     * value. If the argument is found it updates the preference, if not found the
     * existing value is used.
     *
     * @param args the argument list with parameters for this manager.
     */
    public FilePersistenceManager(String[] args) {
        try {
            Options options = new Options();
            options.addOption(
                    "f",
                    "datafile",
                    true,
                    "the name of the data file");
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(options, args);
            if (cl.hasOption("datafile")) {
                Preferences.userNodeForPackage(FilePersistenceManager.class).
                        put(DATA_FILE_PERF, cl.getOptionValue("datafile"));
            }
        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "Error Parsing Command Line Arguments",
                    ex);
        }

    }

    /**
     * Returns the current data file name
     * @return the current data file name
     */
    public String getFileName() {
        return datafile.getName();
    }

    /**
     * Resets the game and session data to be new and empty.
     */
    public void clear() {
        gameSet = new GameSet();
        sessionList = new PlaySessionList();
    }

    /**
     * Updates the data file to a new file with the given name and updates the file name preference for
     * the system.
     *
     * @param fileName the name for the new data file
     */
    public void setFile(String fileName) {
        Preferences.userNodeForPackage(FilePersistenceManagerMenu.class).put(DATA_FILE_PERF, fileName);
        datafile = new File(fileName);
    }


    @Override
    public GameSet getGameSet() {
        return gameSet;
    }

    @Override
    public PlaySessionList getPlaySessionList() {
        return sessionList;
    }

    @Override
    public PersistenceManagerMenu getMenu() {
        return menu;
    }

    protected void setMenu(FilePersistenceManagerMenu menu) {
        this.menu = menu;
    }

}
