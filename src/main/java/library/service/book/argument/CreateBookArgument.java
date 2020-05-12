package library.service.book.argument;

import library.model.author.Author;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateBookArgument {
    // Название книги
    private String bookName;

    // Количество страниц
    private int numberOfPages;

    // Год публикации
    private int publicationYear;

    // Автор
    private Author author;
}
