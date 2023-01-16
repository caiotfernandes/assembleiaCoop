package com.caiotfernandes.assembleiaCoop.resources.exceptions;

import com.caiotfernandes.assembleiaCoop.services.exceptions.ClosedSessionException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.InvalidDateException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(),
                "Não encontrado",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ClosedSessionException.class)
    public ResponseEntity<StandardError> closedSession(ClosedSessionException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(),
                "Sessão encerrada",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<StandardError> invalidDate(InvalidDateException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(),
                "Request inválido",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardError> illegalState(IllegalStateException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(),
                "Request inválido",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> dtoValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(),
                "Request inválido",
                e.getBindingResult().getFieldError().getDefaultMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

}
