package library.service.author;

import com.whitesoft.util.Guard;
import com.whitesoft.util.exceptions.WSNotFoundException;
import library.errorInfo.AuthorErrorInfo;
import library.model.author.Author;
import library.repository.AuthorRepository;
import library.service.author.argument.CreateAuthorArgument;
import library.service.author.argument.UpdateAuthorArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.logging.log4j.util.Strings.trimToNull;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public Author create(@NonNull CreateAuthorArgument argument) {
        Guard.checkArgumentExists(trimToNull(argument.getFullName()), AuthorErrorInfo.FULLNAME_IS_MANDATORY);
        Guard.checkArgumentExists(argument.getDateOfBirth(), AuthorErrorInfo.DATE_OF_BIRTH_IS_MANDATORY);

        return authorRepository.save(Author.builder()
                                           .fullName(argument.getFullName())
                                           .dateOfBirth(argument.getDateOfBirth())
                                           .nationality(argument.getNationality())
                                           .build());
    }

    @Transactional(readOnly = true)
    public Page<Author> getAll(@NonNull Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Author getExisting(@NonNull Long id) {
        return authorRepository.findById(id)
                               .orElseThrow(WSNotFoundException.of(AuthorErrorInfo.NOT_FOUND));
    }

    @Transactional(isolation = REPEATABLE_READ)
    public Author update(@NonNull Long id, @NonNull UpdateAuthorArgument argument) {
        Guard.checkArgumentExists(trimToNull(argument.getFullName()), AuthorErrorInfo.FULLNAME_IS_MANDATORY);
        Guard.checkArgumentExists(argument.getDateOfBirth(), AuthorErrorInfo.DATE_OF_BIRTH_IS_MANDATORY);

        Author author = getExisting(id);
        author.setFullName(argument.getFullName());
        author.setDateOfBirth(argument.getDateOfBirth());
        author.setNationality(argument.getNationality());

        return authorRepository.save(author);
    }

    @Transactional(isolation = SERIALIZABLE)
    public void delete(@NonNull Long id) {
        Guard.checkArgumentExists(id, AuthorErrorInfo.NOT_FOUND);
        Author author = getExisting(id);
        authorRepository.delete(author);
    }
}





