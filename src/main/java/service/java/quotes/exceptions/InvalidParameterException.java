package service.java.quotes.exceptions;

public class InvalidParameterException  extends RuntimeException {
    public InvalidParameterException(String text) {
        super(text);
    }
}
