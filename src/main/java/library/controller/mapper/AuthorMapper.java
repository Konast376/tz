package library.controller.mapper;

import library.controller.dto.AuthorDto;
import library.controller.dto.BookDto;
import library.entity.Author;
import library.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(Author author);

    Author authorDtoToAuthor(AuthorDto authorDto);
}
