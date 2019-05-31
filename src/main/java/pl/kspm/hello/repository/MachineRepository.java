package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Machine;

import java.util.List;

public interface MachineRepository extends CrudRepository<Machine,Integer> {

    List<Machine> findAll();

    Machine findById(long id);
}
