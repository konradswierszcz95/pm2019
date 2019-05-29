package pl.kspm.hello.form;

public class SendMsg {

    private String addressee;
    private String subject;
    private String content;

    public SendMsg() {
        super();
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String msgContent) {
        this.content = msgContent;
    }
}
