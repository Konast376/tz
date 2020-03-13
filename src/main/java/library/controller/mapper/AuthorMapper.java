package library.controller.mapper;

import library.controller.dto.AuthorDto;
import library.entity.Author;
import library.service.AuthorService;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses= AuthorService.class)
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(Author author);

    Author authorDtoToAuthor(AuthorDto authorDto);
}
