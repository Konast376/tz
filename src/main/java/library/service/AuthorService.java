package library.service;

import library.entity.Author;
import library.entity.Book;
import library.exception.AuthorDeleteException;
import library.exception.EntityNotFoundException;
import library.exception.NullBooksException;
import library.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

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

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        Optional<Author> authorDb = authorRepository.findById(id);
        if (authorDb.isPresent()) {
            return authorDb.get();
        } else {
            throw new EntityNotFoundException("Record not found with id! ");
        }
    }


    public Set<Book> findAuthorBooks(Long id) {
        Author author = findById(id);
        if (author.getBooks() != null) {
            return author.getBooks();
        } else {
            throw new NullBooksException("Author has not books!");
        }

    }

    public void authorDelete(Long id) {
        Optional<Author> authorDb = authorRepository.findById(id);
        if (authorDb.isPresent()) {
            Author author = authorDb.get();
            if (author.getBooks() != null) {
                throw new AuthorDeleteException("Deleting is not possible. The author has a book!");
            } else {
                authorRepository.deleteById(id);
            }
        }
    }

    public Author updateAuthor(Author author) {
        Optional<Author> authorDb = authorRepository.findById(author.getId());
        if (authorDb.isPresent()) {
            Author authorUpdate = authorDb.get();
            authorUpdate.setId(author.getId());
            authorUpdate.setFullName(author.getFullName());
            authorUpdate.setNationality(author.getNationality());
            authorUpdate.setDateOfBirth(author.getDateOfBirth());
            authorRepository.save(authorUpdate);
            return authorUpdate;
        }
        throw new EntityNotFoundException("Record not found with id!");

    }
}


