package library.service.author.argument;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UpdateAuthorArgument {
    //Полное имя автора
    private String fullName;

    //Дата  рождения автора
    private Date dateOfBirth;

    //Национальность
    private String nationality;
}
