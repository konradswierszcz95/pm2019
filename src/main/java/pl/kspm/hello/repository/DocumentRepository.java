package pl.kspm.hello.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kspm.hello.model.Document;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document,Integer> {
    List<Document> findAllByMachineDocumentId(long id);
}
