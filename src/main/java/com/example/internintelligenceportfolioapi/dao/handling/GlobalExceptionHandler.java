package com.example.internintelligenceportfolioapi.dao.handling;

import com.example.internintelligenceportfolioapi.dao.exception.FoundException;
import com.example.internintelligenceportfolioapi.dao.exception.NotFoundException;
import com.example.internintelligenceportfolioapi.dao.exception.PasswordException;
import com.example.internintelligenceportfolioapi.model.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionDto notFoundHandler(RuntimeException exception) {
        log.info("404 - Not Found !");
        return new ExceptionDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(FoundException.class)
    public ExceptionDto foundHandler(RuntimeException exception) {
        log.info("302 - Found !");
        return new ExceptionDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PasswordException.class)
    public ExceptionDto authHandler(RuntimeException exception) {
        log.info("401 - Unauthorized !");
        return new ExceptionDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionDto handleException(RuntimeException exception) {
        log.info("500 - Internal Server Error !");
        return new ExceptionDto(exception.getMessage());
    }
}
