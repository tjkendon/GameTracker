
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
    public PlayData load();
    
    
    /**
     * 
     * Saves the given sessions in persistent storage as defined by this manager.
     * 
     * @param sessions the {@link PlayData} session data to store
     */
    public void savePlayData(PlayData sessions);
    
}
