
package gametracker.data;

/**
 *
 * Interface for classes managing play session data.
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
     * @param sessions the data to save
     */
    public void savePlayData(PlayData sessions);
    
}
