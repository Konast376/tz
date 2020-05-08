package library.action;

import com.whitesoft.util.actions.BaseAction;
import library.model.author.Author;
import library.model.book.Book;
import library.service.author.AuthorService;
import library.service.book.BookService;
import library.service.book.argument.CreateBookArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBookAction extends BaseAction<Book, CreateBookActionArgument> {
    private final AuthorService authorService;
    private final BookService bookService;

    @Override
    protected Book executeImpl(CreateBookActionArgument argument) {
        Author author = authorService.getExisting(argument.getAuthorId());

        Book book = bookService.create(CreateBookArgument.builder()
                                             .author(author)
                                             .build());

        try {
            return bookService.create(CreateBookArgument.builder()
                                                        .bookName(book.getBookName())
                                                        .numberOfPages(book.getNumberOfPages())
                                                        .publicationYear(book.getPublicationYear())
                                                        .author(book.getAuthor())
                                                        .build());
        } catch (Exception exception) {
            throw exception;
        }
    }
}


