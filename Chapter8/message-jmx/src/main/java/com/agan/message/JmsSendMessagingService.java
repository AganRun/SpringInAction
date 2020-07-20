package com.agan.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


@Service
public class JmsSendMessagingService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsSendMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(MyMessage message) {
        jmsTemplate.send(session -> session.createObjectMessage(message));
        jmsTemplate.send("taco.queue", session -> session.createObjectMessage(message));
    }

    public void convertAndSendMessage(MyMessage message) {
        jmsTemplate.convertAndSend(message);
        jmsTemplate.convertAndSend("taco.queue", message);
    }

    public void convertAndSendMessage(MyMessage message, Boolean isDeal) {
        jmsTemplate.convertAndSend(message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("X_SOURCE", "MASTER");
                return message;
            }
        });
//        jmsTemplate.convertAndSend("taco.queue", message);
    }
}
