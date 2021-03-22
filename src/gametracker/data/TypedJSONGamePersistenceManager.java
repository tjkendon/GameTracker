package gametracker.data;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class TypedJSONGamePersistenceManager implements GamePersistenceManager{

    private File datafile;

    /**
     *
     * Creates a manager with the given file as its store for game data.
     *
     * @param datafile the handle for the file this manager should store
     * data in.
     */
    public TypedJSONGamePersistenceManager(File datafile) {
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

    @Override
    public GameSet load() {
        try (Scanner read = new Scanner(datafile)) {

            GameSet readGame = (GameSet) JsonReader.jsonToJava(read.nextLine());
            return readGame;
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException(
                    "Not able to find file " + datafile.getName(), ex);
        }
    }

    @Override
    public void saveGameSet(GameSet s) {
        if (datafile == null) {
            throw  new IllegalStateException("No File Set to Save To");
        }

        try (PrintStream save = new PrintStream(datafile)) {
            save.println(JsonWriter.objectToJson(s));
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("Not able to open/create file "
                    + datafile.getName() + " for writing", ex);
        }
    }
}
