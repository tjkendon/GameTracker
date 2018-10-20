
package gametracker.data;

/**
 *
 * Allows Play Session Data to be loaded and saved to a persistent store.
 * 
 * @author tjkendon
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
