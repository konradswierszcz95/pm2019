package pl.kspm.hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;
    private boolean isActive;
    private Timestamp created;
    private Timestamp lastPasswordChange;

    public long getId() {
        return id;
    }

    public Account setId(long id) {
        this.id = id;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Account setActive(boolean active) {
        isActive = active;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Account setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    public Account setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Account setRole(String role) {
        this.role = role;
        return this;
    }
}
