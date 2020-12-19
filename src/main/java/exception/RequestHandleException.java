package exception;

public class RequestHandleException extends RuntimeException {
    public RequestHandleException(Throwable cause) {
        super(cause);
    }

    public RequestHandleException(String message) {
        super(message);
    }
}
