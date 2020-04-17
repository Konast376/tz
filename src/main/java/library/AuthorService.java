package library;

import com.whitesoft.util.Guard;
import library.argument.UpdateAuthorArgument;
import library.entity.Author;
import library.exception.NotFoundException;
import library.repository.AuthorRepository;
import library.service.CreateAuthorArgument;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static library.errorinfo.AuthorErrorInfo.FULLNAME_IS_MANDATORY;

@Service

public class AuthorService {
    private final AuthorRepository authorRepository;


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author create(@NonNull CreateAuthorArgument argument)
    {
        return authorRepository.save(Author.builder()
                                            .fullName(argument.getFullName())
                                            .dateOfBirth(argument.getDateOfBirth())
                                            .nationality(argument.getNationality())
                                            .build());
    }

    @Transactional
    public Page<Author> findAll(@NonNull Pageable pageable)  {
        return authorRepository.findAll(pageable);
    }

    @Transactional
    public Author getExisting(@NonNull Long id)  {
        Optional<Author> authorDb = authorRepository.findById(id);
        return authorDb.orElseThrow(() -> new NotFoundException("Record not found with id! "));
    }

    @Transactional
    public Author update(@NonNull Long id, @NonNull UpdateAuthorArgument argument){
        Guard.checkArgumentValid(argument.getFullName() == null, FULLNAME_IS_MANDATORY );

        Author author = getExisting(id);
        author.setFullName(argument.getFullName());
        author.setDateOfBirth(argument.getDateOfBirth());
        author.setNationality(argument.getNationality());

        return authorRepository.save(author);
    }

    @Transactional
    public void delete(@NonNull Long id){
        authorRepository.deleteById(id);
    }
}





