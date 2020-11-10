package hieu.repository;

import hieu.PageResult;
import hieu.entity.CategoryEntity;
import hieu.entity.ProductEntity;
import hieu.util.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class CategoryRepository implements Serializable {

    private final static Object LOCK = new Object();
    private static CategoryRepository instance;

    public static CategoryRepository getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryRepository();
            }
        }
        return instance;
    }

    public synchronized boolean insert(CategoryEntity category) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (category == null) {
            return false;
        }
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        manager.persist(category);
        transaction.commit();
        return true;
    }

    public boolean isExisted(String hash) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        boolean isExisted = false;
        Query query = manager.createQuery("SELECT 1 FROM CategoryEntity c WHERE c.hash=:hash");
        query.setParameter("hash", hash);
        isExisted = query.getResultList().size() > 0;
        return isExisted;
    }

    public List<CategoryEntity> getAll() {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT c FROM CategoryEntity c");
        List<CategoryEntity> categoryEntities = query.getResultList();
        return categoryEntities;
    }

    public PageResult getProductEntitiesByCategoryHash(String categoryHash, float fromPrice, float toPrice, int pageNumber) {
        EntityManager manager = JPAHelper.initiateEntityManager();

        PageResult pageResult = new PageResult(pageNumber, 20);

        Long count = countProductByCategoryHash(categoryHash, fromPrice, toPrice);
        int lastPageNumber = pageResult.getLastPageNumber(count);
        int offset = pageResult.calculateOffset();

        Query query = manager.createQuery("SELECT p FROM ProductEntity p WHERE p.categoryHash=:hash AND (p.price>=:fromPrice AND p.price <=:toPrice) ORDER BY p.price ASC");
        query.setParameter("hash", categoryHash);
        query.setParameter("fromPrice", fromPrice);
        query.setParameter("toPrice", toPrice);
        query.setFirstResult(offset);
        query.setMaxResults(pageResult.getPageSize());
        List<ProductEntity> productEntities = query.getResultList();

        pageResult.setElements(productEntities);
        pageResult.setTotalPages(lastPageNumber);
        pageResult.setTotalElements(Math.toIntExact(count));

        return pageResult;
    }

    private Long countProductByCategoryHash(String hash, float fromPrice, float toPrice) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT COUNT(p) FROM ProductEntity p WHERE p.categoryHash=:hash AND (p.price>=:fromPrice AND p.price <=:toPrice)");
        query.setParameter("hash", hash);
        query.setParameter("fromPrice", fromPrice);
        query.setParameter("toPrice", toPrice);
        Long count = (Long) query.getSingleResult();
        return count;
    }
}
