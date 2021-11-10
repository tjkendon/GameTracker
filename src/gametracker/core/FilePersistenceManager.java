package gametracker.core;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import gametracker.data.GameSet;
import gametracker.data.PlaySessionList;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public abstract class FilePersistenceManager implements UnifiedPersistenceManager {
    protected static final String DATA_FILE_PERF = "gametracker.datafile";

    protected GameSet gameSet = new GameSet();
    protected PlaySessionList sessionList = new PlaySessionList();

    protected File datafile;

    FilePersistenceManagerMenu menu;


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

    public String getFileName() {
        return datafile.getName();
    }

    public void clear() {
        gameSet = new GameSet();
        sessionList = new PlaySessionList();
    }

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
