package service;


import entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BooksRepository;

import java.util.List;

@Service
public class BooksService {
    @Autowired
    private final BooksRepository booksRepository;
    private BooksService booksService;

    public BooksService (BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }


    public void createAddress(Books books){
        booksRepository.save(books);
    }

    public List<Books> findAll() {
        return booksService.findAll();
    }
}
