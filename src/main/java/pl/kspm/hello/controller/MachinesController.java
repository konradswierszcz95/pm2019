package pl.kspm.hello.controller;

import com.google.zxing.WriterException;
import javafx.scene.shape.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kspm.hello.form.AddMachine;
import pl.kspm.hello.model.Machine;
import pl.kspm.hello.service.MachineService;
import pl.kspm.hello.tools.FindExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class MachinesController {

    @Autowired
    MachineService machineService;

    @GetMapping("/machines")
    public String machineList(Model model) {
        model.addAttribute("machineList", this.machineService.getAllMachines());
        model.addAttribute("case","list");
        return "machines";
    }

    @GetMapping("/machines/add")
    public String addMachine(Model model){
        model.addAttribute("case","add");
        return "machines";
    }

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
        return "machines";
    }

    @GetMapping("/machines/addFoto/{id}")
    public String addMachineFoto(@PathVariable("id") long id, Model model) {
        model.addAttribute("machine",this.machineService.getMachineById(id));
        model.addAttribute("case","addFoto");
        return "machines";
    }

    @PostMapping("/machines/addFoto/{id}")
    public String fotoAddPost(@PathVariable("id") long id, Model model, @RequestParam("foto") MultipartFile file) throws IOException {
        this.machineService.addFoto(id,file);

        return "redirect:/machines/"+id;
    }

    @GetMapping("/machines/addDocuments/{id}")
    public String addDocuments (@PathVariable("id") long id, Model model) {
        model.addAttribute("machine",this.machineService.getMachineById(id));
        model.addAttribute("case","addDocuments");
        return "machines";
    }

    @PostMapping("/machines/addDocuments/{id}")
    public String documentAddPost(@PathVariable("id") long id, Model model, @RequestParam("document") MultipartFile file) {

        return "redirect:/machines/"+id;
    }
}
