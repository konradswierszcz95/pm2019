package pl.kspm.hello.tools;

public class MsgObject {
    private String author;

    private String addressee;

    private String subject;

    private String content;

    public String getAuthor() {
        return author;
    }

    public MsgObject setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAddressee() {
        return addressee;
    }

    public MsgObject setAddressee(String addressee) {
        this.addressee = addressee;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MsgObject setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MsgObject setContent(String content) {
        this.content = content;
        return this;
    }
}
