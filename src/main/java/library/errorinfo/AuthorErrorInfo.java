package library.errorinfo;

import com.whitesoft.util.errorinfo.ErrorInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum AuthorErrorInfo implements ErrorInfo {
    FULLNAME_IS_MANDATORY("Полное имя автора не передано");

    private final int code = ordinal() + 100;
    private final String message;
}
