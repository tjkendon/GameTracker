package gametracker.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Class to hold a set of games.
 *
 * @author tjkendon
 */
public class GameSet {

    private Set<Game> games;
    private boolean changed;


    /**
     *
     * Creates a new empty set.
     *
     */
    public GameSet() {
        games = new HashSet<>();
        changed = false;

    }

    /**
     * Returns a copy of all games in the set.
     *
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
        changed = true;

    }

    /**
     *
     * Returns a game the set if its name equals the given name. Throws and
     * IllegalArgumentException if the game cannot be found or if there are
     * several games with a matching name.
     *
     * @param gameName name of the game (case sensitive) 
     * @return the game with the given name provided there is exactly 1
     */
    public Game getGame(String gameName) {
        List<Game> returnGames = new ArrayList<>();
        for (Game g : games) {
            if (g.getName().equals(gameName)) {
                returnGames.add(g);
            }
        }
        if (returnGames.size() == 1) {
            return returnGames.get(0);
        } else if (returnGames.isEmpty()) {
            throw new IllegalArgumentException(
                    "Not able to find game " + gameName);
        } else {
            throw new IllegalArgumentException(
                    "Multiple games with name " + gameName);
        }

    }

    
    
    /**
     * Returns the one game contained in the set that fully matches the name
     * platform and year.
     * @param gameName name of the game (case sensitive) 
     * @param p the platform
     * @param year the year 
     * @return the game if there is a match or an illegal argument exception 
     * if not
     */
    public Game getGame(String gameName, Game.Platform p, int year) {

        for (Game g : games) {
            if (g.getName().equals(gameName)
                    && g.getPlatform().equals(p)
                    && g.getYear() == year) {
                return g;
            }
        }

        throw new IllegalArgumentException("Not able to find game "
                + gameName + " (" + p + ") - " + year);

    }

    /**
     * Returns all games that match (case sensitive) the given name
     * @param gameName name of the game to match
     * @return all matching games
     */
    public Set<Game> getGames(String gameName) {
        Set<Game> returnSet = new HashSet<>();

        games.stream().filter((g)
                -> (g.getName().equals(gameName))).forEachOrdered((g) -> {
            returnSet.add(g);
        });

        return returnSet;
    }

    /**
     * Returns all games that match the given platform
     * @param platform the platform to match
     * @return all matching games
     */
    public Set<Game> getGames(Game.Platform platform) {
        Set<Game> returnSet = new HashSet<>();

        games.stream().filter((g)
                -> (g.getPlatform().equals(platform))).forEachOrdered((g) -> {
            returnSet.add(g);
        });

        return returnSet;
    }

    /**
     * Returns all games that match the given year
     * @param year the year to match
     * @return all matching games
     */
    public Set<Game> getGames(int year) {
        Set<Game> returnSet = new HashSet<>();

        games.stream().filter((g)
                -> (g.getYear() == year)).forEachOrdered((g) -> {
            returnSet.add(g);
        });

        return returnSet;
    }
    
    /**
     * 
     * Returns all games that begin with the given string;
     * 
     * @param nameStart the portion of game name to match
     * @return all games whose names match up to nameStart
     */
    public Set<Game> getGamesPartial(String nameStart) {
        
        Set<Game> returnSet = new HashSet<>();
        
        for (Game g : games) {
            if (g.getName().startsWith(nameStart)) {
                returnSet.add(g);
            }
        }
        
        return returnSet;
        
    }

    /**
     *
     * Removes the game from the set.
     *
     * @param g
     * @return
     */
    public boolean removeGame(Game g) {
        boolean result = games.remove(g);
        if (result) {
            changed = true;
        }
        return result;   
    }
    
    public boolean contains(Game g) {
        return games.contains(g);
    }

    public int size() {
        return games.size();
    }

    public boolean isEmpty() {
        return games.isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.games);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameSet other = (GameSet) obj;
        if (!Objects.equals(this.games, other.games)) {
            return false;
        }
        return true;
    }

    public boolean hasChanged() {
        return changed;
    }
    
    public void resetChanged() {
        changed = false;
    }
    
    
    
    

}
