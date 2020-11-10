package hieu.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favourite", schema = "dbo", catalog = "DevicesSuggestion")
@IdClass(FavouriteEntityPK.class)
public class FavouriteEntity {
    private String username;
    private String productHash;
    private AccountEntity accountByUsername;
    private ProductEntity productByProductHash;

    public FavouriteEntity() {
    }

    public FavouriteEntity(String username, String productHash) {
        this.username = username;
        this.productHash = productHash;
    }

    @Id
    @Column(name = "username", nullable = false, length = 26)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @Column(name = "product_hash", nullable = false, length = 512)
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
        FavouriteEntity that = (FavouriteEntity) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(productHash, that.productHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, productHash);
    }

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    public AccountEntity getAccountByUsername() {
        return accountByUsername;
    }

    public void setAccountByUsername(AccountEntity accountByUsername) {
        this.accountByUsername = accountByUsername;
    }

    @ManyToOne
    @JoinColumn(name = "product_hash", referencedColumnName = "hash", nullable = false, insertable = false, updatable = false)
    public ProductEntity getProductByProductHash() {
        return productByProductHash;
    }

    public void setProductByProductHash(ProductEntity productByProductHash) {
        this.productByProductHash = productByProductHash;
    }
}
