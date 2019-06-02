package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.repository.FailureRepository;

import java.util.Date;
import java.sql.Timestamp;

@Service
public class FailureService {
    @Autowired
    FailureRepository failureRepository;
    public void addFailure(Failure f) {
        f.setCreator(UserContext.getCurrentUser())
                .setCreated(new Timestamp(new Date().getTime()))
                .setIsAccept(0);

        this.failureRepository.save(f);
    }

    public Iterable<Failure> getAllFaliures() {
        Iterable<Failure> failures = this.failureRepository.findAll();
        return failures;
    }

    public void acceptFailure(long id) {
        Failure f = this.failureRepository.findById(id);
        f.setAccepted(new Timestamp(new Date().getTime()))
                .setAcceptor(UserContext.getCurrentUser())
                .setIsAccept(1);

        this.failureRepository.save(f);
    }

    public void endFailure(long id) {
        Failure f = this.failureRepository.findById(id);
        f.setEnded(new Timestamp(new Date().getTime()))
                .setEnder(UserContext.getCurrentUser());

        this.failureRepository.save(f);
    }
}
