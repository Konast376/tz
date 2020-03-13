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

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Page<Book> listAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional
    public Book findById(Long id) {
        Optional<Book> bookDb = bookRepository.findById(id);
        return bookDb.orElseThrow(() -> new EntityNotFoundException("Record not found with id! "));
    }

    @Transactional
    public Book updateBook(Book book) {
        Book bookDb = findById(book.getId());
        bookDb.setId(book.getId());
        bookDb.setBookName(book.getBookName());
        bookDb.setNumberOfPages(book.getNumberOfPages());
        bookDb.setPublicationYear(book.getPublicationYear());
        bookDb.setAuthor(book.getAuthor());
        return bookRepository.save(bookDb);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}


