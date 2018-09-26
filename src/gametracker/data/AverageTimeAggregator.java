package gametracker.data;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Produces an aggregate of average (mean) aggregates based 
 * on the {@link PlayData} information. For each game in the data the average
 * session play length is calculated and stored for that game in the aggregate
 * data.
 * 
 */
public class AverageTimeAggregator extends Aggregator {
    
    /**
     * The Aggregate Type this aggregator tags its data with.
     */
    private final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.AVERAGE_TIME;

    /**
     * Creates a new Aggregator which will aggregate the given sourceData by 
     * taking the mean for each game's play sessions.
     * 
     * @param sourceData a collection of PlayData sessions which 
     * will be averaged.
     */
    public AverageTimeAggregator(PlayData sourceData) {
        super(sourceData);
    }
    

    /**
     * 
     * Produces a PlayAggregate which includes the average length of time
     * each game has been played for in each of its sessions. The aggregate
     * is generated based on the data provided when constructed.
     * 
     * @return a {@link PlayAggregate} filled with average play times for each game.
     */
    @Override
    public PlayAggregate aggregate() {
        
        Set<Game> games = new HashSet<>();
        
        PlayAggregate returnData = new PlayAggregate();
        
        for (PlaySession session : sourceData.getPlaySessions()) {
            games.add(session.getGame());
        }
        
        for (Game game : games) {
            double totalTime = 0;
            double count = 0;
            for (PlaySession session: sourceData.getPlaySessions()) {
                if (session.getGame().equals(game)) {
                    totalTime += session.getPlayTime();
                    count += 1;
                }
            }
            returnData.putAggregate(game, type, totalTime / count);
        }
        
        return returnData;
        
                
        
        
    }
    
}
