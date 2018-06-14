package pl.zdrojewska.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("PERSISTENCE");

    public EntityManagerFactory getFactory() {
        return factory;
    }
}
