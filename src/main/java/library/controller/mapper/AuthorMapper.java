package library.controller.mapper;

import library.service.argument.UpdateAuthorArgument;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.controller.dto.UpdateAuthorDto;
import library.entity.Author;
import library.service.argument.CreateAuthorArgument;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface AuthorMapper {
  CreateAuthorArgument toCreateArgument (CreateAuthorDto dto);

  AuthorDto toDto(Author author);

  UpdateAuthorArgument toUpdateArgument (UpdateAuthorDto dto);
}
