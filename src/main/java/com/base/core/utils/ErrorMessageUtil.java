package com.base.core.utils;

public class ErrorMessageUtil {
    public static String format(StackTraceElement[] traceElements) {
        StringBuilder message = new StringBuilder();
        for (StackTraceElement traceElement : traceElements) {
            message.append("\n\t").append(traceElement.toString());
        }
        return message.toString();
    }

    public static String format(Exception e) {
        StringBuilder message = new StringBuilder();
        message.append(e.getLocalizedMessage());
        for (StackTraceElement traceElement : e.getStackTrace()) {
            message.append("\n\t").append(traceElement.toString());
        }
        return message.toString();
    }
}
