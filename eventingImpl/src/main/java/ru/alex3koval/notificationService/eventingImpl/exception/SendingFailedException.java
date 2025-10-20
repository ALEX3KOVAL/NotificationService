package ru.alex3koval.notificationService.eventingImpl.exception;

public class SendingFailedException extends Exception {
    public SendingFailedException(String message) {
        super(message);
    }
}
