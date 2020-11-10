package hieu.util;

import hieu.constant.GlobalPath;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAHelper {

    private static final Object LOCK = new Object();
    private static EntityManagerFactory factory;

    public static EntityManager initiateEntityManager() {
        synchronized (LOCK) {
            if (factory == null || !factory.isOpen()) {
                factory = Persistence.createEntityManagerFactory(GlobalPath.PERSISTENCE_UNIT);
            }
        }
        return factory.createEntityManager();
    }

    public static void closeFactory() {
        factory.close();
    }

}
