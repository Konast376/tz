package library.mapper;

import library.api.book.dto.BookDto;
import library.model.book.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
}
