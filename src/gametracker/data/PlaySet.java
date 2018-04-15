
package gametracker.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tjkendon
 */
public class PlaySet {
    
    //TODO - Add flitering system
    //TODO - Add aggregation system
    
    private final List<PlaySession> sessions;

    public PlaySet() {
        this.sessions = new ArrayList<>();
    }
    
    public void addPlaySession(PlaySession session) {
        this.sessions.add(session);
    }
    
    public List<PlaySession> getPlaySessions() {
        List<PlaySession> returnList = new ArrayList<>();
        
        for (PlaySession s : sessions) {
            returnList.add(new PlaySession(s));
        }
        
        return returnList;
    }
    
    
    
}
