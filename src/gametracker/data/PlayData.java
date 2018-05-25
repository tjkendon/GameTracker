
package gametracker.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * Holds a list of PlaySessions.
 * 
 * @author tjkendon
 */
public class PlayData {
    
    private final List<PlaySession> sessions;
    private boolean changed;


    /**
     * Creates a new PlayData with an empty list of sessions.
     */
    public PlayData() {
        this.sessions = new ArrayList<>();
        changed = false;

    }

    /**
     * Creates a new PlayData with a list of sessions copied from the original.
     * 
     * Records that the data has been changed in the changed flag.
     * 
     * @param sourceData 
     */
    PlayData(PlayData sourceData) {
        this.sessions = new ArrayList<>();
        sourceData.getPlaySessions().forEach((s) -> {
            this.sessions.add(new PlaySession(s));
        });
        changed = true;
    }
    
    /**
     * 
     * Adds a new session to the list.
     * 
     * @param session 
     */
    public void addPlaySession(PlaySession session) {
        this.sessions.add(session);
        changed = true;

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

    boolean contains(PlaySession session) {
        return sessions.contains(session);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.sessions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayData other = (PlayData) obj;
        return this.sessions.equals(other.sessions);
    }

    public boolean hasChanged() {
        return changed;
    }
    
    public void resetChanged() {
        changed = false;
    }
    
    
    
    
}
