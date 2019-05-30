package pl.kspm.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.form.SendMsg;
import pl.kspm.hello.model.Message;
import pl.kspm.hello.model.User;
import pl.kspm.hello.service.MessageService;
import pl.kspm.hello.tools.MsgObject;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

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

        return "sendMessage";
    }

    @GetMapping("/sendMessage/sended")
    public String sendedList(Model model) {
        model.addAttribute("sendedMsg", this.messageService.currentUserSendedMsg());
        String answer = "sended";
        model.addAttribute("case",answer);

        return "sendMessage";
    }

    @GetMapping("/sendMessage/openedMessage/{id}")
    public String openedMsg(@PathVariable("id") long msgId, Model model) {
        long currentUserId = UserContext.getCurrentUserId();
        Message message = messageService.getMessageById(msgId, currentUserId);

        if (message!=null) {
            System.out.println(message.getContent());
            model.addAttribute("openMsg",message);
            String answer = "opened";
            model.addAttribute("case",answer);
            model.addAttribute("currentUserId",currentUserId);
        } else {
            System.out.println("Błąd");
        }

        return "sendMessage";
    }
}
