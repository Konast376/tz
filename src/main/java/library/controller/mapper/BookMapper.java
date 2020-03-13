package library.controller.mapper;

import library.controller.dto.BookDto;
import library.entity.Book;
import library.service.BookService;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses= BookService.class)
public interface BookMapper {
    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
}
