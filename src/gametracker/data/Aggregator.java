
package gametracker.data;

/**
 *
 * 
 * 
 * @author tjkendon
 */
public abstract class Aggregator {

    protected final PlayData sourceData;
    
    public Aggregator(PlayData sourceData) {
        this.sourceData = sourceData;
    }
    
    public abstract PlayAggregate aggregate();
    
}
