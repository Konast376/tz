package library.action;

import com.whitesoft.util.actions.BaseAction;
import library.model.author.Author;
import library.model.book.Book;
import library.service.author.AuthorService;
import library.service.book.BookService;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateBookAction extends BaseAction<Book, UpdateBookActionArgument> {
    private final AuthorService authorService;
    private final BookService bookService;
    private final Long id = 1L;

    @Override
    protected Book executeImpl(UpdateBookActionArgument argument) {
        Author author = authorService.getExisting(argument.getAuthorId());

        return bookService.update(id, UpdateBookArgument.builder()
                                                    .bookName(argument.getBookName())
                                                    .numberOfPages(argument.getNumberOfPages())
                                                    .publicationYear(argument.getPublicationYear())
                                                    .author(author)
                                                    .build());
    }
}
