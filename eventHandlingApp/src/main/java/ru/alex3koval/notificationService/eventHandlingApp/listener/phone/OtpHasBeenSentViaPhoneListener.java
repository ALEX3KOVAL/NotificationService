package ru.alex3koval.notificationService.eventHandlingApp.listener.phone;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class OtpHasBeenSentViaPhoneListener implements MessageListener  {
    @Override
    public void onMessage(Message message) {
        System.out.println("OTP has been sent");
    }
}
