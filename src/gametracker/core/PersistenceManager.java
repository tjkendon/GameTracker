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

    public void load();

    public void save();

    public GameSet getGameSet();
    public PlaySessionList getPlaySessionList();


    public PersistenceManagerMenu getMenu();

}
