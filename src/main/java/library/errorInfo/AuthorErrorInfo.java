package library.errorInfo;

import com.whitesoft.util.errorinfo.ErrorInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthorErrorInfo implements ErrorInfo {
    FULLNAME_IS_MANDATORY("Полное имя автора не передано"),
    DATE_OF_BIRTH_IS_MANDATORY("Дата рождения не передана"),
    NOT_FOUND("Автор не найден");

    private final int code = ordinal() + 100;
    private final String message;
}
