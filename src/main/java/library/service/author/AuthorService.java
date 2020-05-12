package library.service.author;

import library.model.author.Author;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Author create(@NonNull CreateAuthorArgument argument);

    Page<Author> getAll(@NonNull Pageable pageable);

    Author getExisting(@NonNull Long id);

    Author update(@NonNull Long id, @NonNull UpdateAuthorArgument argument);

    void delete(@NonNull Long id);
}
