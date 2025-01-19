package com.becoder.exception;

public class ResourceAlreadyExistException extends Exception{
    public ResourceAlreadyExistException(){
        super();
    }

    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
