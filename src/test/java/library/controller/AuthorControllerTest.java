package library.controller;

import library.AuthorService;
import library.argument.UpdateAuthorArgument;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.entity.Author;
import library.service.CreateAuthorArgument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class AuthorControllerTest {

    @Mock
    AuthorService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createAuthor() {
        //arrange
        CreateAuthorDto body = mock(CreateAuthorDto.class);


        Author savedAuthor = mock(Author.class);
        when(repository.save(any())).thenReturn(savedAuthor);

        //act
        Author result = service.create(argument);

        //assert
        assertThat(result).isEqualTo(savedAuthor);
        verify(repository).save(authorCaptor.capture());
        assertThat(authorCaptor.getValue())
                .lazyCheck(Author :: getId, null);
                .lazyCheck(Author :: getFullName, argument.getFullName())
                .lazyCheck(Author :: getDateOfBirth, argument.getDateOfBirth())
                .lazyCheck(Author :: getNationality, argument.getNationality())
                .check();

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
        verify(service).delete(1L);
    }
}
