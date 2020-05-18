package library.action;

import com.whitesoft.util.actions.ActionArgument;
import lombok.Builder;
import lombok.Value;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Value
@Builder
public class CreateBookActionArgument implements ActionArgument {
    private final String bookName;
    private final int numberOfPages;
    private final int publicationYear;

    //Идентификатор автора
    private final Long authorId;

    @Override
    public boolean validate() {
        return isNotBlank(bookName)
               && authorId != null;
    }
}
