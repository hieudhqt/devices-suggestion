package hieu.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "dbo", catalog = "DevicesSuggestion")
public class ProductEntity {
    private String hash;
    private String name;
    private Float price;
    private String warranty;
    private String description;
    private String url;
    private String imageUrl;
    private String categoryHash;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Collection<FavouriteEntity> favouritesByHash;
    private CategoryEntity categoryByCategoryHash;
    private Collection<UsageEntity> usagesByHash;

    public ProductEntity() {
    }

    public ProductEntity(String hash, String name, Float price, String warranty, String description, String url, String imageUrl, String categoryHash, Timestamp createdAt, Timestamp updatedAt) {
        this.hash = hash;
        this.name = name.trim();
        this.price = price;
        if (!warranty.isEmpty()) {
            this.warranty = warranty.trim();
        } else {
            this.warranty = "12 tháng";
        }
        this.description = description.trim();
        this.url = url;
        this.imageUrl = imageUrl;
        this.categoryHash = categoryHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProductEntity(String hash, String name, Float price, String warranty, String description, String url, String imageUrl, String categoryHash, Timestamp updatedAt) {
        this.hash = hash;
        this.name = name.trim();
        this.price = price;
        if (!warranty.isEmpty()) {
            this.warranty = warranty.trim();
        } else {
            this.warranty = "12 tháng";
        }
        this.description = description.trim();
        this.url = url;
        this.imageUrl = imageUrl;
        this.categoryHash = categoryHash;
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
    @Column(name = "name", nullable = true, length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "warranty", nullable = true, length = 200)
    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 2147483647)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "imageUrl", nullable = true, length = 2147483647)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "category_hash", nullable = true, length = 512)
    public String getCategoryHash() {
        return categoryHash;
    }

    public void setCategoryHash(String categoryHash) {
        this.categoryHash = categoryHash;
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
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(hash, that.hash) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(warranty, that.warranty) &&
                Objects.equals(description, that.description) &&
                Objects.equals(url, that.url) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(categoryHash, that.categoryHash) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, name, price, warranty, description, url, imageUrl, categoryHash, createdAt, updatedAt);
    }

    @OneToMany(mappedBy = "productByProductHash")
    public Collection<FavouriteEntity> getFavouritesByHash() {
        return favouritesByHash;
    }

    public void setFavouritesByHash(Collection<FavouriteEntity> favouritesByHash) {
        this.favouritesByHash = favouritesByHash;
    }

    @ManyToOne
    @JoinColumn(name = "category_hash", referencedColumnName = "hash", insertable = false, updatable = false)
    public CategoryEntity getCategoryByCategoryHash() {
        return categoryByCategoryHash;
    }

    public void setCategoryByCategoryHash(CategoryEntity categoryByCategoryHash) {
        this.categoryByCategoryHash = categoryByCategoryHash;
    }

    @OneToMany(mappedBy = "productByProductHash")
    public Collection<UsageEntity> getUsagesByHash() {
        return usagesByHash;
    }

    public void setUsagesByHash(Collection<UsageEntity> usagesByHash) {
        this.usagesByHash = usagesByHash;
    }
}
