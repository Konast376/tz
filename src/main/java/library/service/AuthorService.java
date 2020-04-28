package library.service;

import library.service.argument.UpdateAuthorArgument;
import library.entity.Author;
import library.exception.NotFoundException;
import library.repository.AuthorRepository;
import library.service.argument.CreateAuthorArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public Author create(@NonNull CreateAuthorArgument argument) {
        return authorRepository.save(Author.builder()
                .fullName(argument.getFullName())
                .dateOfBirth(argument.getDateOfBirth())
                .nationality(argument.getNationality())
                .build());
    }

    @Transactional(readOnly = true)
    public Page<Author> findAll(@NonNull Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Author getExisting(@NonNull Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Record not found with id! "));
    }

    @Transactional
    public Author update(@NonNull Long id, @NonNull UpdateAuthorArgument argument) {

        Author author = getExisting(id);
        author.setFullName(argument.getFullName());
        author.setDateOfBirth(argument.getDateOfBirth());
        author.setNationality(argument.getNationality());

        return authorRepository.save(author);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        authorRepository.deleteById(id);
    }
}





