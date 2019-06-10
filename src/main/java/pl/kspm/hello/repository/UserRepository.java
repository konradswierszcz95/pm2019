package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Role;
import pl.kspm.hello.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmployee_Login(String username);

    User findFirstByEmployee_Login(String username);

    User findFirstById(long id);
}
