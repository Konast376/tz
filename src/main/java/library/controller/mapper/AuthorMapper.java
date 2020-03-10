package library.controller.mapper;

import library.controller.dto.AuthorDto;
import library.entity.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(Author author);

    Author authorDtoToAuthor(AuthorDto authorDto);
}
