package library.mapper;

import library.action.CreateBookActionArgument;
import library.action.UpdateBookActionArgument;
import library.api.author.dto.AuthorDto;
import library.api.author.dto.CreateAuthorDto;
import library.api.author.dto.UpdateAuthorDto;
import library.api.book.dto.BookDto;
import library.api.book.dto.CreateBookDto;
import library.api.book.dto.UpdateBookDto;
import library.model.author.Author;
import library.model.book.Book;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookDto toDto(Book book);

    CreateBookActionArgument toCreateDto(CreateBookDto dto);

    UpdateBookActionArgument toUpdateDto(UpdateBookDto dto);
}
