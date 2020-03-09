package gametracker.data;

import java.util.Objects;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * Contains basic data about a single play session. This includes the game that
 * was played, the date of the session, and how long (in hours) the session went
 * on for.
 *
 */
public class PlaySession {

    /**
     *
     * "Usual" format used to read date.
     *
     */
    public static final DateTimeFormatter DATE_FORMAT_YMD
            = DateTimeFormat.forPattern("yyyy/MM/dd");

    /**
     *
     * "Alternate" format used to read date.
     *
     */
    public static final DateTimeFormatter DATE_FORMAT_DMY
            = DateTimeFormat.forPattern("dd/MM/yyyy");

    private final Game game;
    private final LocalDate sessionDate; // date the session was played on
    private final double playTime; // the length of time (in hours)

    /**
     *
     * Creates a new PlaySession instance.
     *
     * @param game the game that was played
     * @param sessionDate the date the games was played
     * @param playTime how long (in hours) the game was played for
     */
    public PlaySession(Game game, LocalDate sessionDate, double playTime) {
        this.game = game;
        this.sessionDate = sessionDate;
        this.playTime = playTime;
    }

    /**
     *
     * Returns a the game which was played during this session.
     *
     * @return the game played during this session.
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * Returns the date on which the session was played.
     *
     * @return the date on which the session was played.
     */
    public LocalDate getSessionDate() {
        return sessionDate;
    }

    /**
     *
     * Returns the play time (in hours) of this session.
     *
     * @return the play time in hours of this session.
     */
    public double getPlayTime() {
        return playTime;
    }

    /**
     *
     * Returns a comma separated list of the date, game name and play time.
     *
     * @return a string representing the session date, the game name and the
     * play time
     */
    @Override
    public String toString() {
        return sessionDate.toString(DATE_FORMAT_DMY)
                + ", " + game.getName()
                + ", " + playTime;
    }

    /**
     *
     * Parses a double from an input string, which is intended
     * to represent the play time. If a double can't be parsed throws a
     * IllegalArgumentException with an explanation.
     *
     * @param timeStr the string to be parsed as a double
     * @return the time played if possible
     */
    public static double parsePlayTime(String timeStr) {
        double time = -1.0;
        try {
            time = Double.parseDouble(timeStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Not able to parse play time from " + timeStr, e);
        }
        return time;
    }

    /**
     *
     * Parses a JodaTime LocalDate from an input string. If the
     * string is blank then returns the DateTime for now. Otherwise attempts to
     * parse from the format in DATE_FORMAT_YMD. If it fails it throws an
     * IllegalArgumentException with information.
     *
     * @param dateStr the string that will be parsed
     * @return a correct date time if the can be parsed or null if its empty
     */
    public static LocalDate parseDateTime(String dateStr) {
        LocalDate date;
        if (dateStr.isEmpty()) {
            return null;
        } else {
            try {
                date = LocalDate.parse(dateStr, DATE_FORMAT_YMD);
            } catch (IllegalArgumentException e) {
                try {
                    date = LocalDate.parse(dateStr, DATE_FORMAT_DMY);
                } catch (IllegalArgumentException e2) {
                    throw new IllegalArgumentException(
                            "Not able to parse date from " + dateStr, e);
                }

            }
        }
        return date;
    }

    /**
     * 
     * Calculates the hashcode for the PlaySession based on the game,
     * session date and play time.
     * 
     * @return the hashcode calculated based on the game, the session date
     * and play time.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.game);
        hash = 71 * hash + Objects.hashCode(this.sessionDate);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.playTime)
                ^ (Double.doubleToLongBits(this.playTime) >>> 32));
        return hash;
    }

    /**
     * 
     * Checks if this PlaySession is equal to the other by checking if they
     * have the same game, play date and play time.
     * 
     * @param other the other object to check
     * @return true iff the other object is a PlaySession with the same
     * game, play date and play time as this one.
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
        final PlaySession otherSession = (PlaySession) other;
        if (Double.doubleToLongBits(this.playTime)
                != Double.doubleToLongBits(otherSession.playTime)) {
            return false;
        }
        if (!Objects.equals(this.game, otherSession.game)) {
            return false;
        }
        if (!Objects.equals(this.sessionDate, otherSession.sessionDate)) {
            return false;
        }
        return true;
    }

}
