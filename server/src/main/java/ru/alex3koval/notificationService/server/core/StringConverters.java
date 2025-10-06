package ru.alex3koval.notificationService.server.core;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringConverters {
    public static String stackTracesToString(StackTraceElement[] stackTraceElements) {
        return Arrays
            .stream(stackTraceElements)
            .map(StackTraceElement::toString)
            .collect(Collectors.joining("\n"));
    }

    public static String getExceptionMessage(Throwable exc) {
        String exceptionMessage = exc.getMessage();

        if (exceptionMessage == null) {
            exceptionMessage = exc.getCause().getMessage();
        }

        return exceptionMessage == null ? "Неизвестная ошибка" : exceptionMessage;
    }
}
