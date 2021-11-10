package gametracker.core;

public class PersistenceManagerFactory {

        public static UnifiedPersistenceManager getManager(String managerType, String[] args) {
            UnifiedPersistenceManager manager;
            switch(managerType) {
                case "TypedJSON" : manager = new TypedJSONPersistenceManager(args);
                    break;
                case "CSV" : manager = new CSVPersistenceManager(args);
                    break;
                default:
                    throw new IllegalStateException("Unexpected manager type: " + managerType);
            }
            return manager;

        }



}
