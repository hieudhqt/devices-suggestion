package hieu.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "dbo", catalog = "DevicesSuggestion")
public class RoomEntity {
    private String id;
    private String name;
    private Collection<UsageEntity> usagesById;

    @Id
    @Column(name = "id", nullable = false, length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<UsageEntity> getUsagesById() {
        return usagesById;
    }

    public void setUsagesById(Collection<UsageEntity> usagesById) {
        this.usagesById = usagesById;
    }
}
