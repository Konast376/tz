package library.service.book;

import library.model.book.Book;
import library.service.book.argument.CreateBookArgument;
import library.service.book.argument.UpdateBookArgument;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
     Book create(@NonNull CreateBookArgument argument);

     Page<Book> findAll(@NonNull Pageable pageable);

     Book getExisting(@NonNull Long id);

     Book update(@NonNull Long id, @NonNull UpdateBookArgument argument);

     void delete(@NonNull Long id);
}
