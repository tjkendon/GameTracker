package gametracker.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Produces an aggregate of the median of all play sessions for each game
 * in a source {@link PlaySessionList}.
 * <p>
 * For each game in the data, the median is calculated, representing the middle
 * most value for the play sessions for the game.
 * 
 */
public class MedianTimeAggregator extends Aggregator {
    
    
    /**
     * The flag used to identify data produced by this aggregator. 
     */
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.MEDIAN_TIME;

    /**
     * Creates a new aggregator with the given {@link sourceData} data to 
     * aggregate.
     * 
     * @param sourceData the data this aggregator will aggregate.
     */
    public MedianTimeAggregator(PlaySessionList sourceData) {
        super(sourceData);
    }
    
    /**
     * 
     * Calculates the median play time for each game and adds the data
     * to the returned PlayAggregate. Takes each game found in the data
     * and finds all of the session times for the game to calculate the 
     * median length play session for the game.
     * 
     * @return A {@link PlayAggregate} containing the median length of time
     * each game has been played for.
     */
    @Override
    public PlayAggregate aggregate() {
        
        Set<Game> games = new HashSet<>();
        
        PlayAggregate returnData = new PlayAggregate();
        
        for (PlaySession session : sourceData.getPlaySessions()) {
            games.add(session.getGame());
        }
        
        for (Game game : games) {
            ArrayList<Double> values = new ArrayList<>();
            for (PlaySession session: sourceData.getPlaySessions()) {
                if (session.getGame().equals(game)) {
                    values.add(session.getPlayTime());
                }
            }
            Collections.sort(values);
            int midpoint = (values.size() - 1) / 2;
            double median;
            if (values.size() % 2 == 1) {
                median = values.get(midpoint);
            } else {
                median = (values.get(midpoint) + values.get(midpoint + 1)) / 2;
            }
            returnData.putAggregate(game, type, median);
        }
        
        return returnData;
        
        
    }
    
}
