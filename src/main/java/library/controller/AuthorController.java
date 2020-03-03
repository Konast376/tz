package library.controller;

import library.controller.dto.AuthorDto;
import library.controller.mapper.AuthorMapper;
import library.entity.Author;
import library.entity.Book;
import library.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/author")
class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    //AuthorDto toDto(Author author);

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper=authorMapper;
    }

    @PostMapping("/create")
    public AuthorDto create(@RequestBody AuthorDto authorDto) {
       Author author = authorMapper.authorDtoToAuthor(authorDto);
        return authorMapper.authorToAuthorDto(authorService.createAuthor(author));
    }

    @GetMapping("/list")
    public Page<AuthorDto> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
       authorMapper.authorDtoToAuthor();
        return authorMapper.authorToAuthorDto(authorService.findAll(pageable);
    }

    @GetMapping("/get")
    public AuthorDto get(@RequestParam Long id) {

        return authorService.findById(id);
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


