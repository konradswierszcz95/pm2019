package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddMachineController {

    @GetMapping("/addMachine")
    public String home() {
        return "addMachine";
    }
}
