package library.service.book.argument;

import library.model.author.Author;
import lombok.Getter;

import java.util.Date;

@Getter
public class CreateBookArgument {
    private String bookName;
    private int numberOfPages;
    private Date publicationYear;
    private Author author;
}
