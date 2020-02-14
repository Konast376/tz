package library.controllers;

import library.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import library.service.AuthorService;

import java.util.List;


@RestController
@RequestMapping("/api/author")
class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @PostMapping("/create")
    public void create(Author author) {
        authorService.create(author);
    }
    @GetMapping("/list")
    public List<Author> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return authorService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public Author get(Long id) {
        return authorService.findById(id);
    }
    @PostMapping("/{id}")
    public void delete(Long id) {
        authorService.deleteById(id);
    }
}


