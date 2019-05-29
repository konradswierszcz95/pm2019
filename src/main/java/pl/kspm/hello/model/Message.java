package pl.kspm.hello.model;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserConnector author;

    @ManyToOne
    @JoinColumn(name="addressee_id")
    private UserConnector addressee;

    private String subject;

    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public UserConnector getAuthor() {
        return author;
    }

    public Message setAuthor(UserConnector author) {
        this.author = author;
        return this;
    }

    public UserConnector getAddressee() {
        return addressee;
    }

    public Message setAddressee(UserConnector addressee) {
        this.addressee = addressee;
        return this;
    }
}
