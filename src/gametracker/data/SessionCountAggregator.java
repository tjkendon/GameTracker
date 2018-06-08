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
 * @author tjkendon
 */
public class SessionCountAggregator implements Aggregator {
    
    PlayData sourceData;
    
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.TOTAL_COUNT;
    
    SessionCountAggregator(PlayData source) {
        this.sourceData = source;
    }

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