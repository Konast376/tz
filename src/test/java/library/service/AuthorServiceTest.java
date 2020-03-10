package library.service;

import library.entity.Author;
import library.repository.AuthorRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @InjectMocks
    AuthorService service;
    @Mock
    AuthorRepository repository;
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createAuthor() {
        Author author= mock(Author.class);
        service.createAuthor(author);
        verify(repository, times(1)).createAuthor(author);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void authorDelete() {
    }

    @Test
    void updateAuthor() {
    }
}