package io.academy.backend.academy.exceptions;

public class RepositoryOperationException extends RuntimeException{
    public RepositoryOperationException(String message){
        super(message);
    }
}
