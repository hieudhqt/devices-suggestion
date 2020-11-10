package hieu.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UsageEntityPK implements Serializable {
    private String roomId;
    private String productHash;

    @Column(name = "room_id", nullable = false, length = 50)
    @Id
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
        UsageEntityPK that = (UsageEntityPK) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(productHash, that.productHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, productHash);
    }
}
