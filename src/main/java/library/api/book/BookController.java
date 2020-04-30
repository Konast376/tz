package library.api.book;

import library.api.book.dto.BookDto;
import library.mapper.BookMapper;
import library.model.author.Author;
import library.model.book.Book;
import library.service.author.AuthorServiceImpl;
import library.service.book.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    private final AuthorServiceImpl authorService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, AuthorServiceImpl authorServiceImpl, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authorService = authorServiceImpl;
        this.bookMapper = bookMapper;
    }

    @PostMapping("/create")
    public BookDto create(@RequestBody BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        return bookMapper.bookToBookDto(bookService.createBook(book));
    }

    @GetMapping("/list")
    public List<BookDto> listAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Book> books = bookService.listAll(pageable);
        List<BookDto> result = new ArrayList<>();
        for (Book book : books.getContent()) {
            result.add(bookMapper.bookToBookDto(book));
        }
        return result;
    }

    @GetMapping("/get")
    public BookDto get(@RequestParam Long id) {
        return bookMapper.bookToBookDto(bookService.findById(id));
    }

    @GetMapping("/authorBooks")
    public Set<BookDto> findAuthorBooks(@RequestParam Long id) {
        Author author = authorService.getExisting(id);
        Set<BookDto> result = new HashSet<>();
        for (Book book : author.getBooks()) {
            result.add(bookMapper.bookToBookDto(book));
        }
        return result;
    }

    @GetMapping("/update")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        return bookMapper.bookToBookDto(bookService.updateBook(book));
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {
        bookService.deleteById(id);
    }
}