package hieu.repository;

import hieu.entity.ProductEntity;
import hieu.util.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;

public class ProductRepository implements Serializable {

    private final static Object LOCK = new Object();
    private static ProductRepository instance;

    public static ProductRepository getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductRepository();
            }
        }
        return instance;
    }

    public synchronized boolean insert(ProductEntity product) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (product == null) {
            return false;
        }
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        manager.persist(product);
        transaction.commit();
        return true;
    }

    public boolean isExisted(String hash) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        boolean isExisted = false;
        Query query = manager.createQuery("SELECT 1 FROM ProductEntity p WHERE p.hash=:hash");
        query.setParameter("hash", hash);
        isExisted = query.getResultList().size() > 0;
        return isExisted;
    }

    public synchronized boolean update(ProductEntity product) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (product == null) {
            return false;
        }
        ProductEntity oldProduct = manager.find(ProductEntity.class, product.getHash());
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        oldProduct.setPrice(product.getPrice());
        oldProduct.setWarranty(product.getWarranty());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setUrl(product.getUrl());
        oldProduct.setImageUrl(product.getImageUrl());
        oldProduct.setUpdatedAt(product.getUpdatedAt());
        manager.merge(oldProduct);
        transaction.commit();
        return true;
    }

    public float getMaxPrice() {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT MAX(p.price) FROM ProductEntity p");
        Float maxPrice = (Float) query.getSingleResult();
        return maxPrice;
    }

    public float getMinPrice() {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT MIN(p.price) FROM ProductEntity p");
        Float maxPrice = (Float) query.getSingleResult();
        return maxPrice;
    }

    public ProductEntity getProductByHash(String hash) {
        try {
            ProductEntity product;
            EntityManager manager = JPAHelper.initiateEntityManager();
            Query query = manager.createQuery("SELECT p FROM ProductEntity p WHERE p.hash=:hash");
            query.setParameter("hash", hash);
            product = (ProductEntity) query.getSingleResult();
            return product;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
