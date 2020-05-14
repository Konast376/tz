package library.api.book.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UpdateBookDto {
    private String bookName;
    private int numberOfPages;
    private int publicationYear;
    private Long authorId;
}