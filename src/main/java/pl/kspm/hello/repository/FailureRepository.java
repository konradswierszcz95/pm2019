package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Failure;

import java.util.List;

public interface FailureRepository extends CrudRepository<Failure,Integer> {
    List<Failure> findAll();
    Failure findById(long id);
}
