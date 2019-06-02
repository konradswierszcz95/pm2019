package pl.kspm.hello.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Failure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private Timestamp created;
    private Timestamp accepted;
    private Timestamp ended;

    private int isAccept;

    @ManyToOne
    private Machine brokenMachine;

    private String shortInfo;

    @ManyToOne
    private User creator;
    @ManyToOne
    private User acceptor;
    @ManyToOne
    private User ender;

    private String raport;

    public long getId() {
        return id;
    }

    public Failure setId(long id) {
        this.id = id;
        return this;
    }



    public Timestamp getCreated() {
        return created;
    }

    public Failure setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public Timestamp getAccepted() {
        return accepted;
    }

    public Failure setAccepted(Timestamp accepted) {
        this.accepted = accepted;
        return this;
    }

    public Timestamp getEnded() {
        return ended;
    }

    public Failure setEnded(Timestamp ended) {
        this.ended = ended;
        return this;
    }

    public Failure setBrokenMachine(Machine brokenMachine) {
        this.brokenMachine = brokenMachine;
        return this;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public Failure setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Failure setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public User getAcceptor() {
        return acceptor;
    }

    public Failure setAcceptor(User acceptor) {
        this.acceptor = acceptor;
        return this;
    }

    public String getRaport() {
        return raport;
    }

    public Failure setRaport(String raport) {
        this.raport = raport;
        return this;
    }

    public int getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(int isAccept) {
        this.isAccept = isAccept;
    }

    public Machine getBrokenMachine() {
        return brokenMachine;
    }

    public User getEnder() {
        return ender;
    }

    public void setEnder(User ender) {
        this.ender = ender;
    }
}
