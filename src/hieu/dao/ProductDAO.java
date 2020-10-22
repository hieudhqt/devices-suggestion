package hieu.dao;

import hieu.db.MyConnection;
import hieu.dto.ProductDTO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO implements Serializable {

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

    public boolean insert(ProductDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT INTO product(hash, name, price, warranty, url, category_hash) VALUES(?, ?, ?, ?, ?, ?)";
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getHash());
            preStm.setString(2, dto.getName());
            preStm.setFloat(3, dto.getPrice());
            preStm.setString(4, dto.getWarranty());
            preStm.setString(5, dto.getUrl());
            preStm.setString(6, dto.getCategoryHash());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean findByHash(String hash) throws Exception {
        boolean found = false;
        try {
            String sql = "SELECT 1 FROM product WHERE hash = ?";
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, hash);
            rs = preStm.executeQuery();
            if (rs.next()) {
                found = true;
            }
        } finally {
            closeConnection();
        }
        return found;
    }

    public boolean updateInfo(ProductDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE product SET description = ?, imageLink = ? WHERE hash = ?";
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getDescription());
            preStm.setString(2, dto.getImageLink());
            preStm.setString(3, dto.getHash());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

}
