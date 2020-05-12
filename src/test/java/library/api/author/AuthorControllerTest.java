package library.api.author;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.DESC;


@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class AuthorControllerTest {

    @InjectMocks
    AuthorController controller;

    @Mock
    AuthorMapper mapper;

    @Mock
    AuthorServiceImpl service;

    private final long id = 1L;

    @Test
    void create() {
        //arrange
        CreateAuthorDto createDto = mock(CreateAuthorDto.class);
        CreateAuthorArgument argument = mock(CreateAuthorArgument.class);
        when(mapper.toCreateArgument(createDto)).thenReturn(argument);

        Author savedAuthor = mock(Author.class);
        when(service.create(argument)).thenReturn(savedAuthor);

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(savedAuthor)).thenReturn(dto);

        //act
        AuthorDto result = controller.create(createDto);

        //assert
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getAll() {
        //arrange
        int pageNo = 1;
        int pageSize = 1;
        String sortField = "test sorting field";
        Sort.Direction sortDirection = DESC;
        Author author = mock(Author.class);
        when(service.getAll(any())).thenReturn(new PageImpl<>(Lists.newArrayList(author)));

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(author)).thenReturn(dto);

        //act
        CollectionDTO<AuthorDto> result = controller.getAll(pageNo, pageSize, sortField, sortDirection);

        //assert
        verify(service).getAll((PageRequest.of(pageNo, pageSize,sortDirection,sortField)));
        Assertions.assertThat(result.getItems()).containsOnly(dto);
    }

    @Test
    void get() {
        //arrange
        Author author = mock(Author.class);
        when(service.getExisting(id)).thenReturn(author);

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(author)).thenReturn(dto);

        //act
        AuthorDto result = controller.get(id);

        //assert
        assertEquals(dto, result);
    }

    @Test
    void update() {
        //arrange

        UpdateAuthorArgument argument = mock(UpdateAuthorArgument.class);
        UpdateAuthorDto updateDto = mock(UpdateAuthorDto.class);
        when(mapper.toUpdateArgument(updateDto)).thenReturn(argument);

        Author updatedDto = mock(Author.class);
        when(service.update(id, argument)).thenReturn(updatedDto);

        AuthorDto dto = mock(AuthorDto.class);
        when(mapper.toDto(updatedDto)).thenReturn(dto);

        //act
        AuthorDto result = controller.update(id, updateDto);

        //assert
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void delete() {
        //act
        controller.delete(id);

        //assert
        verify(service).delete(id);
    }
}
