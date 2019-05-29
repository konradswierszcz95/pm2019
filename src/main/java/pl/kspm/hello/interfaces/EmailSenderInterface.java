package pl.kspm.hello.interfaces;

public interface EmailSenderInterface {
    void sendEmail(String to, String subject, String content);
}
