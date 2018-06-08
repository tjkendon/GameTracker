
package gametracker.data;

/**
 *
 * Interface for classes managing persistence of game data
 * 
 * @author tjkendon
 */
public interface GamePersistenceManager {
    
    /**
     * 
     * Loads a game set from persistent storage and returns it.
     * 
     * @return the game set this manager manages
     */
    public GameSet load();
    
    
    /**
     * 
     * Saves the given set in persistent storage as defined by this manager.
     * 
     * @param s the set to save
     */
    public void saveGameSet(GameSet s);
    
    
}
