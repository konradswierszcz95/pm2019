package pl.kspm.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaliureController {

    @GetMapping("/reportFailure")
    public String reportFaliurePage() {
        return "reportFailure";
    }
}
