package service.java.quotes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import service.java.quotes.exceptions.QuotesNotFoundException;

@ControllerAdvice
class QuotesNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(QuotesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String quotesNotFoundHandler(QuotesNotFoundException ex) {
        return ex.getMessage();
    }
}