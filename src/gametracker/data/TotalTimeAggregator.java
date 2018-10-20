/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 
 * Produces an aggregate of the total time each game has been played for over
 * all sessions in the {@link PlaySessionList}.
 * 
 */
public class TotalTimeAggregator extends Aggregator {
    
    /**
     * The flag to indicate the {@link PlayAggregate} contains the total time
     * aggregate.
     */
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.TOTAL_TIME;

    /**
     * Creates a new aggregator with the given {@link PlaySessionList} as source
     * data.
     * @param sourceData the data this aggregator will aggregate.
     */
    public TotalTimeAggregator(PlaySessionList sourceData) {
        super(sourceData);
    }
    
    /**
     * 
     * Produces a {@link PlayAggregate} which includes the total length of time
     * each game has been played for in all sessions in the source
     * {@link PlaySessionList}.
     * 
     * @return an aggregate of the total time each game as been played in the
     * source data.
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
            for (PlaySession session: sourceData.getPlaySessions()) {
                if (session.getGame().equals(game)) {
                    totalTime += session.getPlayTime();
                }
            }
            returnData.putAggregate(game, type, totalTime);
        }
        
        return returnData;
        
                
        
        
    }
    
}
