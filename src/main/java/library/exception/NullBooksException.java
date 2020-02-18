package library.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NullBooksException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public NullBooksException(String message) {
        super(message);
    }

    public NullBooksException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
