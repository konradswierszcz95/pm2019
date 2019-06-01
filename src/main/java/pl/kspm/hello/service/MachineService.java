package pl.kspm.hello.service;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.model.Machine;
import pl.kspm.hello.repository.MachineRepository;
import pl.kspm.hello.tools.FindExtension;
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

    public void addMachine(Machine machine) throws IOException, WriterException {
        Timestamp ts = new Timestamp(new Date().getTime());
        machine.setAdded(ts)
            .setCode("["+machine.getId()+"] - "+ts.toString()+machine.getProducer()+machine.getModel());
        this.machineRepository.save(machine);

        QRcodeGenerator.generateQRCode(machine.getCode(),machine.getId());
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

        String fileName = id+ FindExtension.of(file.getOriginalFilename());

        Machine m = this.machineRepository.findById(id);
        if(!fileName.equals("")) {
            m.setFoto(fileName);
        }

        this.machineRepository.save(m);

        Files.write(Paths.get(path + fileName),file.getBytes());
    }
}
