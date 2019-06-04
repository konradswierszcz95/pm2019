package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.form.PassForm;
import pl.kspm.hello.form.SendMsg;
import pl.kspm.hello.model.User;
import pl.kspm.hello.service.AccountService;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.service.MessageService;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    @Autowired
    FailureService failureService;

    @GetMapping("/")
    public String myAccountPage(Model model) {
        User user = accountService.getCurrentUser();
        model.addAttribute("case", "welcome");
        model.addAttribute("user",user);
        model.addAttribute("unreaded",this.messageService.numberOfUnreadeMessages(UserContext.getCurrentUser()));
        model.addAttribute("failures",this.failureService.getNumberOfUnaccepted());

        return "home";
    }

    @GetMapping("/home/pass")
    public String changePassword(Model model) {
        model.addAttribute("case", "pass");
        return "home";
    }

    @PostMapping("/home/pass")
    public String postPass(@ModelAttribute(name = "passChange") PassForm passForm, Model model) {
        String oldPass = passForm.getOldPass();
        String newPass = passForm.getNewPass();
        String repeat = passForm.getRepeat();
        model.addAttribute("case", "pass");
        model.addAttribute("info",this.accountService.changePassword(oldPass,newPass,repeat));

        return "home";
    }
}
