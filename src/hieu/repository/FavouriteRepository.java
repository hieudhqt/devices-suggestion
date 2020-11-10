package hieu.repository;

import hieu.PageResult;
import hieu.entity.FavouriteEntity;
import hieu.entity.ProductEntity;
import hieu.util.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class FavouriteRepository implements Serializable {

    private static FavouriteRepository instance;

    public static FavouriteRepository getInstance() {
        if (instance == null) {
            instance = new FavouriteRepository();
        }
        return instance;
    }

    public boolean isExisted(String username, String productHash) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        boolean isExisted = false;
        Query query = manager.createQuery("SELECT 1 FROM FavouriteEntity f WHERE f.username=:username AND f.productHash=:productHash");
        query.setParameter("username", username);
        query.setParameter("productHash", productHash);
        isExisted = query.getResultList().size() > 0;
        return isExisted;
    }

    public boolean insert(FavouriteEntity favourite) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (favourite == null) {
            return false;
        }
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        manager.persist(favourite);
        transaction.commit();
        return true;
    }

    public boolean delete(FavouriteEntity favourite) {
        FavouriteEntity entity;
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (favourite == null) {
            return false;
        }
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        entity = manager.merge(favourite);
        manager.remove(entity);
        transaction.commit();
        return true;
    }

    public List<ProductEntity> getMostFavouriteProducts() {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT f.productByProductHash FROM FavouriteEntity f GROUP BY f.productByProductHash ORDER BY COUNT(f.productByProductHash) DESC");
        query.setMaxResults(10);
        List<ProductEntity> productEntityList = query.getResultList();
        return productEntityList;
    }

    public PageResult getFavouriteProductByUsername(String username, int pageNumber) {
        EntityManager manager = JPAHelper.initiateEntityManager();

        PageResult pageResult = new PageResult(pageNumber, 20);

        Long count = countProductsByUsername(username);
        int lastPageNumber = pageResult.getLastPageNumber(count);
        int offset = pageResult.calculateOffset();

        Query query = manager.createQuery("SELECT f.productByProductHash FROM FavouriteEntity f WHERE f.username=:username ORDER BY f.productByProductHash.price ASC");
        query.setParameter("username", username);
        query.setFirstResult(offset);
        query.setMaxResults(pageResult.getPageSize());
        List<ProductEntity> productEntityList = query.getResultList();

        pageResult.setElements(productEntityList);
        pageResult.setTotalPages(lastPageNumber);

        return pageResult;
    }

    public List<ProductEntity> getFavouriteProductsForReporting(String username) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT f.productByProductHash FROM FavouriteEntity f WHERE f.username=:username");
        query.setParameter("username", username);
        List<ProductEntity> productEntityList = query.getResultList();
        return productEntityList;
    }

    private Long countProductsByUsername(String username) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT COUNT(f.productHash) FROM FavouriteEntity f WHERE f.username=:username");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();
        return count;
    }

}
