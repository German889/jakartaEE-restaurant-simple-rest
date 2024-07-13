package com.aston.second_task.exceptions;

public class ElementNotDeletedException extends RuntimeException{
    public ElementNotDeletedException(String message) {
        super(message);
    }
}
