package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Account;

public interface AccountRepository extends CrudRepository<Account,Integer> {
}
