package pl.kspm.hello.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kspm.hello.model.*;
import pl.kspm.hello.repository.MachineRepository;
import pl.kspm.hello.repository.RoleInterface;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.tools.Pathes;
import pl.kspm.hello.tools.QRcodeGenerator;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    RoleInterface roleInterface;
    @Autowired
    UserConnectorRepository userConnector;
    @Autowired
    MachineRepository machineRepository;

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/hello")
    public String home(ModelMap modelMap) {
        modelMap.put("hello","Witaj świecie");
        return "hello";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/cr")
    public String cr() {
        Role r1 = new Role();
        r1.setRolename("ROOT");

        Role r2 = new Role();
        r2.setRolename("ADMIN");

        Role r3 = new Role();
        r3.setRolename("USER");

        this.roleInterface.save(r1);
        this.roleInterface.save(r2);
        this.roleInterface.save(r3);

        return "hello";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/ar")
    public String addRole() {
        User user = userConnector.findFirstById(1);
        user.addRole(roleInterface.findById(1));
        //user.addRole(roleInterface.findById(2));
        //user.addRole(roleInterface.findById(3));

        userConnector.save(user);

        return "hello";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/new_qr")
    public String recreateQrCode() throws IOException, WriterException {
        List<Machine> machines = this.machineRepository.findAll();
        for (Machine m: machines) {
            m.setCode(Pathes.appHost + "machines/"+m.getId());
            QRcodeGenerator.generateQRCode(m.getId());

            this.machineRepository.save(m);
        }
        return "hello";
    }

}
