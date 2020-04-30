package library.service.author.argument;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CreateAuthorArgument {
    //Полное имя автора
    private String fullName;

    //Дата  рождения автора
    private Date dateOfBirth;

    //Национальность
    private String nationality;
}
