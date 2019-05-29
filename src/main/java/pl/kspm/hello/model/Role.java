package pl.kspm.hello.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private List<UserConnector> usersList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public Role setRolename(String rolename) {
        this.rolename = rolename;
        return this;
    }

    public List<UserConnector> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserConnector> usersList) {
        this.usersList = usersList;
    }
}
