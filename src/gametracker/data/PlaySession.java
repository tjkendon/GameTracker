package gametracker.data;

import gametracker.cli.CLI;
import java.util.Objects;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * Basic data about a single play session. Includes the game that was played,
 * the date of the session and how long (in hours) the session went on for.
 *
 * @author tjkendon
 */
public class PlaySession {

    /**
     *
     */
    public static DateTimeFormatter SESSION_DATE_FORMAT
            = DateTimeFormat.forPattern("yyyy/MM/dd");
    
    public static DateTimeFormatter ALTERNATE_SESSION_DATE_FORMAT
            = DateTimeFormat.forPattern("dd/MM/yyyy");

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
        if (!(this.sessionDate.getYear() == other.sessionDate.getYear())
                && (this.sessionDate.getMonthOfYear()
                == other.sessionDate.getMonthOfYear())
                && (this.sessionDate.getDayOfMonth()
                == other.sessionDate.getDayOfMonth()) ) {
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

    /**
     *
     * Helper method to parse a double from an input string, which is intended
     * to represent the play time. If it can't be parsed throws a
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
     * Helper method to parse a JodaTime DateTime from an input string. If the
     * string is blank then returns the DateTime for now. Otherwise attempts to
     * parse from the format in SESSION_DATE_FORMAT. If it fails it throws an
     * IllegalArgumentException with information.
     *
     * @param dateStr the string that will be parsed
     * @return a correct date time if the string is empty or can be parsed
     */
    public static DateTime parseDateTime(String dateStr) {
        DateTime date;
        if (dateStr.isEmpty()) {
            date = new DateTime();
        } else {
            try {
                date = DateTime.parse(dateStr, SESSION_DATE_FORMAT);
            } catch (IllegalArgumentException e) {
                try {
                    date = DateTime.parse(dateStr, ALTERNATE_SESSION_DATE_FORMAT);
                } catch (IllegalArgumentException e2) {
                    throw new IllegalArgumentException(
                        "Not able to parse date from " + dateStr, e);
                }
                
            }
        }
        return date;
    }

}
