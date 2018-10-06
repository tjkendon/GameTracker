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

    /**
     * Creates a new PlayData with an empty list of sessions.
     */
    public PlayData() {
        this.sessions = new ArrayList<>();

    }

    /**
     * Creates a new PlayData with a list of sessions copied from the original.
     *
     * Records that the data has been changed in the changed flag.
     *
     * @param sourceData
     */
    public PlayData(PlayData sourceData) {
        this.sessions = new ArrayList<>(sourceData.getPlaySessions());
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
            returnList.add(s);
        }

        return returnList;
    }

    boolean contains(PlaySession session) {
        return sessions.contains(session);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.sessions);
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


    @Deprecated
    public static boolean containsMatchingContent(PlayData a, PlayData b) {

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
