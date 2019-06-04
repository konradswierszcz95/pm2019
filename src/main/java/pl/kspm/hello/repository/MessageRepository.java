package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.User;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Integer> {

    List<Message> findAllByAddresseeEquals(User user);
    List<Message> findAllByAddresseeEqualsAndReadedIsNull(User user);

    List<Message> findAllByAuthorEquals(User user);

    Message getMessageById(long id);
}
