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

        return bookService.create(CreateBookArgument.builder()
                                                    .bookName(argument.getBookName())
                                                    .numberOfPages(argument.getNumberOfPages())
                                                    .publicationYear(argument.getPublicationYear())
                                                    .author(author)
                                                    .build());
    }
}


