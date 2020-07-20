package com.agan.controller;

import com.agan.message.JmsSendMessagingService;
import com.agan.message.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JmsController {

    @Autowired
    private JmsSendMessagingService service;

    @GetMapping("/send")
    public void send(String name) {
        String uuid = UUID.randomUUID().toString();
        MyMessage message = new MyMessage();
        message.setUuid(uuid);
        message.setName(name);
        service.sendMessage(message);
        service.convertAndSendMessage(message);
        service.convertAndSendMessage(message, true);
    }
}
