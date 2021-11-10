package gametracker.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersistenceManagerFactoryTest {

    @Test
    public void testGetManager() {

        String[] args = new String[0];

        UnifiedPersistenceManager csv = PersistenceManagerFactory.getManager("CSV", args);
        assertTrue("PersistenceManager - CSV", csv instanceof CSVPersistenceManager);
        UnifiedPersistenceManager json = PersistenceManagerFactory.getManager("TypedJSON", args);
        assertTrue("PersistenceManager - TypedJSON", json instanceof TypedJSONPersistenceManager);
        try {
            UnifiedPersistenceManager bad = PersistenceManagerFactory.getManager("Flubble", args);
            fail("Factory Generated manager with unexpected type");
        } catch (Exception e) {

        }

    }
}