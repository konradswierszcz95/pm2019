package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.form.SendMsg;
import pl.kspm.hello.model.Failure;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.User;
import pl.kspm.hello.service.EmployeeService;
import pl.kspm.hello.service.FailureService;
import pl.kspm.hello.service.MessageService;
import pl.kspm.hello.tools.MsgObject;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    FailureService failureService;

    @GetMapping("/sendMessage")
    public String messageListPage(Model model) {
        model.addAttribute("recivedMsg",this.messageService.currentUserRecivedMsg());
        String answer = "recived";
        model.addAttribute("case",answer);
        return "sendMessage";
    }

    @GetMapping("/sendMessage/send")
    public String sendMessage(@ModelAttribute(name = "sendMsg") SendMsg sendMsg, Model model) {
        String answer = "sending";
        model.addAttribute("case",answer);
        return "sendMessage";
    }

    @GetMapping("/sendMessage/answer/{messageId}")
    public String answerMessage(@PathVariable("messageId") long msgId, Model model) {
        String answer = "answer";
        Message msg = this.messageService.getMessageById(msgId,UserContext.getCurrentUserId());
        model.addAttribute("case",answer);
        model.addAttribute("msg", msg);
        return "sendMessage";
    }

    @PostMapping("/sendMessage/send")
    public String sending(@ModelAttribute(name = "sendMsg")SendMsg sendMsg, Model model) {
        MsgObject msgObject = new MsgObject();
        msgObject.setAddressee(sendMsg.getAddressee())
                .setSubject(sendMsg.getSubject())
                .setContent(sendMsg.getContent());

        messageService.sendMsg(msgObject);

        return "redirect:/sendMessage/sended";
    }

    @GetMapping("/sendMessage/sended")
    public String sendedList(Model model) {
        model.addAttribute("sendedMsg", this.messageService.currentUserSendedMsg());
        String answer = "sended";
        model.addAttribute("case",answer);

        return "sendMessage";
    }

    @GetMapping("/sendMessage/ask/{userId}/{failureId}")
    public String ask(@PathVariable("userId") long userId, @PathVariable("failureId") long failureId, Model model) {
        User user = this.employeeService.getUserById(userId);
        Failure failure = this.failureService.getFailureById(failureId);

        String to = user.getEmployee().getLogin();
        String subject = "Pytanie odnośnie awarii: id:"+failureId+", producent:"+failure.getBrokenMachine().getProducer()+", model:"+failure.getBrokenMachine().getModel();

        model.addAttribute("case","ask");
        model.addAttribute("to", to);
        model.addAttribute("subject",subject);
        return "sendMessage";
    }

    @GetMapping("/sendMessage/sendTo/{userId}")
    public String sendTo(@PathVariable("userId") long userId, Model model) {
        String to = this.employeeService.getUserById(userId).getEmployee().getLogin();
        model.addAttribute("case","sendTo");
        model.addAttribute("to", to);
        return "sendMessage";
    }

    @GetMapping("/sendMessage/openedMessage/{id}")
    public String openedMsg(@PathVariable("id") long msgId, Model model) {
        long currentUserId = UserContext.getCurrentUserId();
        Message message = messageService.getMessageById(msgId, currentUserId);

        if (message!=null) {
            model.addAttribute("openMsg",message);
            String answer = "opened";
            model.addAttribute("case",answer);
            model.addAttribute("currentUserId",currentUserId);

            message.setReaded("YES");
            this.messageService.save(message);

        } else {
            System.out.println("Błąd");
        }

        return "sendMessage";
    }
}
