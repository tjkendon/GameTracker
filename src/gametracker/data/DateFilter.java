package gametracker.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * Filters a {@link PlayData} to view only specific dates. Dates are organized
 * in {@link Window}s, any play session with a date between the start and end
 * dates of a window in the filter is shown, and any play session with a date
 * not included is not shown.
 *
 */
public class DateFilter implements Filter {

    /**
     * Accepted format for the start and end dates of a {@link Window}
     */
    public static final DateTimeFormatter DATE_FORMAT
            = DateTimeFormat.forPattern("yyyy/MM/dd");

    /**
     * The list of windows for this filter.
     */
    private final List<Window> windows;

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
     * Filters the {@link PlayData} and returns all sessions with a date inside
     * one of the windows in the filter.
     *
     * @param source the original play sessions to be filtered
     * @return all play sessions that have a date within a window in this filter
     */
    @Override
    public PlayData filter(PlayData source) {
        PlayData returnData = new PlayData();

        source.getPlaySessions().forEach((s) -> {
            windows.stream().filter((w)
                    -> (w.isIn(s.getSessionDate())
                    && (!returnData.contains(s)))).forEachOrdered((_item)
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
     * @param startDate the start date for the new window
     * @param endDate the end date for the new window
     */
    public void addWindow(LocalDate startDate, LocalDate endDate) {

        Window w = new Window();

        w.setStart(startDate);
        w.setEnd(endDate);
        windows.add(w);

    }

    /**
     *
     * Returns the list of all {@link windows} in this filter.
     *
     * @return a list of of the windows in this filter
     */
    public List<Window> getWindows() {
        return Collections.unmodifiableList(windows);
    }

    /**
     *
     * Removes a window from the filter.
     *
     * @param window the window to be removed
     * @return true if the window was removed
     */
    public boolean removeWindow(Window window) {
        return windows.remove(window);
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

    /**
     *
     * Returns a representation of the Filter as a series of windows, separated
     * by commas.
     *
     * @return a string listing all windows in the filter
     */
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

    /**
     *
     * Returns true if the filter has no windows.
     *
     * @return true if there are no windows in the filter.
     */
    @Override
    public boolean isEmpty() {
        return windows.isEmpty();
    }

    /**
     *
     * Holds the beginning and end times of a period.
     *
     * Starts with the end points set to null and will return true for all dates
     * tested. If the start date is set, it will return true if the date is
     * after the start date (inclusive). If the end date is set, it will return
     * true if the date is before the end date (exclusive).
     *
     * If both are set it will return if the date is between the start date
     * (inclusive) and the end date (exclusive).
     *
     */
    public class Window {

        /**
         * Start date of the window, any session must have a date matching this 
         * or later to be accepted by the filter.
         */
        private LocalDate start;
        /**
         * End date of the window, any session must have a date before this 
         * to be accepted by the filter.
         */
        private LocalDate end;

        /**
         * 
         * Creates a new window with no start or end date. This window will
         * include all dates.
         * 
         */
        public Window() {
            start = null;
            end = null;
        }

        /**
         * 
         * Returns the start date of the window. Sessions must have a date 
         * on or after this to be accepted by the window.
         * 
         * @return the start date of the window
         */
        public LocalDate getStart() {
            return start;
        }

        /**
         * 
         * Sets the start date of the window. Sessions must have a date 
         * on or after this to be accepted by the window. 
         * A date of null will accept all dates.
         * 
         * @param start the date which all sessions must be on or after to be 
         * accepted. null to accept all dates.
         */
        public void setStart(LocalDate start) {
            this.start = start;
        }

        /**
         * Returns the end date of the window. Sessions must have a date before
         * this to be accepted by the window.
         * 
         * @return the end date of the window
         */
        public LocalDate getEnd() {
            return end;
        }

        /**
         * 
         * Sets the end date of the window. Sessions must have a date 
         * before this to be accepted by the window. 
         * A date of null will accept all dates.
         * 
         * @param end the date which all sessions must be before to be 
         * accepted. null to accept all dates.
         */

        public void setEnd(LocalDate end) {
            this.end = end;
        }

        /**
         * 
         * Checks if the given date falls within this window. Will return true
         * so long as the date is on or before the window's start date and 
         * before the window's end date.
         * 
         * If the start date is null it is considered equivalent to the 
         * beginning of time. If the end date is null it is considered 
         * equivalent to the end of time.
         * 
         * @param date the date to test against the window
         * @return true if the date is within the window, false otherwise
         * 
         */
        public boolean isIn(LocalDate date) {
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

        /**
         * 
         * Returns a string representation of the start and end dates
         * of the window. If either is null, it is replaced with "Start of Time"
         * or "End of Time" respectively, or "All of Time" if both are null.
         * 
         * @return a string representing the window via its start and end dates
         */
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
