package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.MessageRepository;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.tools.MsgObject;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserConnectorRepository userConnectorRepository;

    public int sendMsg(MsgObject msgObject) {
        Message m = new Message();
        User user = this.userConnectorRepository.findFirstByEmployee_Login(msgObject.getAddressee());

        m.setAddressee(user)
                .setAuthor(UserContext.getCurrentUser())
                .setSubject(msgObject.getSubject())
                .setContent(msgObject.getContent())
                .setCreated(new Timestamp(new Date().getTime()));

        this.messageRepository.save(m);

        return 0;
    }

    public Iterable<Message> currentUserRecivedMsg() {
        Iterable<Message> recivedMessages = this.messageRepository
                .findAllByAddresseeEquals(userConnectorRepository
                        .findFirstById(UserContext.getCurrentUserId()));

        return recivedMessages;
    }

    public Iterable<Message> currentUserSendedMsg() {
        Iterable<Message> sendedMessenges = this.messageRepository
                .findAllByAuthorEquals(userConnectorRepository.findFirstById(UserContext.getCurrentUserId()));
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
}
