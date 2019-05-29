package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PermissonsController {

    @GetMapping("/addPermissions")
    public String home() {
        return "addPermissions";
    }
}
