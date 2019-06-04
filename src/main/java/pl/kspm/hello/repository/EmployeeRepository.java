package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
    List<Employee> findAllByName(String firstName);

    List<Employee> findAllBySurname(String surname);

    Employee findFirstById(int id);

    List<Employee> findAllByLoginContains(String login);

    boolean existsByEmail(String email);
}
