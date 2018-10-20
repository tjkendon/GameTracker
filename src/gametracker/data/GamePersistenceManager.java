
package gametracker.data;

/**
 *
 * Loads and saves game data to a persistent store.
 * 
 */
public interface GamePersistenceManager {
    
    /**
     * 
     * Loads a game set from persistent storage and returns it.
     * 
     * @return a {@link GameSet} from the store this manager represents.
     */
    public GameSet load();
    
    
    /**
     * 
     * Saves the {@link GameSet} in the persistent storage represented 
     * by this manager.
     * 
     * @param s the set to save
     */
    public void saveGameSet(GameSet s);
    
    
}
