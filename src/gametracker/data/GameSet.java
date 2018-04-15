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
    
    public GameSet() {
        games = new HashSet<>();
    }
    
    public Set<Game> getGames() {
        Set<Game> returnSet = new HashSet<>();
        
        for (Game g : games) {
            returnSet.add(g);
        }
        
        return returnSet;
        
    }
    
    public void addGame(Game g) {
        games.add(g);
    }
    
    public Game getGame(String gameName) {
        for (Game g: games) {
            if (g.getName().equals(gameName)) {
                return g;
            }
        }
        return null;
    }
    
    public boolean removeGame(Game g) {
        return games.remove(g);
    }
         
    
}
