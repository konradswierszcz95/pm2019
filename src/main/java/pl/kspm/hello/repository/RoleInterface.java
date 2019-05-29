package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Employee;
import pl.kspm.hello.model.Role;

import java.util.List;

public interface RoleInterface extends CrudRepository<Role,Integer> {
    Role findById (long id);
}
