package gametracker.core;

import gametracker.data.GameSet;
import gametracker.data.PlaySessionList;

/**
 *
 * Provides the basic interface for managing persistence of game and play session data.
 *
 * Assumes that there is a point in time where data will need to be gathered from the storage in the
 * implementation and equally that there is a point where data will be pushed to storage. Provides
 * access to the games and session information.
 *
 */
public interface PersistenceManager {

    /**
     *
     * Brings data from storage into the manager.
     *
     * Assumes that the data available internally will be up-to-date with the storage when
     * this method is called.
     *
     */
    void load();

    /**
     *
     * Stores data in the manager.
     *
     * Assumes that after this method is called data in storage will be up-to-date with what
     * is in the manager.
     *
     */
    void save();

    public GameSet getGameSet();
    public PlaySessionList getPlaySessionList();


    public PersistenceManagerMenu getMenu();

}
