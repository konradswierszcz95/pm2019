package pl.kspm.hello.service;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.model.Document;
import pl.kspm.hello.model.Machine;
import pl.kspm.hello.repository.DocumentRepository;
import pl.kspm.hello.repository.MachineRepository;
import pl.kspm.hello.tools.Downloader;
import pl.kspm.hello.tools.FindExtension;
import pl.kspm.hello.tools.Pathes;
import pl.kspm.hello.tools.QRcodeGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class MachineService {

    @Autowired
    MachineRepository machineRepository;
    @Autowired
    DocumentRepository documentRepository;

    public void addMachine(Machine machine) throws IOException, WriterException {
        Timestamp ts = new Timestamp(new Date().getTime());
        machine.setAdded(ts);
        this.machineRepository.save(machine);
        machine.setCode(Pathes.appHost + "machines/"+machine.getId());
        QRcodeGenerator.generateQRCode(machine.getId());
    }

    public Iterable<Machine> getAllMachines() {
        Iterable<Machine> machines = this.machineRepository.findAll();
        return machines;
    }

    public Machine getMachineById(long id) {
        return this.machineRepository.findById(id);
    }

    public void addFoto(long id, MultipartFile file) throws IOException{
        String path = new File("").getAbsolutePath()+File.separator+"uploads"+File.separator+"machines"+File.separator+"foto"+File.separator;
        String extension = FindExtension.of(file.getOriginalFilename()).toLowerCase();
        String fileName = id+ extension;

        Machine m = this.machineRepository.findById(id);
        if(extension.equals(".png") && !file.isEmpty()) {
            m.setFoto(fileName);
            this.machineRepository.save(m);

            Files.write(Paths.get(path + fileName),file.getBytes());
        }
    }

    public void addFile(long id, MultipartFile file,String description,String localisation) throws IOException{
        String path = new File("").getAbsolutePath()+File.separator+"uploads"+File.separator+"machines"+
                File.separator+"documents"+File.separator+id+File.separator;

        String fileName = file.getOriginalFilename();

        if (description.isEmpty()) {
            description="BezNazwy";
        }

        if (!file.isEmpty()) {
            Document document = new Document();
            document.setMachineDocument(this.getMachineById(id))
                    .setFileName(file.getOriginalFilename())
                    .setDescription(description)
                    .setPaperCopyLocalisation("Kopaia papierowa: " + localisation);
            this.documentRepository.save(document);
        }

        Downloader.upload(path,fileName,file);
/*
        File isDir = new File(path);
        if(!isDir.exists()) {
            try {
                isDir.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Files.write(Paths.get(path + fileName),file.getBytes());*/
    }

    public Iterable<Document> getAllDocumentForMachine(long machineId) {
        Iterable<Document> documents = this.documentRepository.findAllByMachineDocumentId(machineId);
        return documents;
    }

    public String getFilePath(long id, String fileName) {
        return new File("").getAbsolutePath()+File.separator+"uploads"+File.separator+"machines"+
                File.separator+"documents"+File.separator+id+File.separator+fileName;
    }

    public void regenereteQr(long id) throws IOException,WriterException{
        QRcodeGenerator.generateQRCode(id);
    }

}
