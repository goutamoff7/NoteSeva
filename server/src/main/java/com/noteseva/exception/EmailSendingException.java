package com.noteseva.exception;

public class EmailSendingException extends  RuntimeException{
    public EmailSendingException(String message){
        super(message);
    }
}
