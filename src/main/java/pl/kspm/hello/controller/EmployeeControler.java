package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.form.AddEmployeeForm;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.service.EmployeeService;
import pl.kspm.hello.tools.UserObject;

import java.util.Collection;


@Controller
public class EmployeeControler {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("case","list");
        model.addAttribute("userList",this.employeeService.getAllEmployees());
        return "employees";
    }


    @PreAuthorize("hasAnyRole('ROOT')")
    @RequestMapping(value = "/employees/addEmployee", method = RequestMethod.GET)
    public String getAddEmployeeForm(Model model) {
        model.addAttribute("case","addUser");
        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT')")
    @RequestMapping(value = "/employees/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute(name = "addEmployeeForm")AddEmployeeForm addEmployeeForm, Model model) {
        UserObject userObject = new UserObject();
        userObject.setFirstName(addEmployeeForm.getEmployeeName())
                .setLastName(addEmployeeForm.getEmployeeSurname())
                .setEmail(addEmployeeForm.getEmployeeEmail())
                .setLocality(addEmployeeForm.getEmployeeLocality())
                .setStreet(addEmployeeForm.getEmployeeStreet())
                .setZipCode(addEmployeeForm.getEmployeeZipCode())
                .setPostLocality(addEmployeeForm.getEmployeePostLocality())
                .setHouseNumber(addEmployeeForm.getEmployeeHouseNumber())
                .setCountry(addEmployeeForm.getEmployeeCountry())
                .setPhone(addEmployeeForm.getEmployeePhone());

        int errorCode = employeeService.addNewEmployee(userObject);

        model.addAttribute("errorMessage",employeeService.errorMessage(errorCode));
        return "redirect:/employees";
    }
}
