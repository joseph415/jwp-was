package exception;

public class NotFoundController extends RuntimeException{
    public NotFoundController() {
    }

    public NotFoundController(String message) {
        super(message);
    }
}
