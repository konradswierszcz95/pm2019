package pl.kspm.hello.service;

import javafx.print.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.UserConnector;
import pl.kspm.hello.repository.MessageRepository;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.tools.MsgObject;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserConnectorRepository userConnectorRepository;

    public int sendMsg(MsgObject msgObject) {
        Message m = new Message();
        UserConnector user = this.userConnectorRepository.findFirstByEmployee_Login(msgObject.getAddressee());
        m.setAddressee(user)
                .setAuthor(user)
                .setSubject(msgObject.getSubject())
                .setContent(msgObject.getContent());

        this.messageRepository.save(m);

        return 0;
    }

    public Iterable<Message> currentUserRecivedMsg(int id) {
        Iterable<Message> recivedMessages = this.messageRepository.findAllByAddresseeEquals(userConnectorRepository.findFirstById(2));
        return recivedMessages;
    }
}
