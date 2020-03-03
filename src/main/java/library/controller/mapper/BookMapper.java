package library.controller.mapper;

import library.controller.dto.BookDto;
import library.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
}
