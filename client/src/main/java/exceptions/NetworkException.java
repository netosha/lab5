package exceptions;

public class NetworkException extends RuntimeException {
    public NetworkException(String message) {
        super(message);
    }
}
