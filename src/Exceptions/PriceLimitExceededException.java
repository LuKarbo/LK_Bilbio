package Exceptions;

public class PriceLimitExceededException extends Exception {
    public PriceLimitExceededException(String message) {
        super(message);
    }
}
