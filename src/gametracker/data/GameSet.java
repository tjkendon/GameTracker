package gametracker.data;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Class to hold a set of games.
 *
 * @author tjkendon
 */
public class GameSet {
    
    private Set<Game> games;
    
    /**
     * 
     * Creates a new empty set.
     * 
     */
    public GameSet() {
        games = new HashSet<>();
    }
    
    
    /**
     * Returns a copy of all games in the set.
     * @return 
     */
    public Set<Game> getGames() {
        Set<Game> returnSet = new HashSet<>();
        
        for (Game g : games) {
            returnSet.add(g);
        }
        
        return returnSet;
        
    }
    
    /**
     * 
     * Adds a new game to the set.
     * 
     * @param g 
     */
    public void addGame(Game g) {
        games.add(g);
    }
    
    /**
     * 
     * Returns a game the set if its name equals the given name. Throws
     * and IllegalArgumentException if the game cannot be found.
     * 
     * @param gameName
     * @return 
     */
    public Game getGame(String gameName) {
        for (Game g: games) {
            if (g.getName().equals(gameName)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Not able to find game " + gameName);
    }
    
    /**
     * 
     * Removes the game from the set.
     * 
     * @param g
     * @return 
     */
    public boolean removeGame(Game g) {
        return games.remove(g);
    }
         
    
}
