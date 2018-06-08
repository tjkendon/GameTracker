package gametracker.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * Class to hold data about a game aggregated from PlayData. Contains a mapping
 * of games to data, and each piece of data is constructed of a Type (listed
 * below and a value - a double).
 *
 * @author tjkendon
 */
public class PlayAggregate {

    private final Map<Game, Map<AggregateType, Double>> aggregate;

    /**
     * Labels the different types of aggregate the system can contain.
     */
    public enum AggregateType {
        /** 
         * Value is the total time a game was played in the PlayData.
         */
        TOTAL_TIME,
        /**
         * Value is the total number of times the game was played in the 
         * PlayData.
         */
        TOTAL_COUNT,
        /**
         * Value is the mean time the game was played in in the PlayData.
         */
        AVERAGE_TIME,
        /**
         * Value is the median time the game was played in the PlayData.
         */
        MEDIAN_TIME
    }

    /**
     * Creates a new aggregate with no date included.
     */
    public PlayAggregate() {
        aggregate = new HashMap<>();
    }

    /**
     * 
     * Adds or updates an aggregate value for the given game.
     * 
     * @param game the game to update
     * @param type the type of data to update
     * @param value the new or updated value
     */
    public void putAggregate(Game game, AggregateType type, double value) {

        if (!aggregate.containsKey(game)) {
            aggregate.put(game, new HashMap<>());
        }
        
        aggregate.get(game).put(type, value);
    }
    
    /**
     * 
     * Returns a copy of the full map of aggregate data.
     * 
     * @return a full map of game, data pairs 
     */
    public Map<Game, Map<AggregateType, Double>>  getAggregates() {
        return new HashMap<> (aggregate);
    }
    
    /**
     * 
     * Returns a copy of the data fields for a particular game
     * 
     * @param game the game to get the data for
     * @return a map of type, value pairs
     */
    public Map<AggregateType, Double> getAggregatesForGame(Game game) {
        return new HashMap<>(aggregate.get(game));
    }

    /**
     * 
     * Resets the aggregate to its initial state, with no data included.
     * 
     */
    public void clear() {
        aggregate.clear();
    }
    
    /** 
     * 
     * Resets the data for a particular game. So that there will be no
     * data included for the particular game.
     * 
     * @param game the game to reset the data for.
     */
    public void clearGame(Game game) {
        if (aggregate.containsKey(game)) {
            aggregate.get(game).clear();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.aggregate);
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
        final PlayAggregate other = (PlayAggregate) obj;
        if (!Objects.equals(this.aggregate, other.aggregate)) {
            return false;
        }
        return true;
    }
    
    
    
}
