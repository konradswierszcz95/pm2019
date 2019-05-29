package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Role;
import pl.kspm.hello.model.UserConnector;

import java.util.List;
import java.util.Optional;

public interface UserConnectorRepository extends CrudRepository<UserConnector,Integer> {
    Optional<UserConnector> findByEmployee_Login(String username);

    UserConnector findFirstByEmployee_Login(String username);

    UserConnector findFirstById(long id);

    List<Role> findAllByRoles(String usename);
}
