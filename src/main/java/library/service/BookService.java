package library.service;

import library.entity.Book;
import library.exception.EntityNotFoundException;
import library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void create(Book Book) {
        bookRepository.save(Book);
    }

    public Page<Book> listAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findById(Long id) {
        Optional<Book> bookDb = bookRepository.findById(id);
        if (bookDb.isPresent()) {
            Book book = bookDb.get();
            if (book.getId() != null) {
                return bookRepository.findById(id).get();
            } else {
                throw new EntityNotFoundException("Record not found with id : " + book.getId());
            }
        }
        return null;
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book) {
        Optional<Book> bookDb = this.bookRepository.findById(book.getId());
        if (bookDb.isPresent()) {
            if (book.getId() != null) {
                Book bookUpdate = bookDb.get();
                bookUpdate.setId(book.getId());
                bookUpdate.setBookName(book.getBookName());
                bookUpdate.setNumberOfPages(book.getNumberOfPages());
                bookUpdate.setPublicationYear(book.getPublicationYear());
                bookUpdate.setAuthor(book.getAuthor());
                bookRepository.save(bookUpdate);
                return bookUpdate;
            } else {
                throw new EntityNotFoundException("Record not found with id : " + book.getId());
            }
        }
        return book;
    }
}