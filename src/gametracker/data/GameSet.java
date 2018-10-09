package gametracker.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Holds a set of {@link Game}s. Provides utilities to organize the collection
 * of games and to query for specific games.
 *
 */
public class GameSet {

    /**
     * The set which holds all of the games for the GameSet,
     */
    private final Set<Game> games;
    


    /**
     *
     * Creates a new GameSet, which is initially empty.
     *
     */
    public GameSet() {
        games = new HashSet<>();
        

    }

    /**
     * 
     * Returns a set of all games included in the GameSet
     *
     * @return a new list which is an exact copy of the included Games
     * 
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
     * @param g the game to add to the set.
     */
    public void addGame(Game g) {
        games.add(g);
        

    }

    /**
     *
     * Returns a game the set if its name equals the given name. Throws and
     * IllegalArgumentException if the game cannot be found or if there are
     * several games with a matching name.
     *
     * @param gameName name of the game (case sensitive) 
     * @return the game with the given name provided there is exactly 1
     * 
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
     * 
     * Returns the one game contained in the set that fully matches the name
     * platform and year.
     * 
     * @param gameName name of the game (case sensitive) 
     * @param p the platform
     * @param year the year 
     * @return the game if there is a match or an illegal argument exception 
     * if not
     * 
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
     * 
     * Returns all games that match (case sensitive) the given name
     * 
     * @param gameName name of the game to match
     * @return all matching games
     * 
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
     * 
     * Returns all games that match the given platform
     * 
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
     * Removes the game from the set.
     *
     * @param game the game to remove from the set
     * @return true if the game was able to be removed from the set
     */
    public boolean removeGame(Game game) {
        boolean result = games.remove(game);
        
        return result;   
    }
    
    /**
     * 
     * Checks if the given game is this GameSet.
     * 
     * @param game the game to check to see if its is included in the set
     * @return true if the game is found in the set
     * 
     */
    public boolean contains(Game game) {
        return games.contains(game);
    }

    /**
     * 
     * Returns the number of games in the set
     * 
     * @return the size of the Game Set
     */
    public int size() {
        return games.size();
    }

    /**
     * 
     * Checks if the GameSet is empty.
     * 
     * @return true if .size() == 0
     * 
     */
    public boolean isEmpty() {
        return games.isEmpty();
    }

    /**
     * 
     * Returns the hash code for the GameSet.
     * 
     * @return the hash code calculated based on the set of games being held.
     * 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.games);
        return hash;
    }

    /**
     * 
     * Compares this GameSet to the other, checking if the sets of games
     * being held are the same.
     * 
     * @param other the GameSet to check.
     * @return true if the two sets of games are exactly the same.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        final GameSet otherSet = (GameSet) other;
        if (!Objects.equals(this.games, otherSet.games)) {
            return false;
        }
        return true;
    }

}
