package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.model.User;

import java.util.List;

public interface FailureRepository extends CrudRepository<Failure,Integer> {
    List<Failure> findAll();
    Failure findById(long id);

    List<Failure> findAllByEnder(User user);
    List<Failure> findAllByRaportIsNotNull();

    List<Failure> findAllByRaportIsNotNullAndBrokenMachine_Id(long id);

    List<Failure> findAllByAcceptedIsNull();

}
