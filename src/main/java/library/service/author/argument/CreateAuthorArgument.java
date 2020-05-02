package library.service.author.argument;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Builder
@Value
public class CreateAuthorArgument {
    //Полное имя автора
    private String fullName;

    //Дата  рождения автора
    private Date dateOfBirth;

    //Национальность
    private String nationality;
}
