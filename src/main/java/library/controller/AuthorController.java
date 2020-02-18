package library.controller;

import library.entity.Author;
import library.entity.Book;
import library.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


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
    public Page<Author> getAllAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @GetMapping("/get(id)")
    public Author get(Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/authorBooks")
    public Set<Book> findAuthorBooks(Long id) {
        return authorService.findAuthorBooks(id);
    }

    @GetMapping("/update")
    public Author updateAuthor(Author author) {
        return authorService.updateAuthor(author);
    }

    @GetMapping("/delete(id)")
    public void delete(Long id) {
        authorService.authorDelete(id);
    }
}


