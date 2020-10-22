package hieu.dao;

import hieu.db.MyConnection;
import hieu.dto.CategoryDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoriesDAO implements Serializable {

    public CategoriesDAO() {
    }

    private Connection conn = null;
    private PreparedStatement preStm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean insert(CategoryDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO category(name, url, hash) VALUES(?, ?, ?)";
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1,dto.getName());
            preStm.setString(2, dto.getUrl());
            preStm.setString(3, dto.getHash());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean findByHash(String cateName) throws Exception {
        boolean check = false;
        try {
            String sql = "SELECT 1 FROM category WHERE hash = ?";
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, cateName);
            rs = preStm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
}
