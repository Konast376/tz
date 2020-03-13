package library.controller;

import library.controller.dto.AuthorDto;
import library.controller.mapper.AuthorMapper;
import library.entity.Author;
import library.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/author")
class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/create")
    public AuthorDto create(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.authorDtoToAuthor(authorDto);
        return authorMapper.authorToAuthorDto(authorService.createAuthor(author));
    }

    @GetMapping("/list")
    public List<AuthorDto> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Author> authors = authorService.findAll(pageable);
        List<AuthorDto> result = new ArrayList<>();
        for (Author author : authors.getContent()) {
            result.add(authorMapper.authorToAuthorDto(author));
        }
        return result;
    }

    @GetMapping("/get")
    public AuthorDto get(@RequestParam Long id) {
        return authorMapper.authorToAuthorDto(authorService.findById(id));
    }

    @GetMapping("/update")
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.authorDtoToAuthor(authorDto);
        return authorMapper.authorToAuthorDto(authorService.updateAuthor(author));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {
        authorService.authorDelete(id);
    }
}