package io.academy.backend.academy.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class, RepositoryOperationException.class})
    protected ResponseEntity<Object> notFound(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);

        return handleExceptionInternal(ex, errorMessage,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
