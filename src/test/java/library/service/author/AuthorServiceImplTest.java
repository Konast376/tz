package library.service.author;

import com.whitesoft.util.exceptions.WSArgumentException;
import com.whitesoft.util.exceptions.WSNotFoundException;
import library.errorInfo.AuthorErrorInfo;
import library.model.author.Author;
import library.repository.AuthorRepository;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Optional;

import static com.thewhite.util.test.check.GuardCheck.guardCheck;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class AuthorServiceImplTest {
    @Captor
    private ArgumentCaptor<Author> authorCaptor;

    @InjectMocks
    AuthorServiceImpl service;

    @Mock
    AuthorRepository repository;

    private final long id = 1L;
    private final int pageNo = 1;
    private final int pageSize = 1;
    private final String sortField = "test sorting field";
    private final Sort.Direction sortDirection = DESC;

    @Test
    void create(BDDSoftAssertions softly) throws Exception {
        // Arrange
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("fullName");
        when(argument.getDateOfBirth()).thenReturn(new Date());
        when(argument.getNationality()).thenReturn("nationality");

        Author savedAuthor = mock(Author.class);
        when(repository.save(any())).thenReturn(savedAuthor);

        // Act
        Author result = service.create(argument);

        // Assert
        assertThat(result).isEqualTo(savedAuthor);

        verify(repository).save(authorCaptor.capture());
        Author authorCaptorValue = authorCaptor.getValue();

        softly.then(authorCaptorValue.getId()).isNull();
        softly.then(authorCaptorValue.getFullName()).isEqualTo(argument.getFullName());
        softly.then(authorCaptorValue.getDateOfBirth()).isEqualTo(argument.getDateOfBirth());
        softly.then(authorCaptorValue.getNationality()).isEqualTo(argument.getNationality());

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
    void createWhenFullNameNull() throws Exception {
        //Arrange
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(argument.getFullName()).thenReturn(null);

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.FULLNAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void createWhenFullNameBlank() throws Exception {
        //Arrange
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("    ");

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.FULLNAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void createWhenDateOfBirthNull() throws Exception {
        //Arrange
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("fullName");
        when(argument.getDateOfBirth()).thenReturn(null);

        // Act
        guardCheck(() -> service.create(argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.DATE_OF_BIRTH_IS_MANDATORY);

        verifyNoInteractions(repository);
    }


    @Test
    void getAll() {
        //Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Author> page = mock(Page.class);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        //Act
        Page<Author> result = service.getAll(pageable);

        //Assert
        Assertions.assertThat(result).isSameAs(page);
    }

    @Test
    void getExisting() {
        //Arrange
        Author author = mock(Author.class);
        when(repository.findById(id)).thenReturn(Optional.of(author));

        //Act
        Author result = service.getExisting(id);

        //Assert
        assertEquals(author, result);
    }

    @Test
    void getExistingWhenNotExists() {
        //Arrange
        when(repository.findById(id)).thenReturn(Optional.empty());

        //Act
        guardCheck(() -> service.getExisting(id),

                   //Assert
                   WSNotFoundException.class,
                   AuthorErrorInfo.NOT_FOUND);

        verify(repository).findById(id);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void update() {
        //Arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("fullName");
        when(argument.getDateOfBirth()).thenReturn(new Date());
        when(argument.getNationality()).thenReturn("nationality");

        Author author = mock(Author.class);
        when(repository.findById(id)).thenReturn(Optional.of(author));

        Author savedAuthor = mock(Author.class);
        when(repository.save(author)).thenReturn(savedAuthor);

        //Act
        Author result = service.update(id, argument);

        //Assert
        assertThat(result).isEqualTo(savedAuthor);
        verify(repository).save(authorCaptor.capture());

        verify(author).setFullName(argument.getFullName());
        verify(author).setDateOfBirth(argument.getDateOfBirth());
        verify(author).setNationality(argument.getNationality());

        verifyNoMoreInteractions(author);
    }

    @Test
    void whenUpdateArgumentNull() throws Exception {
        // Act
        assertThrows(NullPointerException.class,
                     () -> service.update(id, null));

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenIdNull() throws Exception {
        //Arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);

        // Act
        assertThrows(NullPointerException.class,
                     () -> service.update(null, argument));

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenNameNull() throws Exception {
        //Arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        when(argument.getFullName()).thenReturn(null);

        // Act
        guardCheck(() -> service.update(id, argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.FULLNAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenFullNameBlank() throws Exception {
        //Arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("    ");

        // Act
        guardCheck(() -> service.update(id, argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.FULLNAME_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void updateWhenDateOfBirthNull() throws Exception {
        //Arrange
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        when(argument.getFullName()).thenReturn("fullName");
        when(argument.getDateOfBirth()).thenReturn(null);

        // Act
        guardCheck(() -> service.update(id, argument),

                   //Assert
                   WSArgumentException.class,
                   AuthorErrorInfo.DATE_OF_BIRTH_IS_MANDATORY);

        verifyNoInteractions(repository);
    }

    @Test
    void deleteWhenIdNull() throws Exception {
        // Act
        assertThrows(NullPointerException.class,
                     () -> service.delete(null));

        //Assert
        verifyNoInteractions(repository);
    }

    @Test
    void deleteWhenNotExists() {
        //Arrange
        when(repository.findById(any())).thenReturn(Optional.empty());

        //Act
        guardCheck(() -> service.delete(id),

                   //Assert
                   WSNotFoundException.class,
                   AuthorErrorInfo.NOT_FOUND);

        verify(repository).findById(id);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void delete() {
        //Arrange
        Author author = mock(Author.class);
        when(repository.findById(id)).thenReturn(Optional.of(author));

        //Act
        service.delete(id);

        //Assert
        verify(repository).delete(author);
    }
}