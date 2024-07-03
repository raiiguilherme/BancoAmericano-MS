package org.grupo5.mscalculate.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.grupo5.mscalculate.exceptions.ex.RuleNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageError> camposInvalidos(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageError(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid fields", result));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageError> categoryAlreadyExist(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageError(request, HttpStatus.UNPROCESSABLE_ENTITY, "Category already exist"));
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageError> camposInvalidos(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageError(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid field"));
    }

    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<MessageError> ruleNotFound(RuleNotFoundException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageError(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }





}
