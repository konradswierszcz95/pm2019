package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MachineCheckController {

    @GetMapping("/checkMachine")
    public String checkMachinePage() {
        return "checkMachine";
    }
}
