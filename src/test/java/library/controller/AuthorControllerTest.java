package library.controller;

import com.whitesoft.api.dto.CollectionDTO;
import library.AuthorService;
import library.argument.UpdateAuthorArgument;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.controller.dto.UpdateAuthorDto;
import library.controller.mapper.AuthorMapper;
import library.entity.Author;
import library.service.CreateAuthorArgument;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    AuthorService service;


    @Test
    void createAuthor() {
        //arrange
        CreateAuthorDto body = mock(CreateAuthorDto.class);
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(mapper.toCreateArgument(any())).thenReturn(argument);

        Author savedAuthor = mock(Author.class);
        when(service.create(any())).thenReturn(savedAuthor);

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(any())).thenReturn(dto);

        //act
        AuthorDto result = controller.create(body);

        //assert
        assertThat(result).isEqualTo(dto);

        verify(service).create(argument);
        verify(mapper).toDto(savedAuthor);
        verify(mapper).toCreateArgument(body);
        verifyNoMoreInteractions(service, mapper);
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

        when(mapper.toDto(any())).thenReturn(dto);
        when(service.findAll(any())).thenReturn(new PageImpl<>(Lists.newArrayList(author)));

        //act
        CollectionDTO<AuthorDto> result = controller.getAllAuthors(pageNo, pageSize, sortField, sortDirection);

        //assert
        Assertions.assertThat(result.getItems()).containsOnly(dto);

        verify(mapper).toDto(author);
        verify(service).findAll(PageRequest.of(pageNo, pageSize, sortDirection, sortField));
        verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void get() {
        //arrange
        AuthorDto dto = mock(AuthorDto.class);
        Author author = mock(Author.class);
        when(service.getExisting(1L)).thenReturn(author);
        when(mapper.toDto(author)).thenReturn(dto);

        //act
        AuthorDto result = controller.get(1L);

        //assert
        assertEquals(dto, result);
    }

    @Test
    void update() {
        //arrange
        long id = 1L;
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

        verify(mapper).toUpdateArgument(body);
        verify(service).update(id, argument);
        verify(mapper).toDto(update);
        verifyNoMoreInteractions(dto);
    }

    @Test
    void delete() {
        //act
        controller.delete(1L);

        //assert
        verify(service).delete(1L);
    }

}
