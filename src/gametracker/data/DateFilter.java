package gametracker.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * Data Filter
 *
 * @author tjkendon
 */
public class DateFilter implements Filter {

    List<Window> windows;

    /**
     *
     * Creates a new DateFilter with an empty list of windows. With no windows
     * added this filter will return an empty PlayData.
     *
     */
    public DateFilter() {
        windows = new ArrayList<>();
    }

    /**
     *
     * Filters the PlayData source and returns all sessions with a date that is
     * inside one of the windows in the filter.
     *
     * @param source
     * @return
     */
    @Override
    public PlayData filter(PlayData source) {
        PlayData returnData = new PlayData();

        source.getPlaySessions().forEach((s) -> {
            windows.stream().filter((w) -> 
                    (w.isIn(s.getSessionDate()) && 
                            (!returnData.contains(s)))).forEachOrdered((_item) 
                                    -> {
                returnData.addPlaySession(s);
            });
        });

        return returnData;
    }

    /**
     *
     * Adds a new window to the filter, any session with a date after the start
     * date (inclusive) and before the end date (exclusive) will be passed
     * through the filter
     *
     * @param beginingTime
     * @param endTime
     */
    public void addWindow(DateTime beginingTime, DateTime endTime) {

        Window w = new Window();

        w.setStart(beginingTime);
        w.setEnd(endTime);
        windows.add(w);

    }

    /**
     *
     * Returns the list of all windows in this filter.
     *
     * @return
     */
    public List<Window> getWindows() {
        return windows;
    }

    /**
     *
     *
     * Removes a given window from the filter.
     *
     * @param w
     * @return
     */
    public boolean removeWindow(Window w) {
        return windows.remove(w);
    }

    /**
     *
     * Removes all windows from the filter and resets it to the base state where
     * filtering will return the empty PlayData.
     *
     */
    @Override
    public void clear() {
        windows.clear();
    }

    @Override
    public String toString() {
        StringBuilder br = new StringBuilder("Date Filter: [");
        // fun with java joiner example from API
        String commaSeparatedGames = windows.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(", "));

        br.append(commaSeparatedGames).append("]");
        return br.toString();
    }

    @Override
    public boolean isEmpty() {
        return windows.isEmpty();
    }
    
    

    /**
     *
     * Class that holds the beginning and end times of a period.
     *
     * Starts with the end points set to null and will return true for all dates
     * tested with isIn. Setting the Start Date, it will return true if the date
     * is after the start date (inclusive). Setting the End Date, it will return
     * true if the date is before the end date (exclusive).
     *
     * If both are set it will return if the date is between the start date
     * (inclusive) and the end date (exclusive).
     *
     */
    public class Window {

        public DateTimeFormatter DATE_FORMAT
            = DateTimeFormat.forPattern("yyyy/MM/dd");
        
        DateTime start;
        DateTime end;

        public Window() {
            start = null;
            end = null;
        }

        public DateTime getStart() {
            return start;
        }

        public void setStart(DateTime start) {
            this.start = start;
        }

        public DateTime getEnd() {
            return end;
        }

        public void setEnd(DateTime end) {
            this.end = end;
        }

        public boolean isIn(DateTime date) {
            if ((start == null) && (end == null)) {
                return true;
            } else if ((start == null) && (end != null)) {
                return date.isBefore(end);
            } else if ((start != null) && (end == null)) {
                return date.isAfter(start) || date.isEqual(start);
            }
            return (date.isAfter(start) || date.isEqual(start)) 
                    && date.isBefore(end);
        }

        @Override
        public String toString() {
            if ((start == null) && (end == null)) {
                return "All Time";
            } else if ((start == null) && (end != null)) {
                return "[ Begining of Time - " + DATE_FORMAT.print(end) + "]";
            } else if ((start != null) && (end == null)) {
                return "[" + DATE_FORMAT.print(start) + " - End of Time]";
            }
            return "[" + DATE_FORMAT.print(start) + " - " 
                    + DATE_FORMAT.print(end) + "]";
        }
        
        

    }

}
