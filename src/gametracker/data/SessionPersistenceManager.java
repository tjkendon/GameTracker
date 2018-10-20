
package gametracker.data;

/**
 *
 * Loads and saves PlaySession Data to a persistent store.
 * 
 */
public interface SessionPersistenceManager {
    
     /**
     * 
     * Loads play data from persistent storage and returns it.
     * 
     * @return the game set this manager manages
     */
    public PlaySessionList load();
    
    
    /**
     * 
     * Saves the given sessions in persistent storage as defined by this manager.
     * 
     * @param sessions the {@link PlaySessionList} session data to store
     */
    public void savePlayData(PlaySessionList sessions);
    
}
