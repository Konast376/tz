package library.api.author.dto;

import lombok.*;

import java.util.Date;


@Value
@Builder
public class CreateAuthorDto {
    private String fullName;
    private Date dateOfBirth;
    private String nationality;
}
