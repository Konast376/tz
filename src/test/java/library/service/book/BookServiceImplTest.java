package library.service.book;

import com.whitesoft.util.exceptions.WSArgumentException;
import com.whitesoft.util.exceptions.WSNotFoundException;
import library.api.book.dto.UpdateBookDto;
import library.errorInfo.AuthorErrorInfo;
import library.errorInfo.BookErrorInfo;
import library.model.author.Author;
import library.model.book.Book;
import library.repository.AuthorRepository;
import library.repository.BookRepository;
import library.service.author.AuthorServiceImpl;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.thewhite.util.test.check.GuardCheck.guardCheck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class BookServiceImplTest {
    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @InjectMocks
    BookServiceImpl service;

    @Mock
    BookRepository repository;

    private final long id = 1L;

    @Test
    void create(BDDSoftAssertions softly) {
        //Arrange
        CreateBookArgument argument = mock(CreateBookArgument.class);
        when(argument.getBookName()).thenReturn("bookName");
        when(argument.getNumberOfPages()).thenReturn(120);
        when(argument.getPublicationYear()).thenReturn(1976);

        Book savedBook = mock(Book.class);
        when(repository.save(any())).thenReturn(savedBook);

        //Act
        Book result = service.create(argument);

        // Assert
        assertThat(result).isEqualTo(savedBook);

        verify(repository).save(bookCaptor.capture());
        Book bookCaptorValue = bookCaptor.getValue();

        softly.then(bookCaptorValue.getId()).isNull();
        softly.then(bookCaptorValue.getBookName()).isEqualTo(argument.getBookName());
        softly.then(bookCaptorValue.getNumberOfPages()).isEqualTo(argument.getNumberOfPages());
        softly.then(bookCaptorValue.getPublicationYear()).isEqualTo(argument.getPublicationYear());
        softly.then(bookCaptorValue.getAuthor()).isNull();

        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenArgumentNull() throws Exception {
        // Act
        assertThrows(NullPointerException.class,
                     () -> service.create(null));

        verifyNoInteractions(repository);
    }

    @Test
    void createWhenBookNameNull() throws Exception {
        //Arrange
        CreateBookArgument argument = mock(CreateBookArgument.class);
        when(argument.getBookName()).thenReturn(null);

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void createWhenFullNameBlank() throws Exception {
        //Arrange
        CreateBookArgument argument = mock(CreateBookArgument.class);
        when(argument.getBookName()).thenReturn("    ");

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void findAll() {
        //Arrange
        Pageable pageable = mock(Pageable.class);

        //Act
        service.getAll(pageable);

        //Assert
        verify(repository).findAll(pageable);
    }

    @Test
    void getExisting() {
        //Assert
        Book book = mock(Book.class);
        when(repository.findById(id)).thenReturn(Optional.of(book));

        //Act
        Book result = service.getExisting(id);

        //Assert
        assertEquals(book, result);
    }

    @Test
    void update(BDDSoftAssertions softly) {
        //Arrange
        UpdateBookArgument argument = mock(UpdateBookArgument.class);
        when(argument.getBookName()).thenReturn("bookName");
        when(argument.getNumberOfPages()).thenReturn(120);
        when(argument.getPublicationYear()).thenReturn(1976);

        Book book = mock(Book.class);
        when(repository.findById(id)).thenReturn(Optional.of(book));

        Book savedBook = mock(Book.class);
        when(repository.save(any())).thenReturn(savedBook);

        //Act
        Book result = service.update(id, argument);

        // Assert
        assertThat(result).isEqualTo(savedBook);
        verify(repository).save(bookCaptor.capture());

        verify(book).setBookName(argument.getBookName());
        verify(book).setNumberOfPages(argument.getNumberOfPages());
        verify(book).setPublicationYear(argument.getPublicationYear());

        verifyNoMoreInteractions(repository);
    }
    @Test
    void updateWhenArgumentNull() throws Exception {
        // Act
        assertThrows(NullPointerException.class,
                     () -> service.update(id,null));

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenBookNameNull() throws Exception {
        //Arrange
        UpdateBookArgument argument = mock(UpdateBookArgument.class);
        when(argument.getBookName()).thenReturn(null);

        // Act
        guardCheck(() -> service.update(id, argument),

                   //Assert
                   WSArgumentException.class,
                   BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenFullNameBlank() throws Exception {
        //Arrange
        CreateBookArgument argument = mock(CreateBookArgument.class);
        when(argument.getBookName()).thenReturn("    ");

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   BookErrorInfo.BOOK_NAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void delete() {
        //Act
        service.delete(id);

        //Assert
        verify(repository).deleteById(id);
    }
}