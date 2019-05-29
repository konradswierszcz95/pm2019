package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.form.SendMsg;
import pl.kspm.hello.service.MessageService;
import pl.kspm.hello.tools.MsgObject;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/sendMessage")
    public String messageListPage(Model model) {
        model.addAttribute("recivedMsg",this.messageService.currentUserRecivedMsg(2));
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

    @PostMapping("/sendMessage/send")
    public String sending(@ModelAttribute(name = "sendMsg")SendMsg sendMsg, Model model) {
        MsgObject msgObject = new MsgObject();
        msgObject.setAddressee(sendMsg.getAddressee())
                .setAuthor("CurrentUser :D")
                .setSubject(sendMsg.getSubject())
                .setContent(sendMsg.getContent());

        messageService.sendMsg(msgObject);
        return "sendMessage";
    }
}
