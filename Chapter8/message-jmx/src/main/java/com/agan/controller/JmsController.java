package com.agan.controller;

import com.agan.message.JmsReceiveMessagingService;
import com.agan.message.JmsSendMessagingService;
import com.agan.message.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JmsController {

    @Autowired
    private JmsSendMessagingService sendService;
    @Autowired
    private JmsReceiveMessagingService receiveService;

    @GetMapping("/send")
    public void send(String name) {
        String uuid = UUID.randomUUID().toString();
        MyMessage message = new MyMessage();
        message.setUuid(uuid);
        message.setName(name);
//        sendService.sendMessage(message);
        sendService.convertAndSendMessage(message);
//        sendService.convertAndSendMessage(message, true);
    }

    @GetMapping("/receive")
    public void receive() {
//        System.out.println(receiveService.receive());
        System.out.println(receiveService.receiveAndConvert());
    }
}
