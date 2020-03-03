package library.controller;

import library.entity.Author;
import library.entity.Book;
import library.service.AuthorService;
import library.controller.dto.BookDto;
import library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @PostMapping("/create")
    public Book create(@RequestBody BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setBookName(bookDto.getBookName());
        book.setNumberOfPages(bookDto.getNumberOfPages());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setAuthor(authorService.findById(bookDto.getAuthorId()));
        return bookService.createBook(book);
    }

    @GetMapping("/list")
    public Page<Book> listAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookService.listAll(pageable);
    }

    @GetMapping("/get")
    public Book get(@RequestParam Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/authorBooks")
    public Set<Book> findAuthorBooks(@RequestParam Long id) {
        Author author = authorService.findById(id);
        return author.getBooks();

    }


    @GetMapping("/update")
    public Book updateBook(@RequestBody BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setBookName(bookDto.getBookName());
        book.setNumberOfPages(bookDto.getNumberOfPages());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setAuthor(authorService.findById(bookDto.getAuthorId()));
        return bookService.updateBook(book);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {
        bookService.deleteById(id);
    }
}

