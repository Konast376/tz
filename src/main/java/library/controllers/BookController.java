package library.controllers;

import library.entity.Book;
import library.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/create")
    public void create(Book Book) {
        bookService.create(Book);
    }
    @GetMapping("/list")
    public List<Book> listAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public Book get(Long id) {
        return bookService.findById(id);
    }
    @PostMapping("/{id}")
    public void delete(Long id) {
        bookService.deleteById(id);
    }
    }

