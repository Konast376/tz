package library.controller.dto;

import library.entity.Author;
import lombok.Data;

import java.util.Date;

@Data
public class BookDto {
    private Long id;
    private String bookName;
    private int numberOfPages;
    private Date publicationYear;
    private Author authorId;
}
