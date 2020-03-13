package library.service;

import library.entity.Author;
import library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @InjectMocks
    AuthorService service;
    @Mock
    AuthorRepository repository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createAuthor() {
        //arrange
        Author author = mock(Author.class);

        //act
        service.createAuthor(author);

        //assert
        verify(repository).save(author);
    }

    @Test
    void findAll() {
        //arrange
        Pageable pageable = mock(Pageable.class);

        //act
        service.findAll(pageable);

        //assert
        verify(repository).findAll(pageable);
    }

    @Test
    void findById() {
        //arrange
        Author author = mock(Author.class);
        when(repository.findById(1L)).thenReturn(Optional.of(author));

        //act
        Author result = service.findById(1L);

        //assert
        assertEquals(author, result);
    }

    @Test
    void updateAuthor() {
        //arrange
        Author author = mock(Author.class);
        when(repository.findById(1L)).thenReturn(Optional.of(author));

        Author author1 = new Author();
        author1.setId(1L);
        author1.setFullName("Цыпкин Александр");
        author1.setNationality("Русский");
        author1.setDateOfBirth(Date.from(Instant.now()));
        author1.setBooks(mock(Set.class));

        //act
        service.updateAuthor(author1);

        //assert
        verify(author).setId(author1.getId());
        verify(author).setFullName(author1.getFullName());
        verify(author).setNationality(author1.getNationality());
        verify(author).setDateOfBirth(author1.getDateOfBirth());
        verify(author).setBooks(author1.getBooks());
        verify(repository).save(author);
    }

    @Test
    void authorDelete() {
        //act
        service.authorDelete(1L);

        //assert
        verify(repository).deleteById(1L);
    }
}