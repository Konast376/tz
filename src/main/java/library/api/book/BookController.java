package library.api.book;

import com.whitesoft.api.dto.CollectionDTO;
import com.whitesoft.api.mappers.MapperUtils;
import com.whitesoft.util.actions.Action;
import io.swagger.annotations.ApiOperation;
import library.action.CreateBookActionArgument;
import library.api.book.dto.BookDto;
import library.api.book.dto.CreateBookDto;
import library.api.book.dto.UpdateBookDto;
import library.mapper.BookMapper;
import library.model.book.Book;
import library.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final Action<Book> createBookAction;

    @ApiOperation("Создать книгу")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody CreateBookDto body) {
        CreateBookActionArgument argument = bookMapper.toCreateDto(body);
        return bookMapper.toDto(createBookAction.execute(argument));
    }

    @ApiOperation("Получить пейджинированный список книг")
    @GetMapping("/list")
    public CollectionDTO<BookDto> getAll(@RequestParam(name = "pageNo") int pageNo,
                                                @RequestParam(name = "pageSize") int pageSize,
                                                @RequestParam String sortField,
                                                @RequestParam Sort.Direction sortDirection) {

        return MapperUtils.mapPage(bookMapper::toDto,
                                   bookService.getAll(PageRequest.of(pageNo, pageSize,
                                                                      Sort.by(sortDirection, sortField))));
    }

    @ApiOperation("Получить книгу")
    @GetMapping("/{id}")
    public BookDto get(@PathVariable Long id) {
        return bookMapper.toDto(bookService.getExisting(id));
    }

    @ApiOperation("Обновить книгу")
    @PostMapping("/{id}/update")
    public BookDto update(@PathVariable Long id,
                          @RequestBody UpdateBookDto body) {
        return bookMapper.toDto(bookService.update(id, bookMapper.toUpdateArgument(body)));
    }

    @ApiOperation("Удалить книгу")
    @PostMapping("/{id}/delete")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}