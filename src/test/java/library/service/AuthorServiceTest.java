package library.service;

import library.AuthorService;
import library.argument.UpdateAuthorArgument;
import library.entity.Author;
import library.repository.AuthorRepository;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Captor
    private ArgumentCaptor<Author> authorCaptor;

    @InjectMocks
    AuthorService service;
    @Mock
    AuthorRepository repository;

    private final String fullName = "fullName";
    private final Date dateOfBirth = new Date(1970, Calendar.DECEMBER, 31);
    private final String nationality = "";

    @ExtendWith(SoftAssertionsExtension.class)

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

        softly.then(result.getFullName()).isEqualTo(savedAuthor.getFullName());
        softly.then(result.getDateOfBirth()).isEqualTo(savedAuthor.getDateOfBirth());
        softly.then(result.getNationality()).isEqualTo(savedAuthor.getNationality());
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
        Author result = service.update(1L, argument);

        //assert
        assertThat(result).isEqualTo(savedAuthor);
        verify(repository).save(authorCaptor.capture());

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