package library.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NullBooksException extends RuntimeException {

    public NullBooksException(String message) {
        super(message);
    }

}
