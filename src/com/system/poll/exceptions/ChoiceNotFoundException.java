package com.system.poll.exceptions;

public class ChoiceNotFoundException extends RuntimeException {
    public ChoiceNotFoundException(String message) {
        super(message);
    }
}
