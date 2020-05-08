package library.action;

import com.whitesoft.util.actions.ActionArgument;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateBookActionArgument implements ActionArgument {
    //Идентификатор автора
    private final Long authorId;

    @Override
    public boolean validate() {
        return authorId != null;
    }
}
