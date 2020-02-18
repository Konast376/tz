package library.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class AuthorDeleteException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public AuthorDeleteException(String message) {
        super(message);
    }

    public AuthorDeleteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

