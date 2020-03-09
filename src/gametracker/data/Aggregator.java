
package gametracker.data;

/**
 * Produces a set of aggregate values from a source {@link PlaySessionList}.
 * <p>
 * Subclasses generate a specific aggregate representing some aspect of the 
 * play sessions stored in the {@link PlaySessionList}.
 * 
 * 
 * 
 */
public abstract class Aggregator {

    /**
     * The source data that the aggregator will aggregate when {@link aggregate}
     * is called.
     */
    protected final PlaySessionList sourceData;
    
    /**
     * 
     * Sets the data that the aggreator will aggregate.
     * 
     * @param sourceData the data the aggregator will aggregate.
     */
    public Aggregator(PlaySessionList sourceData) {
        this.sourceData = sourceData;
    }
    
    /**
     * 
     * Produces an aggregate value based on the {@link sourceData}.
     * 
     * @return a {@link PlayAggregate} representing an aspect of the source 
     * data.
     */
    public abstract PlayAggregate aggregate();
    
}
