package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.FailureRepository;
import pl.kspm.hello.tools.Downloader;
import pl.kspm.hello.tools.FindExtension;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

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

    public Iterable<Failure> getMyEndedFailures() {
        Iterable<Failure> myFailures = this.failureRepository.findAllByEnder(UserContext.getCurrentUser());
        return myFailures;
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

    public String getRaportDirectoryPath() {
        return new File("").getAbsolutePath()+File.separator+"uploads"+File.separator+"failures"
                +File.separator+"raports"+File.separator;
    }

    public Failure getFailureById(long id) {
        return this.failureRepository.findById(id);
    }

    public void uploadRaport(long id, MultipartFile file) throws IOException {
        String fileName ="raport-"+id+FindExtension.of(file.getOriginalFilename());
        String path = getRaportDirectoryPath();

        Failure f = this.failureRepository.findById(id);
        if (!file.isEmpty()){
            f.setRaport(fileName);
        }

        this.failureRepository.save(f);

        Downloader.upload(path,fileName,file);
    }

    public String getRaportName(long id) {
        String answer;
        if (id==0) {
            answer="raportWzor.docx";
            System.out.println("xd");
        } else {
            answer=this.failureRepository.findById(id).getRaport();
        }
        return answer;
    }

    public List<Failure> getAllWithRaport() {
        return this.failureRepository.findAllByRaportIsNotNull();
    }

    public List<Failure> getAllEndedForMachine(long id) {
        return this.failureRepository.findAllByRaportIsNotNullAndBrokenMachine_Id(id);
    }

    public int getNumberOfUnaccepted() {
        return this.failureRepository.findAllByAcceptedIsNull().size();
    }
}
