package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.MessageRepository;
import pl.kspm.hello.repository.UserRepository;
import pl.kspm.hello.tools.MsgObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    public int sendMsg(MsgObject msgObject) {
        Message m = new Message();
        User user = this.userRepository.findFirstByEmployee_Login(msgObject.getAddressee());

        m.setAddressee(user)
                .setAuthor(UserContext.getCurrentUser())
                .setSubject(msgObject.getSubject())
                .setContent(msgObject.getContent())
                .setCreated(new Timestamp(new Date().getTime()));

        Optional<User> optionalUser = userRepository.findByEmployee_Login(msgObject.getAddressee());
        if (optionalUser.isPresent()) {
            this.messageRepository.save(m);
        } else {
            System.out.println("Jebać");
        }

        return 0;
    }

    public Iterable<Message> currentUserRecivedMsg() {
        Iterable<Message> recivedMessages = this.messageRepository
                .findAllByAddresseeEquals(userRepository
                        .findFirstById(UserContext.getCurrentUserId()));

        return recivedMessages;
    }

    public Iterable<Message> currentUserSendedMsg() {
        Iterable<Message> sendedMessenges = this.messageRepository
                .findAllByAuthorEquals(userRepository.findFirstById(UserContext.getCurrentUserId()));
        return sendedMessenges;
    }

    public Message getMessageById(long msgId, long currentUserId) {
        Message message = this.messageRepository.getMessageById(msgId);
        long addresseId = message.getAddressee().getId();
        long authorId = message.getAuthor().getId();

        if (currentUserId == addresseId || currentUserId==authorId) {
            return message;
        } else {
            return null;
        }


    }

    public void save(Message message) {
        this.messageRepository.save(message);
    }

    public int numberOfUnreadeMessages(User u) {
        int number=this.messageRepository.findAllByAddresseeEqualsAndReadedIsNull(u).size();
         return number;
    }
}
