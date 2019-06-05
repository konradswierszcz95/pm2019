package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.form.AddEmployeeForm;
import pl.kspm.hello.form.Permissions;
import pl.kspm.hello.model.Role;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.service.EmployeeService;
import pl.kspm.hello.service.RoleService;
import pl.kspm.hello.tools.UserObject;

import java.util.Collection;
import java.util.List;


@Controller
public class EmployeeControler {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;

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

    @GetMapping ("/employees/permissions/{userId}")
    public String editPermissions(@PathVariable("userId") long id, Model model) {
        List<Role> roles = this.employeeService.getRoleList();
        List<String> userRoles = this.employeeService.getRoleListById(id);
        User user = this.employeeService.getUserById(id);

        model.addAttribute("case","permissions");
        model.addAttribute("allRoles",roles);
        model.addAttribute("userRoles",userRoles);
        model.addAttribute("user",user);

        for (String r:
             userRoles) {
            System.out.println(r);
        }

        return "/employees";
    }

    @PostMapping("/employees/permissions/{userId}")
    public String postEditPermissions(@PathVariable("userId") long id, Model model,
                                      @RequestParam(value = "permissions" , required = false) int[] permissions,
                                      @ModelAttribute(name = "permissions") Permissions pass) {
        String password = pass.getPassword();
        String info;
        if (permissions!=null) {
            info = this.employeeService.changeRoles(permissions,id,password);
        } else {
            info = this.employeeService.changeRoles(id,password);
        }
        System.out.println(info);
        model.addAttribute("info",info);
        model.addAttribute("case","permissions");
        model.addAttribute("user",this.employeeService.getUserById(id));
        return "/employees";
    }
}
