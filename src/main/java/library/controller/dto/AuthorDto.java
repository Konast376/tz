package library.controller.dto;

import library.entity.Book;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class AuthorDto {
    private Long id;
    private String fullName;
    private Date dateOfBirth;
    private String nationality;
    private Set<Book> books;
}
