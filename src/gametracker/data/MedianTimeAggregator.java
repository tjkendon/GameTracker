/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tjkendon
 */
public class MedianTimeAggregator implements Aggregator {
    
    PlayData sourceData;
    
    public final PlayAggregate.AggregateType type = 
            PlayAggregate.AggregateType.AVERAGE_TIME;
    
    public MedianTimeAggregator(PlayData source) {
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
            ArrayList<Double> values = new ArrayList<>();
            for (PlaySession session: sourceData.getPlaySessions()) {
                if (session.getGame().equals(game)) {
                    values.add(session.getPlayTime());
                }
            }
            Collections.sort(values);
            int midpoint = (values.size() - 1) / 2;
            double median = -1;
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
