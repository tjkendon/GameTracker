package gametracker;

import java.util.Objects;
import org.joda.time.DateTime;

/**
 *
 * Basic data about a single play session. Includes the game that was played,
 * the date of the session and how long (in hours) the session went on for.
 *
 * @author tjkendon
 */
public class PlaySession {

    private final Game game;
    private final DateTime sessionDate; // date the session was played on
    private final double playTime; // the length of time (in hours)

    /**
     *
     * Creates a new PlaySession Instance
     *
     * @param game the game that was played
     * @param sessionDate the date the games was played
     * @param playTime how long (in hours) the game was played for
     */
    public PlaySession(Game game, DateTime sessionDate, double playTime) {
        this.game = game;
        this.sessionDate = sessionDate;
        this.playTime = playTime;
    }

    PlaySession(PlaySession original) {
        this.game = original.game;
        this.sessionDate = original.sessionDate;
        this.playTime = original.playTime;

    }

    public Game getGame() {
        return game;
    }

    public DateTime getSessionDate() {
        return sessionDate;
    }

    public double getPlayTime() {
        return playTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.game);
        hash = 29 * hash + Objects.hashCode(this.sessionDate);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.playTime)
                ^ (Double.doubleToLongBits(this.playTime) >>> 32));
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
        final PlaySession other = (PlaySession) obj;
        if (Double.doubleToLongBits(this.playTime)
                != Double.doubleToLongBits(other.playTime)) {
            return false;
        }
        if (!Objects.equals(this.game, other.game)) {
            return false;
        }
        if (!Objects.equals(this.sessionDate, other.sessionDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sessionDate.toString("dd/MM/YYYY")
                + ", " + game.getName()
                + ", " + playTime;
    }

}
