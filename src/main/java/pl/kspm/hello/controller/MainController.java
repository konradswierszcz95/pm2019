package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kspm.hello.model.*;
import pl.kspm.hello.repository.RoleInterface;
import pl.kspm.hello.repository.UserConnectorRepository;

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class MainController {
    @Autowired
    RoleInterface roleInterface;
    @Autowired
    UserConnectorRepository userConnector;

    @GetMapping("/")
    public String home(ModelMap modelMap) {
        modelMap.put("hello","Witaj świecie");
        return "hello";
    }

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

    @GetMapping("/ar")
    public String addRole() {
        UserConnector user = userConnector.findFirstById(2);
        user.addRole(roleInterface.findById(1));
        user.addRole(roleInterface.findById(2));
        user.addRole(roleInterface.findById(3));

        userConnector.save(user);

        return "hello";
    }

}
