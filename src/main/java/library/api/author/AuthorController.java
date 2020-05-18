package library.api.author;

import com.whitesoft.api.dto.CollectionDTO;
import com.whitesoft.api.mappers.MapperUtils;
import io.swagger.annotations.ApiOperation;
import library.service.author.AuthorService;
import library.service.author.AuthorServiceImpl;
import library.api.author.dto.AuthorDto;
import library.api.author.dto.CreateAuthorDto;
import library.api.author.dto.UpdateAuthorDto;
import library.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @ApiOperation("Создать автора")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody CreateAuthorDto body) {
        return authorMapper.toDto(authorService.create(authorMapper.toCreateArgument(body)));
    }

    @ApiOperation("Получить пейджинированный список авторов")
    @GetMapping("/list")
    public CollectionDTO<AuthorDto> getAll(@RequestParam(name = "pageNo") int pageNo,
                                                  @RequestParam(name = "pageSize") int pageSize,
                                                  @RequestParam String sortField,
                                                  @RequestParam Sort.Direction sortDirection) {

        return MapperUtils.mapPage(authorMapper::toDto,
                                   authorService.getAll(PageRequest.of(pageNo, pageSize,
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