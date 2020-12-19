package exception;

public class NotFoundPathException extends RuntimeException{
    public NotFoundPathException(String message) {
        super(message);
    }
}
