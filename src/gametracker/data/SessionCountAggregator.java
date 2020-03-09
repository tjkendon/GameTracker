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
 * Produces an aggregate of the count of sessions in the given PlaySessionList.
 * 
 */
public class SessionCountAggregator extends Aggregator {
    
    
    /**
     * 
     * The flag used to indicate that the PlayAggregate includes the session
     * count.
     * 
     */
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.TOTAL_COUNT;

    /**
     * 
     * Creates a new Aggregator with the given source PlaySessionList.
     * 
     * @param sourceData the PlaySessionList to be aggregated.
     */
    public SessionCountAggregator(PlaySessionList sourceData) {
        super(sourceData);
    }
    
    /**
     * 
     * Produces a {@link PlayAggregate} which includes the number of sessions
     * for each game in the source data.
     * 
     * @return an aggregate with the number of sessions for each game.
     */

    @Override
    public PlayAggregate aggregate() {
        
        Set<Game> games = new HashSet<>();
        
        PlayAggregate returnData = new PlayAggregate();
        
        for (PlaySession session : sourceData.getPlaySessions()) {
            games.add(session.getGame());
        }
        
        for (Game game : games) {
            double count = 0;
            for (PlaySession session: sourceData.getPlaySessions()) {
                if (session.getGame().equals(game)) {
                    count++;
                }
            }
            returnData.putAggregate(game, type, count);
        }
        
        return returnData;
        
                
        
        
    }
    
}
