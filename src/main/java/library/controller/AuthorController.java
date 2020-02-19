package library.controller;

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

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public void create(@RequestBody Author author) {
        authorService.create(author);
    }

    @GetMapping("/list")
    public Page<Author> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @GetMapping("/get")
    public Author get(@RequestParam Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/authorBooks")
    public Set<Book> findAuthorBooks(@RequestParam Long id) {
        return authorService.findAuthorBooks(id);
    }

    @GetMapping("/update")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {
        authorService.authorDelete(id);
    }
}


