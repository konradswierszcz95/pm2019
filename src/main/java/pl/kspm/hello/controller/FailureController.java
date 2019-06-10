package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.form.AddDocument;
import pl.kspm.hello.form.AddFailure;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.service.MachineService;
import pl.kspm.hello.tools.Downloader;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

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

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
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

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @GetMapping("/failures/end/{id}")
    public String end(@PathVariable("id") long id, Model model) {
        this.failureService.endFailure(id);
        return "redirect:/failures/myEnded";
    }

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @GetMapping("/failures/myEnded")
    public String myEndedList(Model model) {
        model.addAttribute("case","myEnded");
        model.addAttribute("failuresList",failureService.getMyEndedFailures());
        return "failures";
    }

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @GetMapping("/failures/addRaport/{id}")
    public String addRaportGet(@PathVariable("id")long id, Model model) {
        model.addAttribute("case", "addRaport");
        model.addAttribute("failure", this.failureService.getFailureById(id));

        return "failures";
    }

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @PostMapping("/failures/addRaport/{id}")
    public String addRaportPost(@PathVariable("id")long id, @ModelAttribute("addDocument")AddDocument addDocument) throws IOException {
        MultipartFile file = addDocument.getDocument();

        this.failureService.uploadRaport(id,file);
        return "redirect:/failures/myEnded";
    }

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @GetMapping("uploads/failures/raports/{filename}")
    @ResponseBody
    public void getRaportPattern(HttpServletResponse response, @PathVariable("filename") String fileName) {
        String filePath=this.failureService.getRaportDirectoryPath();
        String path = new File("")+filePath+fileName;
        System.out.println(path);
        Downloader.download(path,"name",response);
    }

    @PreAuthorize("hasAnyRole('ROOT','ACCEPT_FAILURE')")
    @GetMapping("/failures/ended")
    public String endedList(Model model) {
        model.addAttribute("case", "ended");
        model.addAttribute("failuresList", this.failureService.getAllWithRaport());
        return "failures";
    }
}
