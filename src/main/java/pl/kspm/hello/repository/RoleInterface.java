package pl.kspm.hello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Employee;
import pl.kspm.hello.model.Role;
import pl.kspm.hello.model.User;

import java.util.List;

public interface RoleInterface extends CrudRepository<Role,Integer> {
    Role findById (long id);

    List<Role> findAll();
}
