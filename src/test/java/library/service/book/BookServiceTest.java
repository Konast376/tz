package library.service.book;

import library.model.author.Author;
import library.model.book.Book;
import library.repository.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createBook() {
        //arrange
        Book book = mock(Book.class);

        //act
        service.createBook(book);

        //assert
        verify(repository, times(1)).save(book);
    }

    @Test
    void listAll() {
        //arrange
        Pageable pageable = mock(Pageable.class);

        //act
        service.listAll(pageable);

        //assert
        verify(repository).findAll(pageable);
    }

    @Test
    void findById() {
        //arrange
        Book book = mock(Book.class);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        //act
        Book result = service.findById(1L);

        //assert
        assertEquals(book, result);
    }

    @Test
    void updateBook() {
        //arrange
        Book book = mock(Book.class);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Book book1 = new Book();
        book1.setId(1L);
        book1.setBookName("Девочка, которая всегда смеялась последней");
        book1.setNumberOfPages(396);
        book1.setPublicationYear(Date.from(Instant.now()));
        book1.setAuthor(mock(Author.class));

        //act
        service.updateBook(book1);

        //assert
        verify(book).setId(book1.getId());
        verify(book).setBookName(book1.getBookName());
        verify(book).setNumberOfPages(book1.getNumberOfPages());
        verify(book).setPublicationYear(book1.getPublicationYear());
        verify(book).setAuthor(book1.getAuthor());
        verify(repository).save(book);
    }

    @Test
    void deleteById() {
        //act
        service.deleteById(1L);

        //assert
        verify(repository).deleteById(1L);
    }
}