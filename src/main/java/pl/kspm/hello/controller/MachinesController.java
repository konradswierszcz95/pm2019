package pl.kspm.hello.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.form.AddDocument;
import pl.kspm.hello.form.AddMachine;
import pl.kspm.hello.model.Machine;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.service.MachineService;
import pl.kspm.hello.tools.Downloader;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class MachinesController {

    @Autowired
    MachineService machineService;
    @Autowired
    FailureService failureService;

    @GetMapping("/machines")
    public String machineList(Model model) {
        model.addAttribute("machineList", this.machineService.getAllMachines());
        model.addAttribute("case","list");
        return "machines";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @GetMapping("/machines/add")
    public String addMachine(Model model){
        model.addAttribute("case","add");
        return "machines";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @PostMapping("/machines/add")
    public String add(@ModelAttribute(name = "addMachine")AddMachine addMachine) throws IOException, WriterException {
        Machine machine = new Machine();
        machine.setProducer(addMachine.getProducer())
                .setModel(addMachine.getModel())
                .setServiceNumber(addMachine.getService())
                .setBuilding(addMachine.getBuilding())
                .setLocalisation(addMachine.getLocalisation())
                .setDescription(addMachine.getDescription());
        this.machineService.addMachine(machine);
        return "redirect:/machines";
    }

    @GetMapping("/machines/{id}")
    public String machineIndividualPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("machine",this.machineService.getMachineById(id));
        model.addAttribute("case","machine");
        model.addAttribute("documents",this.machineService.getAllDocumentForMachine(id));
        model.addAttribute("thisFailures",this.failureService.getAllEndedForMachine(id));

        return "machines";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @GetMapping("/machines/addFoto/{id}")
    public String addMachineFoto(@PathVariable("id") long id, Model model) {
        model.addAttribute("machine",this.machineService.getMachineById(id));
        model.addAttribute("case","addFoto");
        return "machines";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @PostMapping("/machines/addFoto/{id}")
    public String fotoAddPost(@PathVariable("id") long id, Model model, @RequestParam("foto") MultipartFile file) throws IOException {
        this.machineService.addFoto(id,file);

        return "redirect:/machines/"+id;
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @GetMapping("/machines/addDocuments/{id}")
    public String addDocuments (@PathVariable("id") long id, Model model) {
        model.addAttribute("machine",this.machineService.getMachineById(id));
        model.addAttribute("case","addDocuments");
        return "machines";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE')")
    @PostMapping("/machines/addDocuments/{id}")
    public String documentAddPost(@PathVariable("id") long id, Model model, @ModelAttribute("addDocument") AddDocument addDocument) throws IOException{
        MultipartFile file = addDocument.getDocument();
        String description = addDocument.getDescription();
        String localisation = addDocument.getLocalisation();

        this.machineService.addFile(id,file,description,localisation);

        return "redirect:/machines/"+id;
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_MACHINE','ACCEPT_FAILURE')")
    @GetMapping("/uploads/machines/documents/{id}/{filename}")
    @ResponseBody
    public void downloadDocument(@PathVariable("id") long id, @PathVariable("filename") String fileName, HttpServletResponse response) {
        Downloader.download(this.machineService.getFilePath(id,fileName),"name",response);
    }
}
