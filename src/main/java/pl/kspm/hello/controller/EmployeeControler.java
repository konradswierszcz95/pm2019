package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.form.AddEmployeeForm;
import pl.kspm.hello.form.Permissions;
import pl.kspm.hello.model.*;
import pl.kspm.hello.service.EmployeeService;
import pl.kspm.hello.service.RoleService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Controller
public class EmployeeControler {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;

    private String info;
    private User temporarilyUser;
    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("case","list");
        model.addAttribute("userList",this.employeeService.getAllEmployees());
        return "employees";
    }


    @PreAuthorize("hasAnyRole('ROOT','ADD_USER')")
    @RequestMapping(value = "/employees/addEmployee", method = RequestMethod.GET)
    public String getAddEmployeeForm(Model model) {
        model.addAttribute("case","addUser");
        model.addAttribute("info", info);

        model.addAttribute("user", temporarilyUser);

        info="";
        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_USER')")
    @RequestMapping(value = "/employees/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute(name = "addEmployeeForm")AddEmployeeForm addEmployeeForm, Model model) {
        User user = new User();
        Employee employee = new Employee();
        Account account = new Account();
        Address address = new Address();

        employee.setEmail(addEmployeeForm.getEmployeeEmail())
                .setPhone(addEmployeeForm.getEmployeePhone())
                .setName(addEmployeeForm.getEmployeeName())
                .setSurname(addEmployeeForm.getEmployeeSurname());

        account.setIsActive(true)
                .setCreated(new Timestamp(new Date().getTime()));

        address.setCountry(addEmployeeForm.getEmployeeCountry())
                .setHouseNumber(addEmployeeForm.getEmployeeHouseNumber())
                .setLocality(addEmployeeForm.getEmployeeLocality())
                .setPostLocality(addEmployeeForm.getEmployeePostLocality())
                .setStreet(addEmployeeForm.getEmployeeStreet())
                .setZipCode(addEmployeeForm.getEmployeeZipCode());

        user.setEmployee(employee)
                .setAccount(account)
                .setAddress(address);

        info = employeeService.addNewEmployee(user);

        if (info.equals("Dodano użytkownika")) {
            temporarilyUser=null;
        } else {
            temporarilyUser = user;
        }

        model.addAttribute("info",info);
        return "redirect:/employees/addEmployee";
    }

    @PreAuthorize("hasAnyRole('ROOT','EDIT_PERMISSIONS')")
    @GetMapping ("/employees/permissions/{userId}")
    public String editPermissions(@PathVariable("userId") long id, Model model) {
        List<Role> roles = this.employeeService.getRoleList();
        List<String> userRoles = this.employeeService.getRoleListById(id);
        User user = this.employeeService.getUserById(id);

        model.addAttribute("case","permissions");
        model.addAttribute("allRoles",roles);
        model.addAttribute("userRoles",userRoles);
        model.addAttribute("user",user);
        model.addAttribute("info",info);
        info="";

        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT','EDIT_PERMISSIONS')")
    @PostMapping("/employees/permissions/{userId}")
    public String postEditPermissions(@PathVariable("userId") long id, Model model,
                                      @RequestParam(value = "permissions" , required = false) int[] permissions,
                                      @ModelAttribute(name = "permissions") Permissions pass) {
        String password = pass.getPassword();
        if (permissions!=null) {
            info = this.employeeService.changeRoles(permissions,id,password);
        } else {
            info = this.employeeService.changeRoles(id,password);
        }
        model.addAttribute("info",info);
        model.addAttribute("case","permissions");
        model.addAttribute("user",this.employeeService.getUserById(id));
        return "redirect:/employees/permissions/"+id;
    }

    @PreAuthorize("hasAnyRole('ROOT','DELETE_USER')")
    @GetMapping("/employees/delete/{userId}")
    public String userDeleteGet(@PathVariable("userId") long id, Model model) {
        model.addAttribute("case","delete");
        model.addAttribute("user",this.employeeService.getUserById(id));
        model.addAttribute("info",info);
        info="";
        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT','DELETE_USER')")
    @PostMapping("/employees/delete/{userId}")
    public String userDeletePost(@PathVariable("userId") long id, @RequestParam(value = "password") String password,
                                 Model model) {
        info = this.employeeService.lockUser(id,password);
        model.addAttribute("info",info);
        return "redirect:/employees/delete/"+id;
    }

    @PreAuthorize("hasAnyRole('ROOT','DELETE_USER','ADD_USER')")
    @GetMapping("/employees/deleted")
    public String deletedUsersList(Model model) {
        model.addAttribute("case","deleted");
        model.addAttribute("userList",this.employeeService.getAllNotActive());
        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_USER')")
    @GetMapping("/employees/deleted/{id}")
    public String redoDeletedUser(Model model,@PathVariable("id")long id) {
        model.addAttribute("case","redo");
        model.addAttribute("user",this.employeeService.getUserById(id));
        model.addAttribute("info",info);
        info="";

        return "employees";
    }

    @PreAuthorize("hasAnyRole('ROOT','ADD_USER')")
    @PostMapping("/employees/deleted/{id}")
    public String redoDeletedUserPost(@RequestParam(value = "password") String pass,
                                      @PathVariable("id") long id, @RequestParam(value = "email") String email) {
        info = this.employeeService.unlockUser(id,pass,email);
        return "redirect:/employees/deleted/"+id;
    }

}
