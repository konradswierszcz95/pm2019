package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Address;

public interface AddressRepository extends CrudRepository<Address,Integer> {
}
