package hieu.repository;

import hieu.entity.AccountEntity;
import hieu.util.JPAHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class AccountRepository implements Serializable {

    private static AccountRepository instance;

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public AccountEntity login(String username, String password) {
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT new hieu.entity.AccountEntity(a.username, a.name, a.role) FROM AccountEntity a WHERE a.username=:username AND a.password=:password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List resultList = query.getResultList();
        AccountEntity account = null;
        if (resultList.size() > 0) {
            account = (AccountEntity) resultList.get(0);
        }
        return account;
    }

    public boolean isExisted(String username) {
        boolean isExisted = false;
        EntityManager manager = JPAHelper.initiateEntityManager();
        Query query = manager.createQuery("SELECT 1 FROM AccountEntity a WHERE a.username=:username");
        query.setParameter("username", username);
        isExisted = query.getResultList().size() > 0;
        return isExisted;
    }

}
