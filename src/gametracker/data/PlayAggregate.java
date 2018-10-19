package gametracker.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Holds data about games as aggregated from {@link PlayData}. 
 * 
 * Can contain many different types of data, each identified by the 
 * {@link PlayAggregate.AggregateType}, then for each game in a source data
 * the value of the aggregate for that type is stored. All data is assumed
 * to be {@link double}s.
 * 
 *
 * @author tjkendon
 */
public class PlayAggregate {

    private final Map<Game, Map<AggregateType, Double>> data;

    /**
     * Labels the different types of data the system can contain.
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
     * Creates a new data with no date included.
     */
    public PlayAggregate() {
        data = new HashMap<>();
    }

    /**
     *
     * Adds or updates an data value for the given game.
     *
     * @param game the game to update
     * @param type the type of data to update
     * @param value the new or updated value
     */
    public void putAggregate(Game game, AggregateType type, double value) {

        if (!data.containsKey(game)) {
            data.put(game, new HashMap<>());
        }

        data.get(game).put(type, value);
    }

    /**
     *
     * Merges the other aggregates into the data for this one. 
     *
     * @param others the other play aggregates to merge data from
     */
    public void mergeAggregates(PlayAggregate... others) {

        for (PlayAggregate other : others) {
            Map<Game, Map<AggregateType, Double>> aggregates = other.getAggregates();
            Set<Game> keySet = aggregates.keySet();
            for (Game game : keySet) {
                Map<AggregateType, Double> get = aggregates.get(game);
                Set<AggregateType> keySet1 = get.keySet();
                for (AggregateType type : keySet1) {
                    putAggregate(game, type, aggregates.get(game).get(type));
                }
            }
        }

    }

    /**
     *
     * Returns a copy of the full map of data data.
     *
     * @return a full map of game, data pairs
     */
    public Map<Game, Map<AggregateType, Double>> getAggregates() {
        return new HashMap<>(data);
    }

    /**
     *
     * Returns a copy of the data fields for a particular game
     *
     * @param game the game to get the data for
     * @return a map of type, value pairs
     */
    public Map<AggregateType, Double> getAggregatesForGame(Game game) {
        return new HashMap<>(data.get(game));
    }

    /**
     * 
     * Calculates a hash code based on the stored data.
     * 
     * @return the hash code calculated based on the data stored in this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.data);
        return hash;
    }

    /**
     * 
     * Checks if this object is equal to the other.
     * 
     * @param other the object to compare to
     * @return true if the data stored in this aggregate is the same as the 
     * other
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
        final PlayAggregate otherAgg = (PlayAggregate) other;
        if (!Objects.equals(this.data, otherAgg.data)) {
            return false;
        }
        return true;
    }
    
    

}
