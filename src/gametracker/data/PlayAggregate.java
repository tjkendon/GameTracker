package gametracker.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * Class to hold data about a game aggregated from PlayData. Contains a mapping
 * of games to data, and each piece of data is constructed of a Type (listed
 * below and a value - a double).
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



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.data);
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
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    
    

}
