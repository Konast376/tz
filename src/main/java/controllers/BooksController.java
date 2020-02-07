package controllers;
import entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.BooksService;
import java.util.List;

    @RestController
    @RequestMapping("/api")
    public class BooksController {

        @Autowired
        private BooksService booksService;

        @GetMapping("/books")
        List<Books> getAllBooks() {
            return booksService.findAll();
        }


    }

