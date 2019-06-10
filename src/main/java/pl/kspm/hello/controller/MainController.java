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
import pl.kspm.hello.repository.UserRepository;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.tools.Pathes;
import pl.kspm.hello.tools.QRcodeGenerator;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    RoleInterface roleInterface;
    @Autowired
    UserRepository userConnector;
    @Autowired
    MachineRepository machineRepository;
    @Autowired
    FailureService failureService;

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/hello")
    public String home(ModelMap modelMap) {
        modelMap.put("hello","Witaj świecie");
        return "hello";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/cr")
    public String cr() {
        /*Role r = new Role();
        r.setRolename("ROOT")
                .setRoleDescription("root");
        this.roleInterface.save(r);

        Role r1 = new Role();
        r1.setRolename("ADD_USER")
            .setRoleDescription("Dodawanie użytkowników");
        this.roleInterface.save(r1);

        Role r2 = new Role();
        r2.setRolename("DELETE_USER")
                .setRoleDescription("Usuwanie użytkowników");
        this.roleInterface.save(r2);

        Role r3 = new Role();
        r3.setRolename("ADD_MACHINE")
                .setRoleDescription("Dodawanie maszyn");
        this.roleInterface.save(r3);

        Role r4 = new Role();
        r4.setRolename("DELETE_MACHINE")
                .setRoleDescription("Usuwanie(dezaktywacja) maszyn");
        this.roleInterface.save(r4);

        Role r5 = new Role();
        r5.setRolename("ACCEPT_FAILURE")
                .setRoleDescription("Akceptowanie i kończenie awarii oraz dodawanie raportów");
        this.roleInterface.save(r5);

        Role r6 = new Role();
        r6.setRolename("EDIT_PERMISSIONS")
                .setRoleDescription("Edycja uprawnień");
        this.roleInterface.save(r6);

        Role r2 = new Role();
        r2.setRolename("ADMIN");

        Role r3 = new Role();
        r3.setRolename("USER");

        //this.roleInterface.save(r1);
        /*this.roleInterface.save(r2);
        this.roleInterface.save(r3);*/

        return "hello";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @GetMapping("/ar")
    public String addRole() {
        /*User user = userConnector.findFirstById(1);
        user.addRole(roleInterface.findById(1));
        //user.addRole(roleInterface.findById(3));
        //user.addRole(roleInterface.findById(4));

        userConnector.save(user);*/

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
