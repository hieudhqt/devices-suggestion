package hieu.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FavouriteEntityPK implements Serializable {
    private String username;
    private String productHash;

    @Column(name = "username", nullable = false, length = 26)
    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "product_hash", nullable = false, length = 512)
    @Id
    public String getProductHash() {
        return productHash;
    }

    public void setProductHash(String productHash) {
        this.productHash = productHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteEntityPK that = (FavouriteEntityPK) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(productHash, that.productHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, productHash);
    }
}
