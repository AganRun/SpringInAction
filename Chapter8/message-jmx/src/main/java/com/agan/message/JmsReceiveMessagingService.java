package com.agan.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;


@Service
public class JmsReceiveMessagingService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsReceiveMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Message receive() {
        Message message = jmsTemplate.receive();
        return message;
    }

    public Object receiveAndConvert() {
        Object message2 = jmsTemplate.receiveAndConvert();
        return message2;
    }

    @JmsListener(destination = "taco.queue")
    public void listenReceive(MyMessage message) {
        System.out.println("listener" + message);
    }

}
