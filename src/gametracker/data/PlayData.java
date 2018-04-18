
package gametracker.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Holds a list of PlaySessions.
 * 
 * @author tjkendon
 */
public class PlayData {
    
    private final List<PlaySession> sessions;

    /**
     * Creates a new PlayData with an empty list of sessions.
     */
    public PlayData() {
        this.sessions = new ArrayList<>();
    }
    
    /**
     * 
     * Adds a new session to the list.
     * 
     * @param session 
     */
    public void addPlaySession(PlaySession session) {
        this.sessions.add(session);
    }
    
    
    /**
     * 
     * Returns a copy of the list of sessions in this data.
     * 
     * @return 
     */
    public List<PlaySession> getPlaySessions() {
        List<PlaySession> returnList = new ArrayList<>();
        
        for (PlaySession s : sessions) {
            returnList.add(new PlaySession(s));
        }
        
        return returnList;
    }
    
    
    
}
