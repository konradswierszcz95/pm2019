package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasklistController {

    @GetMapping("/createTasklist")
    public String home() {
        return "createTasklist";
    }
}
