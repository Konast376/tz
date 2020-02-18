package library.controller;

import library.entity.Book;
import library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public Page<Book> listAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookService.listAll(pageable);
    }

    @GetMapping("/get(id)")
    public Book get(Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/update")
    public  Book updateBook(Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/delete(id)")
    public void delete(Long id) {
        bookService.deleteById(id);
    }
}

