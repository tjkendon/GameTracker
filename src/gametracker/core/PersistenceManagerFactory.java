package gametracker.core;

public class PersistenceManagerFactory {

        public static PersistenceManager getManager(String managerType, String[] args) {
            PersistenceManager manager;
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
