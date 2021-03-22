package gametracker.data;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class TypedJSONSessionPersistenceManager implements SessionPersistenceManager {

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
    public TypedJSONSessionPersistenceManager(File datafile, GameSet gameSet) {
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

        if (datafile == null) {
            throw new IllegalStateException(
                    "Datafile not set to load session data");
        }

        if (gameSet == null) {
            throw new IllegalStateException(
                    "GameSet not set to load session data");
        }

        try (Scanner read = new Scanner(datafile)) {

            PlaySessionList sessions = (PlaySessionList) JsonReader.jsonToJava(read.nextLine());
            return sessions;
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException(
                    "Not able to find file " + datafile.getName(), ex);
        }
    }

    @Override
    public void savePlayData(PlaySessionList sessions) {
        if (datafile == null) {
            throw new IllegalStateException(
                    "No file set to save session data");
        }
        try (PrintStream save = new PrintStream(datafile)) {
            save.println(JsonWriter.objectToJson(sessions));
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Not able to open/create file "
                    + datafile.getName() + " for writing", ex);
        }
    }
}
