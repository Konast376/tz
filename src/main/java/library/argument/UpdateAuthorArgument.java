package library.argument;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UpdateAuthorArgument {
    private String fullName;
    private Date dateOfBirth;
    private String nationality;
}
