package com.dbc.walletapi.exceptions;

import io.jsonwebtoken.SignatureException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handleException(PSQLException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        if(exception.getMessage().contains("usuario_login_key")) {
            body.put("status", badRequest.value());
            body.put("message", "Login já existente no banco"); //Mensagem personalizada caso exceção for gerada por usuário repetido.
        }
        else {
            body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            body.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return new ResponseEntity<>(body, badRequest);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        body.put("status", badRequest.value());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, badRequest);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Object> handleException(RegraDeNegocioException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        body.put("status", badRequest.value());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, badRequest);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleException(SignatureException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        final HttpStatus badRequest = HttpStatus.FORBIDDEN;
        body.put("status", badRequest.value());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, badRequest);
    }
}
