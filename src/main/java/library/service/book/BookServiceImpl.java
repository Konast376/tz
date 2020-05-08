package library.service.book;

import com.whitesoft.util.Guard;
import com.whitesoft.util.exceptions.WSNotFoundException;
import library.errorInfo.BookErrorInfo;
import library.model.book.Book;
import library.repository.BookRepository;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.logging.log4j.util.Strings.trimToNull;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book create(@NonNull CreateBookArgument argument) {
        Guard.checkArgumentExists(trimToNull(argument.getBookName()), BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        return bookRepository.save(Book.builder()
                                       .bookName(argument.getBookName())
                                       .numberOfPages(argument.getNumberOfPages())
                                       .publicationYear(argument.getPublicationYear())
                                       .author(argument.getAuthor())
                                       .build());
    }

    @Transactional(readOnly = true)
    public Page<Book> findAll(@NonNull Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Book getExisting(@NonNull Long id) {
        return bookRepository.findById(id)
                             .orElseThrow(WSNotFoundException.of(BookErrorInfo.NOT_FOUND));
    }

    @Transactional(isolation = REPEATABLE_READ)
    public Book update(@NonNull Long id, @NonNull UpdateBookArgument argument) {
        Guard.checkArgumentExists(trimToNull(argument.getBookName()), BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        Book book = getExisting(id);

        book.setBookName(argument.getBookName());
        book.setNumberOfPages(argument.getNumberOfPages());
        book.setPublicationYear(argument.getPublicationYear());
        book.setAuthor(argument.getAuthor());

        return bookRepository.save(book);
    }

    @Transactional(isolation = SERIALIZABLE)
    public void delete(@NonNull Long id) {
        bookRepository.deleteById(id);
    }
}