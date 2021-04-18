package exceptions;

public class AbortCommandException extends RuntimeException {
    public AbortCommandException(String message) {
        super(message);
    }
}
