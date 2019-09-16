package service.java.quotes.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.h2.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import service.java.quotes.exceptions.InvalidParameterException;
import service.java.quotes.models.Quote;
import service.java.quotes.repository.QuotesRepository;
import service.java.quotes.exceptions.QuotesNotFoundException;

import java.util.List;
import java.util.Objects;

@Api(description="Operations CRUD on quotes")
@RestController
@Slf4j
public class QuotesController {
    private final QuotesRepository repository;

    QuotesController(QuotesRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get all Quotes")
    @GetMapping(path = "/quotes", produces = "application/json")
    List<Quote> getAllQuotes() {
        return repository.findAll();
    }

    @ApiOperation(value = "Get quote")
    @GetMapping(path = "/quotes/{id}", produces = "application/json")
    Quote getQoute(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuotesNotFoundException(id));
    }

    @ApiOperation(value = "Add quote")
    @PostMapping(path = "/quotes", produces = "application/json")
    Quote createQuote(@RequestBody Quote newQuotes) {
        String id = newQuotes.getId();
        if (StringUtils.isNullOrEmpty(id)){
            throw new InvalidParameterException("Empty Id");
        }
        if (repository.existsById(id)){
            throw new InvalidParameterException("Id already exists");
        }
        return repository.save(newQuotes);
    }

    @ApiOperation(value = "Update quote")
    @PutMapping(path = "/quotes/{id}", produces = "application/json")
    Quote updateQuote(@RequestBody Quote newQuote, @PathVariable String id) {
        if (!Objects.equals(id, newQuote.getId())){
            throw new InvalidParameterException("Id not matched");
        }
        if (StringUtils.isNullOrEmpty(newQuote.getDescription())){
            throw new InvalidParameterException("Empty description");
        }
        if (StringUtils.isNullOrEmpty(newQuote.getTitle())){
            throw new InvalidParameterException("Empty title");
        }
        if (newQuote.getPubDate() == null){
            throw new InvalidParameterException("Empty date");
        }
        Quote oldQuote = getQoute(id);
        newQuote.setCreatedAt(oldQuote.getCreatedAt());
        return repository.save(newQuote);
    }

    @ApiOperation(value = "Delete quote", produces = "application/json")
    @DeleteMapping(path = "/quotes/{id}")
    ResponseEntity deleteQuote(@PathVariable String id) {
        repository.deleteById(id);
        return new ResponseEntity("Quote deleted successfully", HttpStatus.OK);
    }
}
