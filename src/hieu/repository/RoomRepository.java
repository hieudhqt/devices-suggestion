package hieu.repository;

import hieu.PageResult;
import hieu.ResultObject;
import hieu.entity.ProductEntity;
import hieu.entity.RoomEntity;
import hieu.entity.UsageEntity;
import hieu.util.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class RoomRepository implements Serializable {

    private static final Object LOCK = new Object();
    private static RoomRepository instance;

    public static RoomRepository getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new RoomRepository();
            }
        }
        return instance;
    }

    public List<RoomEntity> getAll() {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT r FROM RoomEntity r");
        List<RoomEntity> roomEntities = query.getResultList();
        return roomEntities;
    }

    public boolean isUsageExisted(String roomId, String productHash) {
        boolean isExisted = false;
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT 1 FROM UsageEntity u WHERE u.roomId=:id AND u.productHash=:hash");
        query.setParameter("id", roomId);
        query.setParameter("hash", productHash);
        isExisted = query.getResultList().size() > 0;
        return isExisted;
    }

    public synchronized boolean insertUsage(UsageEntity usage) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        if (usage == null) {
            return false;
        }
        EntityTransaction transaction = manager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        manager.persist(usage);
        transaction.commit();
        return true;
    }

    public List<RoomEntity> getRoomByProductHash(String hash) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT u.roomByRoomId FROM UsageEntity u WHERE u.productHash=:hash");
        query.setParameter("hash", hash);
        List<RoomEntity> roomEntityList = query.getResultList();
        return roomEntityList;
    }

    public List<String> getRoomNameByProductHash(String hash) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT u.roomByRoomId.name FROM UsageEntity u WHERE u.productHash=:hash");
        query.setParameter("hash", hash);
        List<String> roomNameList = query.getResultList();
        return roomNameList;
    }

    public ResultObject getFirstFiveProductByRoomId(String roomId, String productHash, float price) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createNativeQuery("SELECT CAST((SELECT TOP 8 us.room_id, p.hash, p.name, p.price, p.url, p.imageUrl " +
                "FROM product p, " +
                "(SELECT u.room_id, u.product_hash " +
                "FROM usage u " +
                "WHERE u.room_id = ? AND u.product_hash <> ?) us " +
                "WHERE p.hash = us.product_hash AND p.price >= ? AND p.price <= ? AND p.price > 0" +
                "FOR XML PATH ('product'), ROOT ('products')) AS NVARCHAR(MAX)) AS XmlData");

        Random random = new Random();
        float percentage = 5f + random.nextFloat() * (15f - 5f);
        float fromPrice = price * (1f - percentage / 100f);
        float toPrice = price * (1f + percentage / 100f);

        query.setParameter(1, roomId);
        query.setParameter(2, productHash);
        query.setParameter(3, fromPrice);
        query.setParameter(4, toPrice);
        String xmlResult = (String) query.getResultList().get(0);

        ResultObject result = new ResultObject(xmlResult, fromPrice, toPrice);
        return result;
    }

    public PageResult getProductByRoomId(String roomId, String productHash, int pageNumber) {
        EntityManager manager = JPAHelper.initiateEntityManager();

        PageResult pageResult = new PageResult(pageNumber, 20);

        Long count = countProductsByRoomId(roomId);
        int lastPageNumber = pageResult.getLastPageNumber(count);
        int offset = pageResult.calculateOffset();

        Query query = manager.createQuery("SELECT u.productByProductHash FROM UsageEntity u WHERE u.roomId=:roomId AND u.productHash<>:productHash");
        query.setParameter("roomId", roomId);
        query.setParameter("productHash", productHash);
        query.setFirstResult(offset);
        query.setMaxResults(pageResult.getPageSize());
        List<ProductEntity> productEntityList = query.getResultList();

        pageResult.setElements(productEntityList);
        pageResult.setTotalPages(lastPageNumber);

        return pageResult;
    }

    private Long countProductsByRoomId(String id) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT COUNT(u.productByProductHash) FROM UsageEntity u WHERE u.roomId=:roomId");
        query.setParameter("roomId", id);
        Long count = (Long) query.getSingleResult();
        return count;
    }

}
