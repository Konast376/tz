package library.service.argument;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CreateAuthorArgument {
    private Long id;
    private String fullName;
    private Date dateOfBirth;
    private String nationality;
}
