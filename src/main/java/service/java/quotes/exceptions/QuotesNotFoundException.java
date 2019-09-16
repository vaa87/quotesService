package service.java.quotes.exceptions;

public class QuotesNotFoundException extends RuntimeException {

    public QuotesNotFoundException(String id) {
        super("Could not find quote " + id);
    }
}