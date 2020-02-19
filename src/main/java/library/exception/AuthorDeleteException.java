package library.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class AuthorDeleteException extends RuntimeException {

    public AuthorDeleteException(String message) {
        super(message);
    }

}

