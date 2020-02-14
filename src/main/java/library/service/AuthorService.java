package library.service;

import library.Exception.ResourceNotFoundException;
import library.entity.Author;
import library.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import library.repository.AuthorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public void create(Author author) {
        authorRepository.save(author);
    }
    public Author get(Long id) {
        return authorRepository.findById(id).get();
    }
    public List<Author> findAll(Pageable pageable) {
        return (List<Author>) authorRepository.findAll(pageable);
    }
    public Author findById(Long id) {
        return authorRepository.findById(id).get();
    }
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
    public Author updateAuthor(Author author) {
        Optional< Author > authorDb = this.authorRepository.findById(author.getId());
        if (authorDb.isPresent()) {
            Author authorUpdate = authorDb.get();
            authorUpdate.setId(author.getId());
            authorUpdate.setFullName(author.getFullName());
            authorUpdate.setNationality(author.getNationality());
            authorUpdate.setDateOfBirth(author.getDateOfBirth());
            authorRepository.save(authorUpdate);
            return authorUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + author.getId());
        }
}
}
