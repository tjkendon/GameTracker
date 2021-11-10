package gametracker.core;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import gametracker.cli.CLI;
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

public class TypedJSONPersistenceManager extends FilePersistenceManager {

    private static final String DATA_FILE_DEFAULT = "gametracker.json";


    public TypedJSONPersistenceManager(String[] args) {
        super(args);

        datafile = new File(Preferences.userNodeForPackage(FilePersistenceManager.class).
                get(DATA_FILE_PERF, DATA_FILE_DEFAULT));

        setMenu(new TypedJSONPersistenceManagerMenu(this));

    }

    @Override
    public void load() {
        try (Scanner read = new Scanner(datafile)) {
            gameSet = (GameSet) JsonReader.jsonToJava(read.nextLine());
            sessionList = (PlaySessionList) JsonReader.jsonToJava(read.nextLine());
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

    @Override
    public void save() {
        if (datafile == null) {
            throw  new IllegalStateException("No File Set to Save To");
        }

        try (PrintStream save = new PrintStream(datafile)) {
            save.println(JsonWriter.objectToJson(gameSet));
            save.println(JsonWriter.objectToJson(sessionList));
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Not able to open/create file "
                    + datafile.getName() + " for writing", ex);
        }
    }

}
