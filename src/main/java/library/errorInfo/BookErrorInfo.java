package library.errorInfo;

import com.whitesoft.util.errorinfo.ErrorInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BookErrorInfo implements ErrorInfo {
    BOOK_NAME_IS_MANDATORY("Наименование книги не передано"),
    NOT_FOUND("Книга не найдена");

    private final int code = ordinal() + 100;
    private final String message;
}