package pl.kspm.hello.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserConnector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adddressId")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToMany
    private List<Role> roles;

    @OneToMany(mappedBy = "author")
    private List<Message> sendedMsg;

    @OneToMany(mappedBy = "addressee")
    private List<Message> recivedMsg;

    public UserConnector() {
    }

    public UserConnector(UserConnector userConnector) {
        this.id = userConnector.getId();
        this.account = userConnector.getAccount();
        this.address = userConnector.getAddress();
        this.employee = userConnector.getEmployee();
    }

    public long getId() {
        return id;
    }

    public UserConnector setId(long id) {
        this.id = id;
        return this;
    }

    public Employee getEmployee() {
        return employee;
    }

    public UserConnector setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public UserConnector setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public UserConnector setAccount(Account account) {
        this.account = account;
        return this;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public UserConnector setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserConnector addRole (Role role) {
        this.roles.add(role);
        return this;
    }

    public List<Message> getSendedMsg() {
        return sendedMsg;
    }

    public void setSendedMsg(List<Message> sendedMsg) {
        this.sendedMsg = sendedMsg;
    }

    public List<Message> getRecivedMsg() {
        return recivedMsg;
    }

    public void setRecivedMsg(List<Message> recivedMsg) {
        this.recivedMsg = recivedMsg;
    }
}
