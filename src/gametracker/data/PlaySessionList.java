package gametracker.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * Holds a list of {@link PlaySession}s. Provides a chronological sequence and
 * the tools to organize and query the sessions.
 *
 * @author tjkendon
 */
public class PlaySessionList {

    private final List<PlaySession> sessions;

    /**
     * Creates a new PlaySessionList with an empty list of sessions.
     */
    public PlaySessionList() {
        this.sessions = new ArrayList<>();

    }

    /**
     * Creates a new PlaySessionList with a list of sessions copied from the original.
     *
     * Records that the data has been changed in the changed flag.
     *
     * @param sourceData the PlaySessionList to copy from.
     */
    public PlaySessionList(PlaySessionList sourceData) {
        this.sessions = new ArrayList<>(sourceData.getPlaySessions());
    }

    /**
     *
     * Adds a new session to the list.
     *
     * @param session the session to add
     */
    public void addPlaySession(PlaySession session) {
        this.sessions.add(session);

    }

    /**
     *
     * Returns a copy of the list of sessions in this data.
     *
     * @return a list with a copy of each session in the list
     */
    public List<PlaySession> getPlaySessions() {
        List<PlaySession> returnList = new ArrayList<>();

        for (PlaySession s : sessions) {
            returnList.add(s);
        }

        return returnList;
    }

    /**
     * 
     * Checks if the given session is contained in the list.
     * 
     * @param session the session to check for inclusion
     * @return true iff the session is contained.
     */
    boolean contains(PlaySession session) {
        return sessions.contains(session);
    }

    /**
     * 
     * Returns the hash code for the PlaySessionList.
     * 
     * @return the hash code calculated based on the set of {link PlaySession}s 
     * being held.
     * 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.sessions);
        return hash;
    }

    
        /**
     * 
     * Compares this GameSet to the other, checking if the sets of games
     * being held are the same.
     * 
     * @param other the GameSet to check.
     * @return true if the two sets of games are exactly the same.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        final PlaySessionList otherList = (PlaySessionList) other;
        return this.sessions.equals(otherList.sessions);
    }


    @Deprecated
    public static boolean containsMatchingContent(PlaySessionList a, PlaySessionList b) {

        if (a.getPlaySessions().size() == b.getPlaySessions().size()) {
            for (int i = 0; i < a.getPlaySessions().size(); i++) {
                if ((a.getPlaySessions().get(i).getGame().equals(
                        (b.getPlaySessions().get(i).getGame())))
                        && a.getPlaySessions().get(i).getSessionDate().equals(
                                b.getPlaySessions().get(i).getSessionDate())
                        && (a.getPlaySessions().get(i).getPlayTime()
                        == b.getPlaySessions().get(i).getPlayTime())) {

                } else {
                    return false;
                }

            }
            return true;
        }
        return false;
    }

}
