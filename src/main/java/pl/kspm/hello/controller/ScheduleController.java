package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/createSchedule")
    public String createSchedulePage() {
        return "createSchedule";
    }
}
