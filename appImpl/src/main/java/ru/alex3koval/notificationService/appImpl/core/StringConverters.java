package ru.alex3koval.notificationService.appImpl.core;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringConverters {
    public static String stackTracesToString(StackTraceElement[] stackTraceElements) {
        return Arrays
            .stream(stackTraceElements)
            .map(StackTraceElement::toString)
            .collect(Collectors.joining("\n"));
    }
}
