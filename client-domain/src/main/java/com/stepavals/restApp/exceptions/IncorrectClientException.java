package com.stepavals.restApp.exceptions;

/**
 * Created by Степан on 02.04.2016.
 */
public class IncorrectClientException extends Exception {
    public IncorrectClientException(String message) {
        super(message);
    }

    public IncorrectClientException() {
    }
}
