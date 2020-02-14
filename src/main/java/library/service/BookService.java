package library.service;

import library.Exception.ResourceNotFoundException;
import library.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import library.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public void create(Book Book) { bookRepository.save(Book);}
    public Book get(Long id) {
        return bookRepository.findById(id).get();
    }
    public List<Book> listAll(Pageable pageable) {
        return (List<Book>) bookRepository.findAll(pageable);
    }
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    public List<Book> findAll(Pageable pageable) {
        return bookRepository.findAll();
}
    public Book findById(Long id) {
        return bookRepository.findById(id).get();
    }
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    public Book updateBook(Book book) {
        Optional< Book > bookDb = this.bookRepository.findById(book.getId());
        if (bookDb.isPresent()) {
            Book bookUpdate = bookDb.get();
            bookUpdate.setId(book.getId());
            bookUpdate.setBookName(book.getBookName());
            bookUpdate.setNumberOfPages(book.getNumberOfPages());
            bookUpdate.setPublicationYear(book.getPublicationYear());
            bookUpdate.setAuthor(book.getAuthor());
            bookRepository.save(bookUpdate);
            return bookUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + book.getId());
        }
    }
}

