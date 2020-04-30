package library.api.author.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UpdateAuthorDto {
    private String fullName;
    private Date dateOfBirth;
    private String nationality;
}
