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
public class AverageTimeAggregator extends Aggregator {
    
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.AVERAGE_TIME;

    public AverageTimeAggregator(PlayData sourceData) {
        super(sourceData);
    }
    

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
