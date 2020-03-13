package library.service;

import library.entity.Author;
import library.exception.EntityNotFoundException;
import library.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service

public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Transactional
    public Author findById(Long id) {
        Optional<Author> authorDb = authorRepository.findById(id);
        return authorDb.orElseThrow(() -> new EntityNotFoundException("Record not found with id! "));
    }

    @Transactional
    public Author updateAuthor(Author author) {
        Author authorDb = findById(author.getId());
        authorDb.setId(author.getId());
        authorDb.setFullName(author.getFullName());
        authorDb.setNationality(author.getNationality());
        authorDb.setDateOfBirth(author.getDateOfBirth());
        authorDb.setBooks(author.getBooks());
        return authorRepository.save(authorDb);
    }

    @Transactional
    public void authorDelete(Long id) {
        authorRepository.deleteById(id);
    }
}




