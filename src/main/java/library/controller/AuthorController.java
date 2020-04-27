package library.controller;

import com.whitesoft.api.dto.CollectionDTO;
import com.whitesoft.api.mappers.MapperUtils;
import io.swagger.annotations.ApiOperation;
import library.AuthorService;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.controller.dto.UpdateAuthorDto;
import library.controller.mapper.AuthorMapper;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/authors")
class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @ApiOperation("Создать автора")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody CreateAuthorDto body) {
        return authorMapper.toDto(authorService.create(authorMapper.toCreateArgument(body)));
    }

    @ApiOperation("Получить список авторов")
    @GetMapping("/list")
    public CollectionDTO<AuthorDto> getAllAuthors(@RequestParam(name = "pageNo") int pageNo,
                                                  @RequestParam(name = "pageSize") int pageSize,
                                                  @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                                  @RequestParam(name = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {

        return MapperUtils.mapPage(authorMapper::toDto,
                                   authorService.findAll(PageRequest.of(pageNo, pageSize,
                                                                        Sort.by(sortDirection, sortField))));
    }

    @ApiOperation("Получить автора")
    @GetMapping("/{id}")
    public AuthorDto get(@PathVariable Long id) {
        return authorMapper.toDto(authorService.getExisting(id));
    }

    @ApiOperation("Обновить автора")
    @PostMapping("/{id}/update")
    public AuthorDto update(@PathVariable Long id,
                            @RequestBody UpdateAuthorDto body) {
        return authorMapper.toDto(authorService.update(id, authorMapper.toUpdateArgument(body)));
    }

    @ApiOperation("Удалить автора")
    @PostMapping("/{id}/delete")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}