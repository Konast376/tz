package library.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String bookName;
    private int numberOfPages;
    private Date publicationYear;
    private Long authorId;
}
