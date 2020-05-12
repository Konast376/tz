package library.api.book.dto;

import library.model.author.Author;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateBookDto {
    private String bookName;
    private int numberOfPages;
    private int publicationYear;
    private Long authorId;
}
