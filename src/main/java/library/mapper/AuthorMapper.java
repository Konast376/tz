package library.mapper;

import library.api.author.dto.AuthorDto;
import library.api.author.dto.CreateAuthorDto;
import library.api.author.dto.UpdateAuthorDto;
import library.service.author.argument.UpdateAuthorArgument;
import library.model.author.Author;
import library.service.author.argument.CreateAuthorArgument;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    CreateAuthorArgument toCreateArgument(CreateAuthorDto dto);

    AuthorDto toDto(Author author);

    UpdateAuthorArgument toUpdateArgument(UpdateAuthorDto dto);
}
