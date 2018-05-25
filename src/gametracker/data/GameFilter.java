/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tjkendon
 */
public class GameFilter implements Filter {

    public List<Game> filterGames = new ArrayList<>();;

    /**
     * 
     * Creates a new filter with no games to filter 
     * 
     * If used to filter it will return an empty PlayData.
     * 
     */
    GameFilter() {
        
    }
    
    
    /**
     * 
     * Creates a new filter with the games listed in the arguments in the filter
     *
     * When used, it will a data set with any session using any game in the
     * filter included.
     * 
     * @param games the game (or games) to add
     */
    GameFilter(Game... games) {
        addAllGames(Arrays.asList(games));
    }

    /**
     * 
     * Creates a new filter with the games in the list in the filter
     *
     * When used, it will a data set with any session using any game in the
     * filter included.
     * 
     * @param games the games to add
     */
    GameFilter(List<Game> games) {
        addAllGames(games);
    }
    
    protected final void addAllGames(List<Game> games) {
        for (Game g : games) {
            filterGames.add(g);
        }
    }
    
    
    @Override
    public PlayData filter(PlayData source) {
        
        PlayData returnData = new PlayData();
        
        source.getPlaySessions().stream().filter((session) 
                -> (filterGames.contains(
                        session.getGame()))).forEachOrdered((session) 
                        -> {
            returnData.addPlaySession(session);
        });
        
        return returnData;
        
    }
    
}
