package gametracker.core;

/**
 * Dynamically provides a {@link PersistenceManager}.
 * <p>
 * Uses a simple name lookup to provide the manager.
 */
public class PersistenceManagerFactory {

    /**
     *
     * Returns a new <code>PersistenceManager</code> based on the name provided.
     * <p>
     * The name is based on a keyword, rather than on fully qualified name.
     * <ul>
     *     <li>TypedJSON - TypedJSONPersistenceManager</li>
     *     <li>CSV - CSVPersistenceManager</li>
     * </ul>
     *
     *
     * @param managerType the keyword for the <code>PersistenceManager</code>
     * @param args the argument list being passed to the <code>PersistenceManager</code>
     * @return the <code>PersistenceManager</code> for the given keyword
     */
    public static PersistenceManager getManager(String managerType, String[] args) {
        PersistenceManager manager;
        switch (managerType) {
            case "TypedJSON":
                manager = new TypedJSONPersistenceManager(args);
                break;
            case "CSV":
                manager = new CSVPersistenceManager(args);
                break;
            default:
                throw new IllegalStateException("Unexpected manager type: " + managerType);
        }
        return manager;

    }


}
