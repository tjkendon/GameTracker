package gametracker.data;

/**
 *
 * Interface describing the classes which allow Play Data about Play Sessions to
 * be filtered so that they match a particular condition.
 * 
 * @author tjkendon
 */
public interface Filter {
    
    /**
     * 
     * Filters the given data to find a match for the filter.
     * 
     * @param source the data to filter
     * @return the play sessions in the data that match the filter condition
     */
    public PlaySessionList filter(PlaySessionList source);
    
    /**
     * Resets the filter to its default state
     */
    public void clear();

    /**
     * Checks if the filter has any conditions that could be matched.
     * @return true if there is NO possible match, false if there is a 
     * possible match
     */
    public boolean isEmpty();
    
}
