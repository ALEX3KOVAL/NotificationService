package ru.alex3koval.notificationService.eventHandlingApp.listener.mail;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MailHasBeenSentListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Mail has been sent");
    }
}
