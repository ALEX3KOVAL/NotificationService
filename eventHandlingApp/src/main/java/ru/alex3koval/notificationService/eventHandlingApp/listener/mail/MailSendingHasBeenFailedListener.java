package ru.alex3koval.notificationService.eventHandlingApp.listener.mail;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MailSendingHasBeenFailedListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}
