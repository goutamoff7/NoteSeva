package com.noteseva.exception;

public class EmailSendingException extends  RuntimeException{
    public EmailSendingException(String messgae){
        super(messgae);
    }
}
