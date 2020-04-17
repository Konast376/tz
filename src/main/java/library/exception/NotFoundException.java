package library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Record not found with id :")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}