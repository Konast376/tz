package library.api;

import com.whitesoft.api.dto.CollectionDTO;
import library.api.author.AuthorController;
import library.api.author.dto.AuthorDto;
import library.api.author.dto.CreateAuthorDto;
import library.api.author.dto.UpdateAuthorDto;
import library.mapper.AuthorMapper;
import library.model.author.Author;
import library.service.author.AuthorServiceImpl;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class AuthorControllerTest {

    @InjectMocks
    AuthorController controller;

    @Mock
    AuthorMapper mapper;

    @Mock
    AuthorServiceImpl service;

    long id = 1L;

    @Test
    void create() {
        //arrange
        CreateAuthorDto body = mock(CreateAuthorDto.class);
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(mapper.toCreateArgument(body)).thenReturn(argument);

        Author savedAuthor = mock(Author.class);
        when(service.create(argument)).thenReturn(savedAuthor);

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(savedAuthor)).thenReturn(dto);

        //act
        AuthorDto result = controller.create(body);

        //assert
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getAllAuthors() {
        //arrange
        int pageNo = 1;
        int pageSize = 1;
        String sortField = "test sorting field";
        Sort.Direction sortDirection = Sort.Direction.DESC;
        Author author = mock(Author.class);
        AuthorDto dto = mock(AuthorDto.class);

        when(mapper.toDto(author)).thenReturn(dto);
        when(service.findAll(any())).thenReturn(new PageImpl<>(Lists.newArrayList(author)));

        //act
        CollectionDTO<AuthorDto> result = controller.getAllAuthors(pageNo, pageSize, sortField, sortDirection);

        //assert
        Assertions.assertThat(result.getItems()).containsOnly(dto);
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void get() {
        //arrange
        AuthorDto dto = mock(AuthorDto.class);
        Author author = mock(Author.class);
        when(service.getExisting(id)).thenReturn(author);
        when(mapper.toDto(author)).thenReturn(dto);

        //act
        AuthorDto result = controller.get(id);

        //assert
        assertEquals(dto, result);
    }

    @Test
    void update() {
        //arrange
        UpdateAuthorDto body = mock(UpdateAuthorDto.class);
        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        Author update = mock(Author.class);
        AuthorDto dto = mock(AuthorDto.class);

        when(mapper.toUpdateArgument(body)).thenReturn(argument);

        when(service.update(id, argument)).thenReturn(update);
        when(mapper.toDto(update)).thenReturn(dto);

        //act
        AuthorDto result = controller.update(id, body);

        //assert
        assertThat(result).isEqualTo(dto);

        verifyNoMoreInteractions(dto);
    }

    @Test
    void delete() {
        //act
        controller.delete(id);

        //assert
        verify(service).delete(id);
    }
}
