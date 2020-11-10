package hieu.entity;

import hieu.util.StringHelper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "category", schema = "dbo", catalog = "DevicesSuggestion")
public class CategoryEntity {
    private String hash;
    private String name;
    private String url;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Collection<ProductEntity> productsByHash;

    public CategoryEntity() {
    }

    public CategoryEntity(String hash, String name, String url, Timestamp createdAt, Timestamp updatedAt) {
        this.hash = hash;
        this.name = StringHelper.normalizeString(name);
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CategoryEntity(String hash, String name, String url, Timestamp updatedAt) {
        this.hash = hash;
        this.name = StringHelper.normalizeString(name);
        this.url = url;
        this.updatedAt = updatedAt;
    }

    @Id
    @Column(name = "hash", nullable = false, length = 512)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 2147483647)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 2147483647)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(hash, that.hash) &&
                Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, name, url, createdAt, updatedAt);
    }

    @OneToMany(mappedBy = "categoryByCategoryHash")
    public Collection<ProductEntity> getProductsByHash() {
        return productsByHash;
    }

    public void setProductsByHash(Collection<ProductEntity> productsByHash) {
        this.productsByHash = productsByHash;
    }
}
