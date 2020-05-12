package library.api.book;

import com.whitesoft.api.dto.CollectionDTO;
import library.api.book.dto.BookDto;
import library.api.book.dto.CreateBookDto;
import library.api.book.dto.UpdateBookDto;
import library.mapper.BookMapper;
import library.model.book.Book;
import library.service.book.BookServiceImpl;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class BookControllerTest {
    @InjectMocks
    BookController controller;

    @Mock
    BookMapper mapper;

    @Mock
    BookServiceImpl service;

    private final long id = 1L;

    @Test
    void create() {
        //Arrange
        CreateBookDto createDto = mock(CreateBookDto.class);
        CreateBookArgument argument = mock(CreateBookArgument.class);
        when(mapper.toCreateArgument(createDto)).thenReturn(argument);

        Book savedBook = mock(Book.class);
        when(service.create(argument)).thenReturn(savedBook);

        BookDto dto = mock(BookDto.class);
        when(mapper.toDto(savedBook)).thenReturn(dto);

        //Act
        BookDto result = controller.create(createDto);

        //Assert
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getAll() {
        //Arrange
        int pageNo = 1;
        int pageSize = 1;
        String sortField = "test sorting field";
        Sort.Direction sortDirection = Sort.Direction.DESC;
        Book book = mock(Book.class);
        when(service.getAll(any())).thenReturn(new PageImpl<>(Lists.newArrayList(book)));

        BookDto dto = mock(BookDto.class);
        when(mapper.toDto(book)).thenReturn(dto);

        //Act
        CollectionDTO<BookDto> result = controller.getAll(pageNo, pageSize, sortField, sortDirection);

        //Assert
        verify(service).getAll(refEq(PageRequest.of(pageNo, pageSize, sortDirection, sortField)));
        Assertions.assertThat(result.getItems()).containsOnly(dto);
    }

    @Test
    void get() {
        //Arrange
        Book book = mock(Book.class);
        when(service.getExisting(id)).thenReturn(book);

        BookDto dto = mock(BookDto.class);
        when(mapper.toDto(book)).thenReturn(dto);

        //Act
        BookDto result = controller.get(id);

        //Assert
        assertEquals(dto, result);
    }

    @Test
    void update() {
        //Arrange
        UpdateBookArgument argument = mock(UpdateBookArgument.class);
        UpdateBookDto updateDto = mock(UpdateBookDto.class);
        when(mapper.toUpdateArgument(updateDto)).thenReturn(argument);

        Book updatedDto = mock(Book.class);
        when(service.update(id, argument)).thenReturn(updatedDto);

        BookDto dto = mock(BookDto.class);
        when(mapper.toDto(updatedDto)).thenReturn(dto);

        //Act
        BookDto result = controller.update(id, updateDto);

        //Assert
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void delete() {
        //Act
        controller.delete(id);

        //Assert
        verify(service).delete(id);
    }
}
