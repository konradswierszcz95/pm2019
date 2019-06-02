package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kspm.hello.form.AddFailure;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.service.MachineService;

@Controller
public class FailureController {
    @Autowired
    MachineService machineService;
    @Autowired
    FailureService failureService;

    @GetMapping("/failures")
    public String reportFailurePage(Model model) {
        model.addAttribute("case", "list");
        model.addAttribute("failuresList",failureService.getAllFaliures());

        return "failures";
    }

    @GetMapping("/failures/create/{id}")
    public String addFaliure(@PathVariable("id") long id, Model model){
        model.addAttribute("case","add");
        model.addAttribute("machine", this.machineService.getMachineById(id));
        return "failures";
    }

    @PostMapping("/failures/create/{id}")
    public String add(@ModelAttribute("createFailure")AddFailure addFailure, @PathVariable("id") long id) {
        Failure failure = new Failure();
        failure.setBrokenMachine(this.machineService.getMachineById(id))
                .setShortInfo(addFailure.getDescription());

        this.failureService.addFailure(failure);

        return "redirect:/failures";
    }

    @GetMapping("/failures/accept/{id}")
    public String accept(@PathVariable("id") long id, Model model) {
        this.failureService.acceptFailure(id);
        return "redirect:/failures/accepted";
    }

    @GetMapping("/failures/accepted")
    public String acceptedList(Model model) {
        model.addAttribute("case","accepted");
        model.addAttribute("failuresList",failureService.getAllFaliures());
        return "failures";
    }

    @GetMapping("/failures/end/{id}")
    public String end(@PathVariable("id") long id, Model model) {
        this.failureService.endFailure(id);
        return "redirect:/failures/myEnded";
    }

    @GetMapping("/failures/myEnded")
    public String myEndedList(Model model) {
        model.addAttribute("case","myEnded");
        model.addAttribute("failuresList",failureService.getAllFaliures());
        return "failures";
    }
}
