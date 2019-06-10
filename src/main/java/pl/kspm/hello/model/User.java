package pl.kspm.hello.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String dtype;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adddressId")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "author")
    private List<Message> sendedMsg;

    @OneToMany(mappedBy = "addressee")
    private List<Message> recivedMsg;

    @OneToMany(mappedBy = "creator")
    private List<Failure> createdFailures;

    @OneToMany(mappedBy = "acceptor")
    private List<Failure> acceptedFailures;

    @OneToMany(mappedBy = "ender")
    private List<Failure> endedFailures;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.address = user.getAddress();
        this.employee = user.getEmployee();
        this.roles = user.getRoles();
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public Employee getEmployee() {
        return employee;
    }

    public User setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public User setAccount(Account account) {
        this.account = account;
        return this;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public User addRole (Role role) {
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

    public List<Failure> getCreatedFailures() {
        return createdFailures;
    }

    public void setCreatedFailures(List<Failure> createdFailures) {
        this.createdFailures = createdFailures;
    }

    public List<Failure> getAcceptedFailures() {
        return acceptedFailures;
    }

    public void setAcceptedFailures(List<Failure> acceptedFailures) {
        this.acceptedFailures = acceptedFailures;
    }

    public List<Failure> getEndedFailures() {
        return endedFailures;
    }

    public void setEndedFailures(List<Failure> endedFailures) {
        this.endedFailures = endedFailures;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype() {
        this.dtype = "String";
    }
}
