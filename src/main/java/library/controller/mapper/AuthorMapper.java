package library.controller.mapper;

import library.argument.UpdateAuthorArgument;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.controller.dto.UpdateAuthorDto;
import library.entity.Author;
import library.AuthorService;
import library.service.CreateAuthorArgument;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses= AuthorService.class)
public interface AuthorMapper {
  CreateAuthorArgument toCreateArgument (CreateAuthorDto dto);

  AuthorDto toDto(Author author);

  UpdateAuthorArgument toUpdateArgument (UpdateAuthorDto dto);
}
