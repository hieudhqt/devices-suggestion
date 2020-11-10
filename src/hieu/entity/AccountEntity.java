package hieu.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "account", schema = "dbo", catalog = "DevicesSuggestion")
public class AccountEntity {
    private String username;
    private String password;
    private String name;
    private String role;
    private Collection<FavouriteEntity> favouritesByUsername;

    public AccountEntity() {
    }

    public AccountEntity(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    @Id
    @Column(name = "username", nullable = false, length = 26)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Basic
    @Column(name = "role", nullable = true, length = 13)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, role);
    }

    @OneToMany(mappedBy = "accountByUsername")
    public Collection<FavouriteEntity> getFavouritesByUsername() {
        return favouritesByUsername;
    }

    public void setFavouritesByUsername(Collection<FavouriteEntity> favouritesByUsername) {
        this.favouritesByUsername = favouritesByUsername;
    }
}
