/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.List;

/**
 *
 * @author tjkendon
 */
public class GameFilter implements Filter {

    public Game filterGame;

    
    
    public GameFilter(Game filterGame) {
        this.filterGame = filterGame;
    }

    GameFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    GameFilter(Game... games) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    GameFilter(List<Game> games) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public PlayData filter(PlayData source) {
        
        PlayData returnData = new PlayData();
        
        source.getPlaySessions().stream().filter((s) -> 
                (s.getGame().equals(filterGame))).forEachOrdered((s) -> {
            returnData.addPlaySession(s);
        });
        
        return returnData;
        
    }
    
}
