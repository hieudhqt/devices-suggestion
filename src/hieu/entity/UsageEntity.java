package hieu.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usage", schema = "dbo", catalog = "DevicesSuggestion")
@IdClass(UsageEntityPK.class)
public class UsageEntity {
    private String roomId;
    private String productHash;
    private RoomEntity roomByRoomId;
    private ProductEntity productByProductHash;

    public UsageEntity() {
    }

    public UsageEntity(String roomId, String productHash) {
        this.roomId = roomId;
        this.productHash = productHash;
    }

    @Id
    @Column(name = "room_id", nullable = false, length = 50)
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
        UsageEntity that = (UsageEntity) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(productHash, that.productHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, productHash);
    }

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public RoomEntity getRoomByRoomId() {
        return roomByRoomId;
    }

    public void setRoomByRoomId(RoomEntity roomByRoomId) {
        this.roomByRoomId = roomByRoomId;
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
