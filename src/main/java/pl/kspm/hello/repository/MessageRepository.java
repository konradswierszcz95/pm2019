package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.UserConnector;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Integer> {

    List<Message> findAllByAddresseeEquals(UserConnector userConnector);
}
