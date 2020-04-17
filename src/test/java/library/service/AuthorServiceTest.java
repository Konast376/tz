package library.service;

import library.AuthorService;
import library.argument.UpdateAuthorArgument;
import library.entity.Author;
import library.repository.AuthorRepository;
import lombok.var;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Captor
    private ArgumentCaptor<Author> authorCaptor;

    @InjectMocks
    AuthorService service;
    @Mock
    AuthorRepository repository;

    private final String fullName = "full name";
    private final Date dateOfBirth = new Date(1970, Calendar.DECEMBER,31);
    private final String nationality = "";

    @BeforeEach
   void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createAuthor(BDDSoftAssertions softly) {
    //arrange
    CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
    when(argument.getFullName()).thenReturn(fullName);
    when(argument.getDateOfBirth()).thenReturn(dateOfBirth);
    when(argument.getNationality()).thenReturn(nationality);

    Author savedAuthor = mock(Author.class);
    when(repository.save(any())).thenReturn(savedAuthor);

    //act
    Author result = service.create(argument);

    //assert
        assertThat(result).isEqualTo(savedAuthor);
    verify(repository).save(authorCaptor.capture());
        softly.then(result.getFullName()).isEqualTo(argument.getFullName());
        softly.then(result.getDateOfBirth()).isEqualTo(argument.getDateOfBirth());
        softly.then(result.getNationality()).isEqualTo(argument.getNationality());
    verifyNoMoreInteractions(repository);
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
    void getExisting() {
        //arrange
        Author author = mock(Author.class);
        when(repository.findById(1L)).thenReturn(Optional.of(author));

        //act
        Author result = service.getExisting(1L);

        //assert
        assertEquals(author, result);
    }

    @Test
    void updateAuthor() {
        //arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        when(argument.getFullName()).thenReturn(fullName);
        when(argument.getDateOfBirth()).thenReturn(dateOfBirth);
        when(argument.getNationality()).thenReturn(nationality);

        Author author = mock(Author.class);
        when(repository.findById(1L)).thenReturn(Optional.of(author));

        Author savedAuthor = mock(Author.class);
        when(repository.save(author)).thenReturn(savedAuthor);
        //act
        Author result = service.update(author.getId(), argument);

        //assert
        assertThat(result).isEqualTo(savedAuthor);

        verify(author).setFullName(argument.getFullName());
        verify(author).setDateOfBirth(argument.getDateOfBirth());
        verify(author).setNationality(argument.getNationality());

        verifyNoMoreInteractions(author);
    }

    @Test
    void delete() {
        //act
        service.delete(1L);

        //assert
        verify(repository).deleteById(1L);
    }
}